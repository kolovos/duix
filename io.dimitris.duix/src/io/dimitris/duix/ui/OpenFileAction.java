package io.dimitris.duix.ui;

import io.dimitris.duix.Slideshow;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

@SuppressWarnings("serial")
public class OpenFileAction extends AppAction {
	
	protected File file;
	
	public OpenFileAction(Duix app, File file) {
		super(app, "Open...");
		this.file = file;
	}

	@Override
	public void actionPerformed() throws Exception {
		
		File pdf = null;
		
		if (file == null) {
			FileDialog fileDialog = new FileDialog((Frame) null);
			fileDialog.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".pdf");
				}
			});
			fileDialog.setVisible(true);
			
			if (fileDialog.getFiles().length > 0) {
				pdf = fileDialog.getFiles()[0];
			}
			else {
				return;
			}
			
		}
		else {
			pdf = file;
		}
		
		if (app.getSlideshow() != null) app.getSlideshow().stop();
		app.setSlideshow(new Slideshow(pdf));
	}

}
