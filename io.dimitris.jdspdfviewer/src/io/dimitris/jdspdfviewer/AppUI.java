package io.dimitris.jdspdfviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.apache.pdfbox.pdmodel.PDDocument;

public class AppUI {
	
	protected JPanel dropTarget;
	protected JFrame main;
	protected Slideshow slideshow;
	protected SlidePanel previewPanel;
	
	public static void main(String[] args) throws Exception {
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		new AppUI().run();
	}
	
	protected void run() throws Exception {
		
		main = new JFrame();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getRootPane().setLayout(new BorderLayout());
		
		dropTarget = new JPanel();
		dropTarget.setLayout(new BorderLayout());
		Border dashedBorder = BorderFactory.createDashedBorder(Color.GRAY, 2, 3, 2, true);
		Border activeDashedBorder = BorderFactory.createDashedBorder(Color.LIGHT_GRAY, 2, 3, 2, true);
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		dropTarget.setBorder(BorderFactory.createCompoundBorder(emptyBorder, dashedBorder));
		final JLabel dropTargetLabel = new JLabel("Drop PDF presentation here.", SwingConstants.CENTER);
		
		dropTarget.add(dropTargetLabel, BorderLayout.CENTER);
		main.getRootPane().add(dropTarget, BorderLayout.CENTER);
		
		new  FileDrop(dropTarget, BorderFactory.createCompoundBorder(emptyBorder, activeDashedBorder), new FileDrop.Listener() {   
			public void  filesDropped( java.io.File[] files ) {   
		          if (files[0].getName().endsWith("pdf")) {
		        	  try { open(files[0]); }
		        	  catch (Exception ex) { ex.printStackTrace(); }
		          }
		      }
		});
		
		createToolbar();
		
		main.setSize(300, 300);
		main.setLocation(800, 400);
		main.setTitle("jdspdfviewer");
		main.setVisible(true);
		
	}
	
	protected void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(new AbstractAction("Start") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					startSlideshow();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		toolbar.add(new AbstractAction("Resume") {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (slideshow.canResume()) {
						slideshow.resume();
					}
					else {
						startSlideshow();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		main.getRootPane().add(toolbar, BorderLayout.SOUTH);
	}
	
	protected void startSlideshow() throws Exception {
		
		slideshow.start(new DefaultSlideshowCommandListener() {
			
			@Override
			public void goToSlide(int slideNumber) {
				previewPanel.goToSlide(slideNumber);
			}
			
			@Override
			public void exit() {
				slideshow.stop();
			}
		});
	}
	
	protected void open(File pdf) throws Exception {
		slideshow = new Slideshow(pdf);
		PDDocument document = PDDocument.load(pdf);
		previewPanel = new SlidePanel(document, false, true);
		dropTarget.removeAll();
		dropTarget.add(previewPanel, BorderLayout.CENTER);
		dropTarget.updateUI();
	}
	
}
