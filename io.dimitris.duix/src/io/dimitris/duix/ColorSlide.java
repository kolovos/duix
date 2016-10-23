package io.dimitris.duix;

import java.awt.Color;
import java.awt.Graphics;

public class ColorSlide extends Slide {
	
	protected Color color;
	
	public ColorSlide(Color color) {
		this.color = color;
	}
	
	@Override
	public void paint(SlidePanel slidePanel, Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, slidePanel.getWidth(), slidePanel.getHeight());
	}
	
}
