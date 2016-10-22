package io.dimitris.duix;

import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFSlide extends Slide {
	
	protected PDFRenderer renderer = null;
	protected int pageNumber;
	protected boolean split;
	protected boolean left;
	
	public PDFSlide(PDFRenderer renderer, int pageNumber, boolean split, boolean left) {
		super();
		this.renderer = renderer;
		this.pageNumber = pageNumber;
		this.split = split;
		this.left = left;
	}

	public PDFRenderer getRenderer() {
		return renderer;
	}
	
	public void setRenderer(PDFRenderer renderer) {
		this.renderer = renderer;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isSplit() {
		return split;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
}
