package io.dimitris.duix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SlideFrame extends JFrame {
	
	protected SlidePanel slidePanel;
	protected List<SlideshowCommandListener> slideshowCommandListeners = new ArrayList<SlideshowCommandListener>();
	
	public SlideFrame(final SlidePanel slidePanel) {
		super();
		this.slidePanel = slidePanel;
		this.setUndecorated(true);
		getRootPane().setLayout(new BorderLayout());
		getRootPane().add(slidePanel, BorderLayout.CENTER);
		getRootPane().setBackground(Color.black);
		addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.exit();
				}
			}
		}, KeyEvent.VK_ESCAPE);
		
		addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.next();;
				}
			}
		}, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
		
		addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.previous();
				}
			}
		}, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_BACK_SPACE);
		
		addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.blank();
				}
			}
		}, KeyEvent.VK_B);
		
	}
	
	public void addSlideshowCommandListener(SlideshowCommandListener listener) {
		slideshowCommandListeners.add(listener);
	}
	
	public boolean removeSlideshowCommandListener(SlideshowCommandListener listener) {
		return slideshowCommandListeners.remove(listener);
	}
	
	public SlidePanel getSlidePanel() {
		return slidePanel;
	}
	
	public void addKeyAction(Action action, int... keyEvents) {
		for (int keyEvent : keyEvents) {
			KeyStroke keyStroke = KeyStroke.getKeyStroke(keyEvent, 0);
			getRootPane().getInputMap().put(keyStroke, keyEvent + "");
			getRootPane().getActionMap().put(keyEvent + "", action);			
		}
	}
	
	public void showOnScreen(int screen) {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();
	    
	    this.setState(Frame.NORMAL);
	    if( screen > -1 && screen < gs.length ) {
	        gs[screen].setFullScreenWindow(this);
	    }
	    else if( gs.length > 0 ) {
	        gs[0].setFullScreenWindow(this);
	    }
	    else {
	        throw new RuntimeException( "No Screens Found" );
	    }
	    
	    // Workaround so that key-bindings work on Mac
	    // See: http://mail.openjdk.java.net/pipermail/macosx-port-dev/2012-November/005109.html
	    this.setVisible(false);
	    this.setVisible(true);
	}
}