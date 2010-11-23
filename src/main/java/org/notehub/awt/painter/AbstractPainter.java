package org.notehub.awt.painter;

import java.awt.Dimension;
import java.awt.Point;

abstract public class AbstractPainter implements Painter{

	private Dimension size;
	public void setSize(Dimension size){
		this.size=size;
	}
	public Dimension getSize(){
		if(size==null){
			size=new Dimension(100,100);
		}
		return size;
	}
	
	private Point loc;
	public Point getLocation(){
		if(loc==null){
			loc=new Point();
		}
		return loc;
	}
	public void setLocation(Point loc){
		this.loc=loc;
	}
}

