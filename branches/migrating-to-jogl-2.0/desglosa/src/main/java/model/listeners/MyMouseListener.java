package model.listeners;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.gl.GLDrawer;
import model.gl.control.EViewLevels;


public class MyMouseListener implements MouseListener {

	private GLDrawer drawer;

	public MyMouseListener(GLDrawer d) {
		this.drawer = d;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2){	// Double click
			this.drawer.setViewLevel(EViewLevels.MetricIndicatorLevel);
//			this.drawer.setViewLevel(EViewLevels.ProjectLevel);
		} else {						// Click
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				this.drawer.getPickPoint().setX((float)e.getPoint().getX());
				this.drawer.getPickPoint().setY((float)e.getPoint().getY());
				this.drawer.setSelectionMode(true);
			} else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
				this.drawer.setViewLevel(EViewLevels.ProjectLevel);
			} else if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
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
