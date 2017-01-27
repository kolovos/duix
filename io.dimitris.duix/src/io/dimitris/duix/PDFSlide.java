package io.dimitris.duix;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFSlide extends Slide {
	
	protected PDFRenderer renderer = null;
	protected int pageNumber;
	protected boolean split;
	protected boolean left;
	protected Color background = Color.BLACK;
	
	public PDFSlide(PDFRenderer renderer, int pageNumber, boolean split, boolean left) {
		super();
		this.renderer = renderer;
		this.pageNumber = pageNumber;
		this.split = split;
		this.left = left;
	}

	public PDFRenderer getRenderer() {
		return renderer;
	}
	
	public void setRenderer(PDFRenderer renderer) {
		this.renderer = renderer;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void setBackground(Color background) {
		this.background = background;
	}
	
	public Color getBackground() {
		return background;
	}
	
	@Override
	public void paint(SlidePanel slidePanel, Graphics g) {
		
	    try {
	    	//TODO: See if the width/height of the page can be computed as follows to avoid rendering multiple times
	    	// int originalWidth = (int) document.getPage(slideNumber).getMediaBox().getWidth();
	    	//TODO: Add support for non-split slides
	        
	    	g.setColor(background);
        	g.fillRect(0, 0, slidePanel.getWidth(), slidePanel.getHeight());
        	
	    	BufferedImage image = renderer.renderImage(pageNumber);
	        int originalWidth = image.getWidth();
	        int originalHeight = image.getHeight();
	        int parts = 1;
	        if (split) { parts = 2; };
	        
			image = renderer.renderImage(pageNumber, parts*(float)slidePanel.getWidth()/originalWidth);
			if (image.getHeight() > slidePanel.getHeight()) {
				image = renderer.renderImage(pageNumber, (float) slidePanel.getHeight()/originalHeight);
			}
			
			BufferedImage drawable = image;
			
			if (split) {
				BufferedImage half = new BufferedImage(image.getWidth()/parts, image.getHeight(), image.getType());
				Graphics2D gr = half.createGraphics();
				
				if (left) {
					gr.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight(),
						0, 0, image.getWidth()/2, image.getHeight(), null);
				}
				else {
					gr.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight(),
							image.getWidth()/2, 0, image.getWidth(), image.getHeight(), null);					
				}
				drawable = half;
				gr.dispose();
			}
			
			int leftMargin = (slidePanel.getWidth() - drawable.getWidth()) / 2;
			int topMargin = (slidePanel.getHeight() - drawable.getHeight()) / 2;
			
			g.drawImage(drawable, leftMargin, topMargin, slidePanel);
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
	
}
