package model.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import model.gl.knowledge.Camera;


public class MyMouseWheelListener implements MouseWheelListener {
	
	private Camera cam;

	public MyMouseWheelListener(Camera c) {
		this.cam = c;
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            cam.zoomIn();
        } else {
            cam.zoomOut();
        }
	}

}
