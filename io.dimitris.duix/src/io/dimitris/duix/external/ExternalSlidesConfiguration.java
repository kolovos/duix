package io.dimitris.duix.external;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.DOMBuilder;

public class ExternalSlidesConfiguration {
	
	protected File file;
	protected List<Element> slideConfigurations;
	protected ExternalSlideFactory factory;
	
	public ExternalSlidesConfiguration(File file) {
		if (file.exists()) {
			try {
		        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		        DOMBuilder domBuilder = new DOMBuilder();
		        Document document = domBuilder.build(dBuilder.parse(file));
		        slideConfigurations = document.getRootElement().getChildren();
		        factory = new ExternalSlideFactory(file.getParentFile());
			}
			catch (Exception ex) {
				ex.printStackTrace();
				slideConfigurations = new ArrayList<Element>();
			}
		}
		else {
			slideConfigurations = new ArrayList<Element>();
		}
	}
	
	public List<ExternalSlide> getExtenralSlidesBefore(int slideNumber) {
		
		List<ExternalSlide> externalSlides = new ArrayList<ExternalSlide>();
		for (Element slideConfiguration : slideConfigurations) {
			if ((slideNumber + "").equals(slideConfiguration.getAttributeValue("before"))) {
				externalSlides.add(factory.createSlide(slideConfiguration));
			}
		}
		return externalSlides;
	}

	public List<ExternalSlide> getExtenralSlidesAfter(int slideNumber) {
		
		List<ExternalSlide> externalSlides = new ArrayList<ExternalSlide>();
		for (Element slideConfiguration : slideConfigurations) {
			if ((slideNumber + "").equals(slideConfiguration.getAttributeValue("after"))) {
				externalSlides.add(factory.createSlide(slideConfiguration));
			}
		}
		return externalSlides;
	}
	
}
