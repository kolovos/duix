package io.dimitris.duix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
		
		addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.exit();
				}
			}
		}, KeyEvent.VK_ESCAPE, KeyEvent.VK_Q);
		
		final Action nextAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.next();;
				}
			}
		};
		addKeyAction(nextAction, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
		
		final Action previousAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.previous();
				}
			}
		};
		addKeyAction(previousAction, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_BACK_SPACE);
		
		addKeyAction(new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (SlideshowCommandListener listener : slideshowCommandListeners) {
					listener.blank();
				}
			}
		}, KeyEvent.VK_B, KeyEvent.VK_PERIOD);
		
		addMouseListener(new DefaultMouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					nextAction.actionPerformed(null);
				}
				else if (e.getButton() == MouseEvent.BUTTON3) {
					previousAction.actionPerformed(null);
				}
			}
		});
		
		addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
					if (e.getUnitsToScroll() < 0) {
						for (int i = 0; i < -e.getUnitsToScroll(); i++) {
							previousAction.actionPerformed(null);
						}
					}
					else {
						for (int i = 0; i < e.getUnitsToScroll(); i++) {
							nextAction.actionPerformed(null);
						}
					}
				}
			}
		});
		
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