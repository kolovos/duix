package io.dimitris.jdspdfviewer;

import java.awt.Color;
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
	
	public SlidePanel(PDDocument document, boolean left) {
		this.document = document;
		this.left = left;
		this.renderer = new PDFRenderer(document);
	}
	
	public int getSlideNumber() {
		return slideNumber;
	}
	
	public void setSlideNumber(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < document.getNumberOfPages()) {
			this.slideNumber = slideNumber;
		}
		this.repaint();
	}
	
	public void showNext() {
		setSlideNumber(getSlideNumber() + 1); 
	}
	
	public void showPrevious() {
		setSlideNumber(getSlideNumber() - 1);
	}
	
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public boolean isLeft() {
		return left;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage image;
	    try {
	        image = renderer.renderImage(slideNumber);
	        int originalWidth = image.getWidth();
	        int originalHeight = image.getHeight();
			image = renderer.renderImage(slideNumber, 2*(float)getWidth()/originalWidth);
			if (image.getHeight() > this.getHeight()) {
				image = renderer.renderImage(slideNumber, (float) getHeight()/originalHeight);
			}
			
			BufferedImage half = new BufferedImage(image.getWidth()/2, image.getHeight(), image.getType());
			Graphics2D gr = half.createGraphics();
			
			if (left) {
				gr.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight(),
					0, 0, image.getWidth()/2, image.getHeight(), null);
			}
			else {
				gr.drawImage(image, 0, 0, image.getWidth()/2, image.getHeight(),
						image.getWidth()/2, 0, image.getWidth(), image.getHeight(), null);					
			}
			gr.dispose();
			
			int leftMargin = (getWidth() - half.getWidth()) / 2;
			int topMargin = (getHeight() - half.getHeight()) / 2;
			
			g.drawImage(half, leftMargin, topMargin, this);
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        
    }
	
}