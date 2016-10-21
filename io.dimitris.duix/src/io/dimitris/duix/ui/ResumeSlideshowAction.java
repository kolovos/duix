package io.dimitris.duix.ui;

@SuppressWarnings("serial")
public class ResumeSlideshowAction extends AppAction {

	public ResumeSlideshowAction(Duix app) {
		super(app, "Resume");
	}

	@Override
	public void actionPerformed() throws Exception {
		if (app.getSlideshow().canResume()) {
			app.getSlideshow().setFullscreen(app.isFullscreen());
			app.getSlideshow().setSwapScreens(app.isSwapScreeens());
			app.getSlideshow().resume();
		}
		else {
			new StartSlideshowAction(app).actionPerformed();
		}
	}
	
	@Override
	public boolean isEnabled() {
		System.out.println("Resume enabled?");
		return app.getSlideshow() != null && 
			app.getSlideshow().canResume();
	}

}
