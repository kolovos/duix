package io.dimitris.duix.ui;

import io.dimitris.duix.SlidePanel;
import io.dimitris.duix.Slideshow;

import java.awt.BorderLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class Duix {
	
	protected JPanel dropTarget;
	protected JFrame main;
	protected Slideshow slideshow;
	protected SlidePanel previewPanel;
	protected PopupMenu menu = null;
	protected CheckboxMenuItem fullscreenMenuItem = null;
	protected CheckboxMenuItem swapScreensMenuItem = null;
	protected List<ActionMenuItem> actionMenuItems = new ArrayList<ActionMenuItem>();
	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Duix duix = new Duix();
		duix.run();
		if (args.length == 1) {
			new OpenFileAction(duix, new File(args[0])).actionPerformed(null);
		}
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
		final JLabel dropTargetLabel = new JLabel("Go to File->Open... or drop PDF slides here.", SwingConstants.CENTER);
		
		dropTarget.add(dropTargetLabel, BorderLayout.CENTER);
		main.getRootPane().add(dropTarget, BorderLayout.CENTER);
		
		new  FileDrop(dropTarget, BorderFactory.createCompoundBorder(emptyBorder, activeDashedBorder), new FileDrop.Listener() {   
			public void  filesDropped( java.io.File[] files ) {   
		          if (files[0].getName().endsWith("pdf")) {
		        	  try { new OpenFileAction(Duix.this, files[0]).actionPerformed(null); }
		        	  catch (Exception ex) { ex.printStackTrace(); }
		          }
		      }
		});
		
		createMenuBar();
		refreshActions();
		
		main.setSize(400, 400);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		main.setLocation((int)(screen.getWidth()-main.getWidth())/2, (int)(screen.getHeight()-main.getHeight())/2);
		main.setTitle("Duix");
		main.setVisible(true);
	}
	
	protected void createMenuBar() {
		MenuBar menuBar = new MenuBar();
		
		// File menu
		Menu fileMenu = new PopupMenu("File");
		fileMenu.add(new ActionMenuItem(this, new OpenFileAction(this, null)));
		menuBar.add(fileMenu);
		
		// Slideshow menu
		Menu slideshowMenu = new PopupMenu("Slideshow");
		slideshowMenu.add(new ActionMenuItem(this, new StartSlideshowAction(this)));
		slideshowMenu.add(new ActionMenuItem(this, new ResumeSlideshowAction(this)));
		slideshowMenu.addSeparator();
		fullscreenMenuItem = new CheckboxMenuItem("Fullscreen", true);
		slideshowMenu.add(fullscreenMenuItem);
		swapScreensMenuItem = new CheckboxMenuItem("Swap screens", false);
		slideshowMenu.add(swapScreensMenuItem);
		menuBar.add(slideshowMenu);
		
		main.setMenuBar(menuBar);
	}
	
	public SlidePanel getPreviewPanel() {
		return previewPanel;
	}
	
	public void setSlideshow(Slideshow slideshow) {
		this.slideshow = slideshow;
		previewPanel = new SlidePanel(slideshow.getDocument(), slideshow.hasNotes(), true);
		dropTarget.removeAll();
		dropTarget.add(previewPanel, BorderLayout.CENTER);
		dropTarget.updateUI();
	}
	
	public Slideshow getSlideshow() {
		return slideshow;
	}
	
	public List<ActionMenuItem> getActionMenuItems() {
		return actionMenuItems;
	}

	public void refreshActions() {
		for (ActionMenuItem item : getActionMenuItems()) {
			item.setEnabled(item.getAction().isEnabled());
		}
	}
	
	public boolean isFullscreen() {
		return fullscreenMenuItem.getState();
	}

	public boolean isSwapScreeens() {
		return swapScreensMenuItem.getState();
	}
	
}
