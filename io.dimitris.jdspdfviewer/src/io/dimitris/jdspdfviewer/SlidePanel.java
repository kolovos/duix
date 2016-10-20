package io.dimitris.jdspdfviewer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

@SuppressWarnings("serial")
public class SlidePanel extends JComponent {
	
	protected PDDocument document;
	protected PDFRenderer renderer;
	protected int slideNumber = 0;
	protected boolean left = true;
	protected boolean blank = false;
	protected boolean split = true;
	
	public SlidePanel(PDDocument document, boolean split, boolean left) {
		this.document = document;
		this.split = split;
		this.left = left;
		this.renderer = new PDFRenderer(document);
	}
	
	public int getSlideNumber() {
		return slideNumber;
	}
	
	public void goToSlide(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < document.getNumberOfPages()) {
			this.slideNumber = slideNumber;
		}
		this.repaint();
	}
	
	public void setBlank(boolean blank) {
		this.blank = blank;
		this.repaint();
	}
	
	public boolean isBlank() {
		return blank;
	}
	
	public void showNext() {
		goToSlide(getSlideNumber() + 1); 
	}
	
	public void showPrevious() {
		goToSlide(getSlideNumber() - 1);
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	public boolean isSplit() {
		return split;
	}
	
	public void setSplit(boolean split) {
		this.split = split;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blank) return;
        
        BufferedImage image;
	    try {
	    	//TODO: See if the width/height of the page can be computed as follows to avoid rendering multiple times
	    	// int originalWidth = (int) document.getPage(slideNumber).getMediaBox().getWidth();
	    	//TODO: Add support for non-split slides
	        image = renderer.renderImage(slideNumber);
	        int originalWidth = image.getWidth();
	        int originalHeight = image.getHeight();
	        int parts = 1;
	        if (split) { parts = 2; };
	        
			image = renderer.renderImage(slideNumber, parts*(float)getWidth()/originalWidth);
			if (image.getHeight() > this.getHeight()) {
				image = renderer.renderImage(slideNumber, (float) getHeight()/originalHeight);
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
			
			int leftMargin = (getWidth() - drawable.getWidth()) / 2;
			int topMargin = (getHeight() - drawable.getHeight()) / 2;
			
			g.drawImage(drawable, leftMargin, topMargin, this);
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        
    }
	
}