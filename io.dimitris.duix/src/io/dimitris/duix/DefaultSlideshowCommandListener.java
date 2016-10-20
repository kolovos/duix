package io.dimitris.duix;

public class DefaultSlideshowCommandListener implements SlideshowCommandListener {
	
	protected int slideNumber = 0;
	
	public DefaultSlideshowCommandListener() {}
	
	public DefaultSlideshowCommandListener(int slideNumber) {
		this.slideNumber = slideNumber;
		goToSlide(slideNumber);
	}
	
	@Override
	public void exit() {}

	@Override
	public boolean goToSlide(int slideNumber) { return false; }

	@Override
	final public boolean next() {
		if (goToSlide(slideNumber+1)) {
			slideNumber++;
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	final public boolean previous() {
		if (goToSlide(slideNumber-1)) {
			slideNumber--;
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void blank() {}
	
}
