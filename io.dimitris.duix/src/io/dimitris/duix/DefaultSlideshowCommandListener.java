package io.dimitris.duix;

public class DefaultSlideshowCommandListener implements SlideshowCommandListener {
	
	protected int slideNumber = 1;
	
	public DefaultSlideshowCommandListener() {}
	
	public DefaultSlideshowCommandListener(int slideNumber) {
		this.slideNumber = slideNumber;
		goToSlide(slideNumber);
	}
	
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
