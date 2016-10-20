package io.dimitris.duix.ui;

import io.dimitris.duix.Slideshow;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

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
			FileDialog fileDialog = new FileDialog((Frame) null);
			fileDialog.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".pdf");
				}
			});
			fileDialog.setVisible(true);
			file = fileDialog.getFiles()[0];
			if (file == null) return;
		}
		
		if (app.getSlideshow() != null) app.getSlideshow().stop();
		app.setSlideshow(new Slideshow(file));
	}

}
