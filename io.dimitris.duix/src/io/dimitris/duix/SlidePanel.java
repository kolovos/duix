package io.dimitris.duix;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class SlidePanel extends JComponent {
	
	protected int slideNumber = -1;
	protected boolean blank = false;
	protected List<Slide> slides;
	
	public SlidePanel(List<Slide> slides) {
		setLayout(new BorderLayout());
		this.slides = slides;
		goToSlide(0);
	}
	
	public int getSlideNumber() {
		return slideNumber;
	}
	
	public boolean goToSlide(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < slides.size()) {
			if (this.slideNumber >= 0) slides.get(this.slideNumber).uninstall(this);
			this.slideNumber = slideNumber;
			slides.get(slideNumber).install(this);
			this.repaint();
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setBlank(boolean blank) {
		this.blank = blank;
		this.repaint();
	}
	
	public boolean isBlank() {
		return blank;
	}
	
	public void showNext() {
		goToSlide(getSlideNumber() + 1); 
	}
	
	public void showPrevious() {
		goToSlide(getSlideNumber() - 1);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (blank) {
        	g.setColor(getBackground());
        	g.fillRect(0, 0, getWidth(), getHeight());
        }
        else {
        	 slides.get(slideNumber).paint(this, g);
        }
    }
	
}