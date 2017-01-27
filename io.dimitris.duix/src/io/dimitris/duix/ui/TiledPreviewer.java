package io.dimitris.duix.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

import io.dimitris.duix.PDFSlide;
import io.dimitris.duix.Slide;
import io.dimitris.duix.SlidePanel;
import io.dimitris.duix.Slideshow;

public class TiledPreviewer extends JFrame {
	
	protected List<SlidePanel> slidePanels = new ArrayList<SlidePanel>();
	protected GridLayout gridLayout = new GridLayout(0, 3);
	protected JPanel rootPanel = null;
	
	public static void main(String[] args) throws Exception {
		System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
		TiledPreviewer previewer = new TiledPreviewer();
		//previewer.open(new File("/Users/dkolovos/git/models16-10-year-award/models16-10-year-award.pdf"));
		previewer.open(new File("/Users/dkolovos/Dropbox/MODE/EMF-GMF.pdf"));
		previewer.setSize(400, 400);
		previewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		previewer.setVisible(true);
	}
	
	protected Slideshow slideshow;
	
	public void open (File file) throws Exception {
		
		
		MacUtils.makeWindowLeopardStyle(getRootPane());
		UnifiedToolBar toolbar = new UnifiedToolBar();
		toolbar.installWindowDraggerOnWindow(this);
		
		slideshow = new Slideshow(file);
		
		getRootPane().setLayout(new BorderLayout());
		
		rootPanel = new JPanel();
		gridLayout.setHgap(5);
		gridLayout.setVgap(5);
		rootPanel.setLayout(gridLayout);
		getRootPane().add(new JScrollPane(rootPanel), BorderLayout.CENTER);
		
		int i = 0;
		slidePanels.clear();
		rootPanel.removeAll();
		for (Slide slide : slideshow.getSlides()) {
			if (slide instanceof PDFSlide) ((PDFSlide) slide).setBackground(rootPanel.getBackground());
			SlidePanel slidePanel = new SlidePanel(slideshow.getSlides());
			slidePanel.setMinimumSize(new Dimension(0, 300));
			slidePanel.setPreferredSize(slidePanel.getMinimumSize());
			slidePanel.setBackground(Color.WHITE);
			rootPanel.add(slidePanel);
			slidePanels.add(slidePanel);
			slidePanel.goToSlide(i);
			i++;
		}
		
		toolbar.addComponentToLeft(createJSlider("Row Height", 100, 500, new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				for (SlidePanel slidePanel : slidePanels) {
					slidePanel.setMinimumSize(new Dimension(0, ((JSlider)e.getSource()).getValue()));
					slidePanel.setPreferredSize(slidePanel.getMinimumSize());
				}
				rootPanel.updateUI();
			}
		}));
		toolbar.addComponentToLeft(createJSlider("Columns", 1, 10, new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				gridLayout.setColumns(((JSlider)e.getSource()).getValue());
				rootPanel.updateUI();
			}
		}));
		
		getRootPane().add(toolbar.getComponent(), BorderLayout.NORTH);
		getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
		
		this.pack();
	}
	
	protected JComponent createJSlider(String title, int min, int max, ChangeListener listener) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JSlider slider = new JSlider(min, max);
		//slider.setPaintLabels(true);
		//slider.setPaintTrack(true);
		slider.setPaintTicks(true);
		slider.addChangeListener(listener);
		panel.add(new JLabel(title, JLabel.CENTER), BorderLayout.NORTH);
		panel.add(slider, BorderLayout.CENTER);
		return panel;
	}
	
}
