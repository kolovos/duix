package io.dimitris.jdspdfviewer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class Slideshow {
	
	protected SlideFrame slidesFrame;
	protected SlideFrame notesFrame;
	protected PDDocument document;
	protected boolean hasNotes = false;
	
	public Slideshow(File pdf) throws Exception {
		document = PDDocument.load(pdf);
		PDRectangle rectangle = document.getPage(0).getMediaBox();
		if (rectangle.getWidth() > rectangle.getHeight() * 2) { hasNotes = true; };
	}
	
	protected void start(SlideshowCommandListener externalListener) throws Exception {
		
		slidesFrame = new SlideFrame(new SlidePanel(document, hasNotes, true));
		notesFrame = new SlideFrame(new SlidePanel(document, hasNotes, false));
		
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
		
	    resume();
		
	}
	
	public boolean hasNotes() {
		return hasNotes;
	}
	
	public PDDocument getDocument() {
		return document;
	}
	
	public void resume() {
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
		if (slidesFrame != null) slidesFrame.dispose();
		if (notesFrame != null) notesFrame.dispose();
	}
	
	protected boolean canResume() {
		return slidesFrame != null;
	}
}
