package io.dimitris.duix.ui;

import io.dimitris.duix.Slideshow;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class OpenFileAction extends AppAction {
	
	protected File file;
	protected String lastDirectory;
	
	public OpenFileAction(Duix app, File file) {
		super(app, "Open...");
		this.file = file;
	}

	@Override
	public void actionPerformed() throws Exception {
		
		File pdf = null;
		
		if (file == null) {
			FileDialog fileDialog = new FileDialog((Frame) null, "Open", FileDialog.LOAD);
			
			fileDialog.setFilenameFilter(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".pdf");
				}
			});
			
			fileDialog.setDirectory(lastDirectory);
			fileDialog.setVisible(true);
			
			if (fileDialog.getFile() != null) {
				if (fileDialog.getDirectory() == null) {
					// See https://bugs.openjdk.java.net/browse/JDK-7132194
					JOptionPane.showMessageDialog(null, "Please select the file again, from its directory (not from recent files / search). \n To stop receiving this message, please update your Java installation.", "Cannot open file", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					lastDirectory = fileDialog.getDirectory();
					pdf = fileDialog.getFiles()[0];
				}
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
