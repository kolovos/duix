package io.dimitris.jdspdfviewer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class App {
	
	protected SlideFrame slidesFrame;
	protected SlideFrame notesFrame;
	
	public static void main(String[] args) throws Exception {
		new App().run(new File(args[0]));
	}

	protected void run(File pdf) throws Exception {

		PDDocument document = PDDocument.load(pdf);

		boolean splitGuess = false;
		PDRectangle rectangle = document.getPage(0).getMediaBox();
		if (rectangle.getWidth() > rectangle.getHeight() * 2) { splitGuess = true; };
		
		slidesFrame = new SlideFrame(new SlidePanel(document, splitGuess, true));
		slidesFrame.setPublic(true);
		notesFrame = new SlideFrame(new SlidePanel(document, splitGuess, false));
		slidesFrame.setCounterpart(notesFrame);
		notesFrame.setCounterpart(slidesFrame);
		
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
	
}
