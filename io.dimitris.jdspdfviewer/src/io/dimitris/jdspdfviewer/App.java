package io.dimitris.jdspdfviewer;

import java.io.File;

public class App {
	
	public static void main(String[] args) throws Exception {
		new Slideshow(new File(args[0])).start(new DefaultSlideshowCommandListener() {
			@Override
			public void exit() {
				System.exit(0);
			}
		});
	}
	
}
