package io.dimitris.jdspdfviewer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;

import org.apache.pdfbox.pdmodel.PDDocument;

public class App {
	
	protected SlideFrame slidesFrame;
	protected SlideFrame notesFrame;
	
	public static void main(String[] args) throws Exception {
		new App().run(args[0]);
	}

	@SuppressWarnings("serial")
	protected void run(String pdf) throws Exception {

		File file = new File(pdf);
		PDDocument document = PDDocument.load(file);

		slidesFrame = new SlideFrame(new SlidePanel(document, true));
		notesFrame = new SlideFrame(new SlidePanel(document, false));
		slidesFrame.setCounterpart(notesFrame);
		notesFrame.setCounterpart(slidesFrame);
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		
		slidesFrame.addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				slidesFrame.getSlidePanel().setBlank(!slidesFrame.getSlidePanel().isBlank());
			}
		}, KeyEvent.VK_B);
		
		if (gs.length > 1) {
			notesFrame.showOnScreen(1);
			slidesFrame.showOnScreen(2);
		}
		else {
			slidesFrame.showOnScreen(1);
		}
		
	}
	
}
