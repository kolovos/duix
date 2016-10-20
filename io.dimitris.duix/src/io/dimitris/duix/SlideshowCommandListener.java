package io.dimitris.duix;

public interface SlideshowCommandListener {
	
	public void exit();
	
	public void goToSlide(int slideNumber);
	
	public void next();
	
	public void previous();
	
	public void blank();
	
}
