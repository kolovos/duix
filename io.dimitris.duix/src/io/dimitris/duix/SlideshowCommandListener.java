package io.dimitris.duix;

public interface SlideshowCommandListener {
	
	public void exit();
	
	public boolean goToSlide(int slideNumber);
	
	public boolean next();
	
	public boolean previous();
	
	public void blank();
	
}
