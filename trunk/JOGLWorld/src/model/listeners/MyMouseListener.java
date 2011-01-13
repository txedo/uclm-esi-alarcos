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
	
	@SuppressWarnings("static-access")
	@Override
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

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("entered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("exited");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("pressed");
		this.drawer.getCamera().setMousing(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("released");
		this.drawer.getCamera().setMousing(false);
	}

}
