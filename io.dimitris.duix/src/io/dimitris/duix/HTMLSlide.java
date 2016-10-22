package io.dimitris.duix;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HTMLSlide extends Slide {
	
	public String getContent() {
		return "<html><h1>I am a HTML slide</h1></html>";
	}
	
	@Override
	public void install(SlidePanel slidePanel) {
		System.out.println("Installing " + slidePanel.getComponentCount());
		//slidePanel.setLayout(new BorderLayout());
		JLabel label = new JLabel(getContent(), SwingConstants.CENTER);
		label.setForeground(Color.RED);
		slidePanel.add(label, BorderLayout.CENTER);
		slidePanel.repaint();
	}
	
	@Override
	public void uninstall(SlidePanel slidePanel) {
		System.out.println("Uninstalling");
		slidePanel.removeAll();
	}
	
}
