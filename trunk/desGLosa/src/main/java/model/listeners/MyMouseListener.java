package model.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.gl.control.EViewLevels;
import model.gl.control.GLDrawer;


public class MyMouseListener implements MouseListener {

	private GLDrawer drawer;

	public MyMouseListener(GLDrawer d) {
		this.drawer = d;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2){	// Double click
			this.drawer.setViewLevel(EViewLevels.MetricIndicatorLevel);
		} else {						// Click
			if (e.getButton() == e.BUTTON1) {
				this.drawer.getPickPoint().setX((float)e.getPoint().getX());
				this.drawer.getPickPoint().setY((float)e.getPoint().getY());
				this.drawer.setSelectionMode(true);
			} else if (e.getButton() == e.BUTTON3) {
				this.drawer.setViewLevel(EViewLevels.MapLevel);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		this.drawer.getCamera().setMousing(true);
	}

	public void mouseReleased(MouseEvent e) {
		this.drawer.getCamera().setMousing(false);
	}

}