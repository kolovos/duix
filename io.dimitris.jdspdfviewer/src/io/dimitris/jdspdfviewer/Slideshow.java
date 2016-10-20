package io.dimitris.jdspdfviewer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class Slideshow {
	
	protected SlideFrame slidesFrame;
	protected SlideFrame notesFrame;
	protected File pdf;
	
	public Slideshow(File pdf) {
		this.pdf = pdf;
	}
	
	protected void start(SlideshowCommandListener externalListener) throws Exception {

		PDDocument document = PDDocument.load(pdf);

		boolean splitGuess = false;
		PDRectangle rectangle = document.getPage(0).getMediaBox();
		if (rectangle.getWidth() > rectangle.getHeight() * 2) { splitGuess = true; };
		
		slidesFrame = new SlideFrame(new SlidePanel(document, splitGuess, true));
		notesFrame = new SlideFrame(new SlidePanel(document, splitGuess, false));
		
		SlideshowCommandListener defaultListener = new DefaultSlideshowCommandListener() {
			
			@Override
			public void goToSlide(int slideNumber) {
				slidesFrame.getSlidePanel().goToSlide(slideNumber);
				notesFrame.getSlidePanel().goToSlide(slideNumber);
			}
			
			@Override
			public void blank() {
				slidesFrame.getSlidePanel().setBlank(!slidesFrame.getSlidePanel().isBlank());
			}
		};
		
		slidesFrame.addSlideshowCommandListener(defaultListener);
		notesFrame.addSlideshowCommandListener(defaultListener);
		if (externalListener != null) {
			slidesFrame.addSlideshowCommandListener(externalListener);
			notesFrame.addSlideshowCommandListener(externalListener);
		}
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		
		if (gs.length > 1) {
			notesFrame.showOnScreen(1);
			slidesFrame.showOnScreen(2);
		}
		else {
			slidesFrame.showOnScreen(1);
		}
		
	}
	
	public SlideFrame getSlidesFrame() {
		return slidesFrame;
	}
	
	public SlideFrame getNotesFrame() {
		return notesFrame;
	}
	
	protected void stop() {
		slidesFrame.dispose();
		notesFrame.dispose();
	}
	
}