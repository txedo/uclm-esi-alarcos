package model.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.gl.knowledge.Camera;


public class MyMouseMotionListener implements MouseMotionListener {
	
	private Camera cam;

	public MyMouseMotionListener(Camera c) {
		this.cam = c;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("dragged");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
