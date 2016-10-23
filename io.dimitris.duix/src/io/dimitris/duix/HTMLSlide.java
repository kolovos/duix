package io.dimitris.duix;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HTMLSlide extends Slide {
	
	public String getContent() {
		return "<html><h1>I am a <a href='http://google.com'>HTML</a> slide</h1></html>";
	}
	
	@Override
	public void attach(SlidePanel slidePanel) {
		JLabel label = new JLabel(getContent(), SwingConstants.CENTER);
		slidePanel.add(label, BorderLayout.CENTER);
	}
	
	@Override
	public void detach(SlidePanel slidePanel) {
		slidePanel.removeAll();
	}
	
}
