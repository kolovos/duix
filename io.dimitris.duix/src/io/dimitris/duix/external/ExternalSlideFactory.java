package io.dimitris.duix.external;

import java.io.File;

import org.jdom2.Element;


public class ExternalSlideFactory {
	
	protected File baseDirectory = null;
	
	public ExternalSlideFactory(File baseDirectory) {
		this.baseDirectory = baseDirectory;
	}
	
	public ExternalSlide createSlide(Element configuration) {
		String tag = configuration.getName();
		ExternalSlide slide = null;
		if ("poll".equalsIgnoreCase(tag)) {
			slide = new PollSlide();
		}
		else if ("gif".equalsIgnoreCase(tag)) {
			slide = new GIFSlide();
		}
		else if ("html".equalsIgnoreCase(tag)) {
			slide = new HTMLSlide();
		}
		
		if (slide instanceof ExternalSlide) {
			slide.setBaseDirectory(baseDirectory);
			slide.setConfiguration(configuration);
		}
		
		return slide;
	}
	
}
