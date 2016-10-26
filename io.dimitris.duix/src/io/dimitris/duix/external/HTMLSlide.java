package io.dimitris.duix.external;

import io.dimitris.duix.SlidePanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class HTMLSlide extends ExternalSlide {
	
	@Override
	public void attach(SlidePanel slidePanel) {
		JLabel label = new JLabel(configuration.getTextNormalize(), SwingConstants.CENTER);
		slidePanel.add(label, BorderLayout.CENTER);
	}
	
	@Override
	public void detach(SlidePanel slidePanel) {
		slidePanel.removeAll();
	}
	
}
