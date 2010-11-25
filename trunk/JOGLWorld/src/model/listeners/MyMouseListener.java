package model.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.gl.control.GLDrawer;
import model.gl.knowledge.IViewLevels;


public class MyMouseListener implements MouseListener {

	private GLDrawer drawer;

	public MyMouseListener(GLDrawer d) {
		this.drawer = d;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2){	// Double click
			this.drawer.setViewLevel(IViewLevels.NODE_LEVEL);
		} else {						// Click
			this.drawer.getPickPoint().setX((float)e.getPoint().getX());
			this.drawer.getPickPoint().setY((float)e.getPoint().getY());
			this.drawer.setSelectionMode(true);
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
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("released");
	}

}
