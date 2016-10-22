package io.dimitris.duix.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.explodingpixels.macwidgets.MacButtonFactory;
import com.explodingpixels.macwidgets.MacUtils;
import com.explodingpixels.macwidgets.UnifiedToolBar;

public class Duix4Mac extends Duix {
	
	protected List<AbstractButton> unifiedToolbarButtons = new ArrayList<AbstractButton>();
	
	public static void main(String[] args) throws Exception {
		System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
		Duix4Mac duix = new Duix4Mac();
		duix.run();
		if (args.length == 1) {
			new OpenFileAction(duix, new File(args[0])).actionPerformed(null);
		}
	}
	
	protected void createMenuBar() {
		super.createMenuBar();
		MacUtils.makeWindowLeopardStyle(main.getRootPane());
		UnifiedToolBar toolbar = new UnifiedToolBar();
		toolbar.installWindowDraggerOnWindow(main);
		
		startSlideshowAction.putValue(Action.SMALL_ICON, new ImageIcon(new File("resources/presentation.png").getAbsolutePath()));
		openFileAction.putValue(Action.SMALL_ICON, new ImageIcon(new File("resources/open.png").getAbsolutePath()));
		resumeSlideshowAction.putValue(Action.SMALL_ICON, new ImageIcon(new File("resources/resume.png").getAbsolutePath()));
		
		toolbar.addComponentToRight(getUnifiedToolBarButton(startSlideshowAction));
		toolbar.addComponentToRight(getUnifiedToolBarButton(resumeSlideshowAction));
		toolbar.addComponentToLeft(getUnifiedToolBarButton(openFileAction));
		
		main.getRootPane().add(toolbar.getComponent(), BorderLayout.NORTH);
		main.getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
		
	}
	
	protected AbstractButton getUnifiedToolBarButton(AbstractAction action) {
		JButton button = new JButton(action);
		button.setActionCommand("pressed");
		Dimension d = new Dimension(48,48);
		button.setPreferredSize(d);
		button.setMinimumSize(d);
		button.setMaximumSize(d);
		button.putClientProperty("JButton.buttonType", "textured");
		AbstractButton unifiedToolBarButton = MacButtonFactory.makeUnifiedToolBarButton(button);
		unifiedToolbarButtons.add(unifiedToolBarButton);
		return unifiedToolBarButton;
	}
	
	@Override
	public void refreshActions() {
		super.refreshActions();
		for (AbstractButton unifiedToolbarButton : unifiedToolbarButtons) {
			unifiedToolbarButton.setEnabled(unifiedToolbarButton.getAction().isEnabled());
		}
	}
	
}
