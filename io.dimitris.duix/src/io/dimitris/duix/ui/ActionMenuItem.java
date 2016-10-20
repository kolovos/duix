package io.dimitris.duix.ui;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
class ActionMenuItem extends MenuItem {
		
	protected AbstractAction action;
	protected App app;
	
	public ActionMenuItem(final App app, final AbstractAction action) {
		super("" + action.getValue(AbstractAction.NAME));
		this.app = app;
		app.getActionMenuItems().add(this);
		this.action = action;
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(null);
				app.refreshActions();
			}
		});
	}
	
	public AbstractAction getAction() {
		return action;
	}
	
}