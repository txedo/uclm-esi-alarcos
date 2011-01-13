package model.listeners;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.gl.knowledge.Camera;
import model.knowledge.Vector2f;

public class MyMouseMotionListener implements MouseMotionListener {

	private Camera cam;
	private Vector2f currentMousePosition;
	private Vector2f lastMousePosition;

	public MyMouseMotionListener(Camera c) {
		this.cam = c;
		this.currentMousePosition = new Vector2f();
		this.lastMousePosition = new Vector2f();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.currentMousePosition.setX((float) e.getPoint().getX());
		this.currentMousePosition.setY((float) e.getPoint().getY());
		if (this.cam.isMousing()) {
			float nXDiff = this.currentMousePosition.getX()	- this.lastMousePosition.getX();
			float nYDiff = this.currentMousePosition.getY()	- this.lastMousePosition.getY();
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0 && (e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
				if (nYDiff < 0) this.cam.moveForward();
				else if (nYDiff > 0) this.cam.moveBackward();

				if (nXDiff < 0) this.cam.strafeLeft();
				else if (nXDiff > 0) this.cam.strafeRight();
			} else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				if (nYDiff < 0) this.cam.lookUp();
				else if (nYDiff > 0) this.cam.lookDown();

				if (nXDiff < 0) this.cam.rotateLeft();
				else if (nXDiff > 0) this.cam.rotateRight();
			}
		}
		this.lastMousePosition.setX(this.currentMousePosition.getX());
		this.lastMousePosition.setY(this.currentMousePosition.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
