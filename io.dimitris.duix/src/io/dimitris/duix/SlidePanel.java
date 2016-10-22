package io.dimitris.duix;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.apache.pdfbox.rendering.PDFRenderer;

@SuppressWarnings("serial")
public class SlidePanel extends JComponent {
	
	protected int slideNumber = 0;
	protected boolean blank = false;
	protected List<Slide> slides;
	
	public SlidePanel(List<Slide> slides) {
		this.slides = slides;
	}
	
	public int getSlideNumber() {
		return slideNumber;
	}
	
	public boolean goToSlide(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < slides.size()) {
			this.slideNumber = slideNumber;
			this.repaint();
			return true;
		}
		else {
			return false;
		}
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
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blank) return;
        
        if (getBackground() != null) {
	        g.setColor(getBackground());
	        g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        Slide slide = slides.get(slideNumber);
        
        if (slide instanceof PDFSlide) {
        	paint((PDFSlide) slide, g);
        }
        
        
    }
	
	protected void paint(PDFSlide slide, Graphics g) {
		BufferedImage image;
	    try {
	    	//TODO: See if the width/height of the page can be computed as follows to avoid rendering multiple times
	    	// int originalWidth = (int) document.getPage(slideNumber).getMediaBox().getWidth();
	    	//TODO: Add support for non-split slides
	        PDFRenderer renderer = slide.getRenderer();
	        boolean split = slide.isSplit();
	        boolean left = slide.isLeft();
	        int pageNumber = slide.getPageNumber();
	        
	    	image = renderer.renderImage(pageNumber);
	        int originalWidth = image.getWidth();
	        int originalHeight = image.getHeight();
	        int parts = 1;
	        if (split) { parts = 2; };
	        
			image = renderer.renderImage(pageNumber, parts*(float)getWidth()/originalWidth);
			if (image.getHeight() > this.getHeight()) {
				image = renderer.renderImage(pageNumber, (float) getHeight()/originalHeight);
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