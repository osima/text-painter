package org.notehub.awt.painter;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;


public class MultiLineLabel extends JComponent{

	private TextPainter painter;
	public MultiLineLabel(){
		this("");
	}
	public MultiLineLabel(String text){
		super();
		painter=new TextPainter();
		painter.setReturnAlive(true);
		setText(text);
	}

	public void setReturnAlive(boolean b){
		painter.setReturnAlive(b);
	}
	public boolean isReturnAlive(){
		return painter.isReturnAlive();
	}

	public void setHorizontalAlignment(int policy){
		painter.setHorizontalAlignment(policy);
	}
	public int getHorizontalAlignment(){
		return painter.getHorizontalAlignment();
	}
	public void setVerticalAlignment(int policy){
		painter.setVerticalAlignment(policy);
	}
	public int getVerticalAlignment(){
		return painter.getVerticalAlignment();
	}



	public void setText(String text){
		painter.setText(text);
		repaint();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		painter.setLocation(new Point());
		painter.setSize(getSize());
		painter.paint((Graphics2D)g);
	}

}
