package org.notehub.awt.painter;


import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class TextPainter extends AbstractPainter
implements PainterConstants{


	//文字表示開始位置の調整(縦方向)
	//private static final int HSPACE=-3;
	//private static final int HSPACE=0;

	private static final String RET=System.getProperty("line.separator");

	public TextPainter(){
	}

	private String text;
	public TextPainter(String text){
		this.text=text;
	}
	public void setText(String text){
		this.text=text;
	}
	public String getText(){
		if(text==null)
			text="";
		return text;
	}
	

	
	/*
	 * 何行とれるか数える
	 */
	private int getLines(){
		Graphics g = this.getGraphics();
		if(g == null)
			return 1;
		
		FontMetrics fm = g.getFontMetrics();
		int h = fm.getHeight();

		Dimension d = getSize();
		//Dimension d = getPreferredSize();
		int lines = (int)(d.height/h)+2;

		return lines;
	}

	private Graphics graphics;
	private void setGraphics(Graphics g){ graphics=g; }
	private Graphics getGraphics(){ return graphics; }



	private int align=LEFT;
	public void setHorizontalAlignment(int align){
		this.align=align;
	}
	public int getHorizontalAlignment(){
		return align;
	}

	private int valign=TOP;
	public void setVerticalAlignment(int valign){
		this.valign=valign;
	}
	public int getVerticalAlignment(){
		return valign;
	}

	
	public void paint(Graphics2D g2){
		Point loc=getLocation();
		paint(g2,loc.x,loc.y);
		
	}
	private void paint(Graphics2D g,int offsetx,int offsety){
		if(g==null)
			return ;

		setGraphics(g);


		String[] texts = getTexts();


		
		int hspace=g.getFontMetrics().getAscent();
		int x=offsetx;
		//int y=offsety+h+HSPACE;
		int y=offsety+hspace;

		int h = g.getFontMetrics().getHeight() - g.getFontMetrics().getLeading();

		// vertical ajustmenet
		{

			int lines=texts.length;
			int textsHeight=g.getFontMetrics().getHeight()*lines;

			if( getSize().height>textsHeight ){
				if( getVerticalAlignment()==MIDDLE ){
					y=(int)( ((float)y)+(getSize().height-textsHeight)/2.0f );
				}
				if( getVerticalAlignment()==BOTTOM ){
					y=y+getSize().height-textsHeight;
				}
			}
		}


	
		Shape shape = g.getClip();
		Dimension d = getSize();
		g.clipRect(offsetx,offsety,d.width,d.height);

	

		int align=getHorizontalAlignment();
		FontMetrics fm=g.getFontMetrics();

		for(int i=0; i<texts.length; i++){

			if(align==RIGHT){
				int sw=fm.stringWidth(texts[i]);
				int diffX=d.width-sw;
				g.drawString( texts[i],x+diffX,y);
				//System.out.println("Right");
			}
			else if(align==CENTER){
				int sw=fm.stringWidth(texts[i]);
				float diffX=((d.width-sw)/2.0f);
				g.drawString( texts[i],x+diffX,y);
			}
			else{
				g.drawString( texts[i],x,y);
			}

			y = y +h;
		}

		g.setClip(shape);
	}

	/*
	private String[] getTexts(){
		String[] texts = 
			getSubjects(
				this.getGraphics(),
				cutReturnCode(getText()),
				getSize().width,
				getLines()
			);

		ArrayList list=new ArrayList();
		for(int i=0; i<texts.length; i++){
			if(texts[i].length()>0){
				list.add( texts[i] );
			}
		}
		return (String[])list.toArray(new String[0]);

		//return texts;
	}
	*/
	private boolean returnAlive;
	public boolean isReturnAlive(){
		return returnAlive;
	}
	public void setReturnAlive(boolean b){
		this.returnAlive=b;
	}

	private String[] getTexts(){
		String text=getText();
		if(isReturnAlive()==false){
			text=cutReturnCode(text);
		}

		String[] texts = 
			getSubjects(
				this.getGraphics(),
				text,
				getSize().width,
				getLines()
			);

		ArrayList list=new ArrayList();
		for(int i=0; i<texts.length; i++){
			if(texts[i].length()>0){
				list.add( texts[i] );
			}
		}
		return (String[])list.toArray(new String[0]);

		//return texts;
	}


	//------------------------
	// 文字列分解処理関係
	//------------------------
	private static String[] getSubjects
	(Graphics g, String s, int cellwidth, int lines)	{

		if(g==null || lines<1){
			return new String[0];
			//String[] str = new String[]{s};
			//return str;
		}

		//cellwidth -= 15;	//make a bit space at rightSide.
		//cellwidth -= 5;	//make a bit space at rightSide.
		//cellwidth -= 1;	//make a bit space at rightSide.

		String[] str = new String[lines];
		for(int i=0; i<str.length; i++)
			str[i]="";

		FontMetrics fm = g.getFontMetrics();
		/*
		int w = fm.stringWidth(s);
		if( w < cellwidth )	{
			str[0] = s;
			return str;
		}
		else	{
		*/
		{
			Chunk[] list = getStringPerWord(s);
			String sx = "";
			int j = 0;

			for(int i=0; i<list.length; i++)	{
				Chunk chunk=list[i];
				if( chunk.isReturn() ){
					str[j]=sx;
					if(!sx.equals("")){
						j++;
					}
					if(j > (lines-1))	{ break; }
					sx = "";
				}
				else{
					int wx = fm.stringWidth(sx+chunk.getString());
					if( wx > cellwidth )	{
						str[j] = sx;
						if(!sx.equals("")){
							j++;
						}
						if(j > (lines-1))	{ break; }
						sx = "";
					}
				}
				if( (list.length-1) == i )	{	//doEnd
					str[j] = sx+list[i].getString();
				}
				else{
					sx = sx+list[i].getString();
				}
			}
		}
		return str;
	}
	/*
	private static String[] getSubjects
	(Graphics g, String s, int cellwidth, int lines)	{

		if(g==null || lines<1){
			String[] str = new String[]{s};
			return str;
		}

		//cellwidth -= 15;	//make a bit space at rightSide.
		//cellwidth -= 5;	//make a bit space at rightSide.
		//cellwidth -= 1;	//make a bit space at rightSide.

		String[] str = new String[lines];
		for(int i=0; i<str.length; i++)
			str[i]="";

		FontMetrics fm = g.getFontMetrics();
		int w = fm.stringWidth(s);
		if( w < cellwidth )	{
			str[0] = s;
			return str;
		}
		else	{

			String[] list = getStringPerWord(s);
			String sx = "";
			int j = 0;

			for(int i=0; i<list.length; i++)	{
				//System.out.println("PASS "+list[i]);
				int wx = fm.stringWidth(sx+list[i]);
				if( wx > cellwidth )	{
					str[j] = sx;
					if(!sx.equals(""))
						j++;

					//if(j > 2)	{ break; }
					if(j > (lines-1))	{ break; }
					sx = "";
				}
				if( (list.length-1) == i )	{	//doEnd
					str[j] = sx+list[i];
				}
				sx = sx+list[i];
			}
		}
		return str;
	}
	*/

	private static Chunk[] getStringPerWord(String str) {
		ArrayList list=new ArrayList();

		StringTokenizer st = new StringTokenizer(str,RET);
		while( st.hasMoreTokens() ){
			String s=(st.nextToken());
			String[] strings=__getStringPerWord(s);
			for(int i=0; i<strings.length; i++){
				list.add( Chunk.getInstance(strings[i]) );
			}
			list.add( Chunk.getInstanceAsReturn() );
		}
		return (Chunk[])list.toArray(new Chunk[0]);
	}
	private static String[] __getStringPerWord(String str) {
		ArrayList list=new ArrayList();
		int size=str.length();
		for(int i=0; i<size; i++){
			list.add( str.substring(i,i+1) );
		}
		return (String[])list.toArray(new String[0]);
	}

	/*
	public static String[] getStringPerWord(String str) {
		ArrayList list=new ArrayList();
		int size=str.length();
		for(int i=0; i<size; i++){
			list.add( str.substring(i,i+1) );
		}
		return (String[])list.toArray(new String[0]);
	}
	*/
	
	

	private static StringTokenizer st;
	/** 改行コードを削除 */
	private static String cutReturnCode(String s)	{
		//return s;
		
		if(s==null)
			return "";

		st = new StringTokenizer(s,RET);
		StringBuffer sb = new StringBuffer();
		//int i = 0;
		while( st.hasMoreTokens() ){
			sb.append(st.nextToken());
			//sb.append(RETSTRING);//改行の代わりに特殊文字を埋める
			sb.append(" ");//改行の代わりにスペースを追加
		}

		return sb.toString();
	}

}

class Chunk {
	private boolean ret;
	private String str;

	private Chunk(boolean ret,String str){
		this.ret=ret;
		this.str=str;
	}
	public boolean isReturn(){ return ret; }
	public String getString(){ return str; }
	static Chunk getInstance(String str){
		return new Chunk(false,str);
	}
	static Chunk getInstanceAsReturn(){
		return new Chunk(true,"");
	}
}

