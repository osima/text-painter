package org.notehub.awt.painter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public interface Painter {

	void paint(Graphics2D g);
	
	void setSize(Dimension size);
	Dimension getSize();
	
	void setLocation(Point loc);
	Point getLocation();
	
}
