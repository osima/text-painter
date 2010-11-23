package org.notehub.awt.painter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestFrame extends JFrame{

	static final String TEXT="じゅげむじゅげむごこうのすりきれ、"+System.getProperty("line.separator")+"かいじゃりすいぎょのすいぎょうまつ";
	public TestFrame() {
		
		
		JPanel p=new JPanel(new GridLayout(3,3,10,10));
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		// first row
		{
			int vta=TextPainter.TOP;
			
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.LEFT);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.CENTER);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.RIGHT);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
		}
		
		// sencond row
		{
			int vta=TextPainter.MIDDLE;
			
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.LEFT);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.CENTER);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.RIGHT);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
		}
		// third row
		{
			int vta=TextPainter.BOTTOM;

			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.LEFT);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.CENTER);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
			{
				MultiLineLabel l=createLabel();
				l.setHorizontalAlignment(TextPainter.RIGHT);
				l.setVerticalAlignment(vta);
				p.add(l);
			}
		}


		
		getContentPane().add(p,BorderLayout.CENTER);
		
	}
	private MultiLineLabel createLabel(){
		MultiLineLabel l=new MultiLineLabel();
		l.setFont( createDefaultFont(10) );
		l.setText(TEXT);
		l.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		return l;
	}
	private static Font createDefaultFont(int fontSize){
		return new Font("Dialog",Font.PLAIN,fontSize);
	}
	
	public static void main(String[] s){
		JFrame f=new TestFrame();
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		f.setSize(500,400);
		f.setVisible(true);
		
	}
}
