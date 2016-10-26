package io.dimitris.duix.external;

import io.dimitris.duix.SlidePanel;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GifSlide extends ExternalSlide {
	
	@Override
	public void attach(SlidePanel slidePanel) {
		super.attach(slidePanel);
		
		JLabel label = new JLabel(new ImageIcon(new File(baseDirectory, configuration.getAttributeValue("file")).getAbsolutePath()));
		label.setOpaque(true);
		String[] colorComponents = configuration.getAttributeValue("background", "255, 255, 255").split(",");
		label.setBackground(new Color(
				Integer.parseInt(colorComponents[0].trim()),
				Integer.parseInt(colorComponents[1].trim()),
				Integer.parseInt(colorComponents[2].trim())
		));
		slidePanel.add(label);
	}
	
	public void detach(SlidePanel slidePanel) {
		slidePanel.removeAll();
	};
	
}
