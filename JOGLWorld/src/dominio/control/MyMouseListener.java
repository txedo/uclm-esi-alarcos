package dominio.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import dominio.conocimiento.Camera;

public class MyMouseListener implements MouseListener {

	private Camera cam;
	private Drawer drawer;

	public MyMouseListener(Camera c, Drawer d) {
		this.cam = c;
		this.drawer = d;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2){
			System.out.println("doble click");
			this.drawer.setNodeLevel();
		} else {
			System.out.println ("click");
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
