package io.dimitris.duix;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class Slideshow {
	
	protected SlideFrame slidesFrame;
	protected SlideFrame notesFrame;
	protected PDDocument document;
	protected boolean hasNotes = false;
	protected boolean fullscreen = true;
	
	public Slideshow(File pdf) throws Exception {
		document = PDDocument.load(pdf);
		PDRectangle rectangle = document.getPage(0).getMediaBox();
		if (rectangle.getWidth() > rectangle.getHeight() * 2) { hasNotes = true; };
	}
	
	public void start(SlideshowCommandListener externalListener) throws Exception {
		
		slidesFrame = new SlideFrame(new SlidePanel(document, hasNotes, true));
		notesFrame = new SlideFrame(new SlidePanel(document, hasNotes, false));
		slidesFrame.setSize(400, 400);
		notesFrame.setSize(400, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		slidesFrame.setLocation((int)(screen.getWidth()-slidesFrame.getWidth())/2 - 100, (int)(screen.getHeight()-slidesFrame.getHeight())/2 - 100);
		notesFrame.setLocation((int)(screen.getWidth()-notesFrame.getWidth())/2 + 100, (int)(screen.getHeight()-notesFrame.getHeight())/2 + 100);
		
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
		
		if (fullscreen) {
		
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
		else {
			notesFrame.setVisible(true);
			slidesFrame.setVisible(true);
		}
	}
	
	public SlideFrame getSlidesFrame() {
		return slidesFrame;
	}
	
	public SlideFrame getNotesFrame() {
		return notesFrame;
	}
	
	public void stop() {
		if (slidesFrame != null) {
			slidesFrame.setVisible(false);
			slidesFrame.dispose();
		}
		if (notesFrame != null) {
			notesFrame.setVisible(false);
			notesFrame.dispose();
		}
	}
	
	public boolean canResume() {
		return slidesFrame != null;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
}
