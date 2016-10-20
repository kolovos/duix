package io.dimitris.duix.ui;

import io.dimitris.duix.DefaultSlideshowCommandListener;


@SuppressWarnings("serial")
public class StartSlideshowAction extends AppAction {

	public StartSlideshowAction(App app) {
		super(app, "Start");
	}

	@Override
	public void actionPerformed() throws Exception {
		app.getSlideshow().setFullscreen(app.isFullscreen());
		app.getSlideshow().start(new DefaultSlideshowCommandListener() {
			
			@Override
			public void goToSlide(int slideNumber) {
				app.getPreviewPanel().goToSlide(slideNumber);
			}
			
			@Override
			public void exit() {
				app.getSlideshow().stop();
			}
		});
	}
	
	@Override
	public boolean isEnabled() {
		return app.getSlideshow() != null;
	}
	
}
