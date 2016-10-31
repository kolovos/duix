package io.dimitris.duix;

import io.dimitris.duix.external.ExternalSlide;
import io.dimitris.duix.external.ExternalSlidesConfiguration;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Slideshow {
	
	protected SlideFrame slidesFrame;
	protected SlideFrame notesFrame;
	protected boolean fullscreen = true;
	protected boolean swapScreens = false;
	protected ArrayList<Slide> slides = new ArrayList<Slide>();
	protected ArrayList<Slide> notes = new ArrayList<Slide>();
	protected ArrayList<Slide> thumbnails = new ArrayList<Slide>();
	
	public Slideshow(File pdf) throws Exception {
		PDDocument document = PDDocument.load(pdf);
		PDRectangle rectangle = document.getPage(0).getMediaBox();
		boolean hasNotes = false;
		if (rectangle.getWidth() > rectangle.getHeight() * 2) { hasNotes = true; };
		PDFRenderer renderer = new PDFRenderer(document);
		
		ExternalSlidesConfiguration configuration = new ExternalSlidesConfiguration(new File(pdf.getAbsolutePath().substring(0, pdf.getAbsolutePath().length() - 4) + ".duix"));
		
		for (int i=0;i<document.getNumberOfPages();i++) {
			
			List<ExternalSlide> externalSlides = configuration.getExtenralSlidesBefore(i);
			slides.addAll(externalSlides);
			thumbnails.addAll(externalSlides);
			for (ExternalSlide externalSlide : externalSlides) {
				notes.add(externalSlide.getNotesSlide());
			}
			
			slides.add(new PDFSlide(renderer, i, hasNotes, true));
			thumbnails.add(new PDFSlide(renderer, i, hasNotes, true));
			notes.add(new PDFSlide(renderer, i, hasNotes, false));
			
			externalSlides = configuration.getExtenralSlidesAfter(i);
			slides.addAll(externalSlides);
			thumbnails.addAll(externalSlides);
			for (ExternalSlide externalSlide : externalSlides) {
				notes.add(externalSlide.getNotesSlide());
			}
		}
		
	}
	
	public List<Slide> getSlides() {
		return slides;
	}
	
	public ArrayList<Slide> getThumbnails() {
		return thumbnails;
	}
	
	public void start(SlideshowCommandListener externalListener) throws Exception {
		
		slidesFrame = new SlideFrame(new SlidePanel(slides));
		notesFrame = new SlideFrame(new SlidePanel(notes));
		
		slidesFrame.setSize(400, 400);
		notesFrame.setSize(400, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		slidesFrame.setLocation((int)(screen.getWidth()-slidesFrame.getWidth())/2 - 100, (int)(screen.getHeight()-slidesFrame.getHeight())/2 - 100);
		notesFrame.setLocation((int)(screen.getWidth()-notesFrame.getWidth())/2 + 100, (int)(screen.getHeight()-notesFrame.getHeight())/2 + 100);
		
		SlideshowCommandListener defaultListener = new DefaultSlideshowCommandListener() {
			
			@Override
			public boolean goToSlide(int slideNumber) {
				slidesFrame.getSlidePanel().goToSlide(slideNumber);
				return notesFrame.getSlidePanel().goToSlide(slideNumber);
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

		slidesFrame.setUndecorated(fullscreen);
		notesFrame.setUndecorated(fullscreen);
	    resume();
		
	}
	
	public void resume() {
		
		if (fullscreen) {
		
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] gs = ge.getScreenDevices();
			
			if (gs.length > 1) {
				if (!swapScreens) {
					slidesFrame.showOnScreen(1);
					notesFrame.showOnScreen(2);
				}
				else {
					notesFrame.showOnScreen(1);
					slidesFrame.showOnScreen(2);
				}
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

	public void setSwapScreens(boolean swapScreeens) {
		this.swapScreens = swapScreeens;
	}
	
	public boolean isSwapScreens() {
		return swapScreens;
	}
	
}
