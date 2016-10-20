package io.dimitris.jdspdfviewer.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public abstract class AppAction extends AbstractAction {
	
	protected App app;
	
	public AppAction(App app, String label) {
		super(label);
		this.app = app;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			actionPerformed();
			app.refreshActions();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public abstract void actionPerformed() throws Exception;
	
}
