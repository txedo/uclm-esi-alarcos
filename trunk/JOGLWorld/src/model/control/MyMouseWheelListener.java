package model.control;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import model.knowledge.Camera;


public class MyMouseWheelListener implements MouseWheelListener {
	
	private Camera cam;

	public MyMouseWheelListener(Camera c) {
		this.cam = c;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		System.out.println("wheel moved");
        int notches = e.getWheelRotation();
        if (notches < 0) {
            cam.zoomIn();
        } else {
            cam.zoomOut();
        }
	}

}
