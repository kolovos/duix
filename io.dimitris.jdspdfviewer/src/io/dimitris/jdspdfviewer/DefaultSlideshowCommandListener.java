package io.dimitris.jdspdfviewer;

public class DefaultSlideshowCommandListener implements SlideshowCommandListener {
	
	protected int slideNumber = 1;

	@Override
	public void exit() {}

	@Override
	public void goToSlide(int slideNumber) {}

	@Override
	final public void next() {
		slideNumber ++;
		goToSlide(slideNumber);
	}

	@Override
	final public void previous() {
		slideNumber --;
		goToSlide(slideNumber);
	}

	@Override
	public void blank() {}
	
}