package model.listeners;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import presentation.IAppCore;

public class MyAppMouseListener implements MouseListener {
	private IAppCore core;
	private Component container;

	public MyAppMouseListener(IAppCore core, Component container) {
		this.core = core;
		this.container = container;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		if (core.isSettingCoordinates()) {
			container.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		}
	}

	public void mouseExited(MouseEvent e) {
		if (core.isSettingCoordinates()) {
			container.setCursor(Cursor.getDefaultCursor());
		}
	}

}
