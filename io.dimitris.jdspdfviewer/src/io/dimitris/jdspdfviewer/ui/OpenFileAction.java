package io.dimitris.jdspdfviewer.ui;

import io.dimitris.jdspdfviewer.Slideshow;

import java.io.File;

@SuppressWarnings("serial")
public class OpenFileAction extends AppAction {
	
	protected File file;
	
	public OpenFileAction(App app, File file) {
		super(app, "Open...");
		this.file = file;
	}

	@Override
	public void actionPerformed() throws Exception {
		if (file == null) {
			// Browse for file here
		}
		
		if (app.getSlideshow() != null) app.getSlideshow().stop();
		app.setSlideshow(new Slideshow(file));
	}

}
