package io.dimitris.duix.ui;



@SuppressWarnings("serial")
public class StopSlideshowAction extends AppAction {

	public StopSlideshowAction(Duix app) {
		super(app, "Stop");
	}

	@Override
	public void actionPerformed() throws Exception {
		app.getSlideshow().stop();
	}
	
	@Override
	public boolean isEnabled() {
		return app.getSlideshow() != null;
	}
	
}
