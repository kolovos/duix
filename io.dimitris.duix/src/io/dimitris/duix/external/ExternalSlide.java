package io.dimitris.duix.external;

import io.dimitris.duix.Slide;

import java.io.File;

import org.jdom2.Element;

public class ExternalSlide extends Slide {
	
	protected Element configuration;
	protected File baseDirectory;
	
	public Element getConfiguration() {
		return configuration;
	}
	
	public void setConfiguration(Element configuration) {
		this.configuration = configuration;
	}
	
	public File getBaseDirectory() {
		return baseDirectory;
	}
	
	public void setBaseDirectory(File baseDirectory) {
		this.baseDirectory = baseDirectory;
	}
	
	public ExternalSlide getNotesSlide() {
		Element notesElement = configuration.getChild("note");
		if (notesElement != null) {
			HTMLSlide notesSlide = new HTMLSlide();
			notesSlide.setConfiguration(notesElement);
			notesSlide.setBaseDirectory(baseDirectory);
			return notesSlide;
		}
		else return this;
	}
	
}
