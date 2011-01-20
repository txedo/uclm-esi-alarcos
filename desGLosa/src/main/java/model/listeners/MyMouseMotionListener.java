package model.listeners;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import model.gl.control.GLDrawer;
import model.knowledge.Vector2f;

public class MyMouseMotionListener implements MouseMotionListener {

	private final float MAX_DIFF = 5;
	private GLDrawer drawer;
	private Vector2f currentMousePosition;
	private Vector2f lastMousePosition;

	public MyMouseMotionListener(GLDrawer d) {
		this.drawer = d;
		this.currentMousePosition = new Vector2f();
		this.lastMousePosition = new Vector2f();
	}

	public void mouseDragged(MouseEvent e) {
		// TODO comprobar el viewLevel, para ello hay que pasar el drawer, no solo la camara
		this.currentMousePosition.setX((float) e.getPoint().getX());
		this.currentMousePosition.setY((float) e.getPoint().getY());
		
		if (this.drawer.getCamera().isMousing()) {
			switch (this.drawer.getViewLevel()) {
				case TowerLevel:
					float nXDiff = this.currentMousePosition.getX()	- this.lastMousePosition.getX();
					float nYDiff = this.currentMousePosition.getY()	- this.lastMousePosition.getY();
					if (nYDiff < -MAX_DIFF) nYDiff = -MAX_DIFF;
					if (nYDiff > MAX_DIFF) nYDiff = MAX_DIFF;
					if (nXDiff < -MAX_DIFF) nXDiff = -MAX_DIFF;
					if (nXDiff > MAX_DIFF) nXDiff = MAX_DIFF;
					
					if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0 && (e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
						if (nYDiff < 0) this.drawer.getCamera().moveForward((int)-nYDiff);
						else if (nYDiff > 0) this.drawer.getCamera().moveBackward((int)nYDiff);
	
						if (nXDiff < 0) this.drawer.getCamera().strafeLeft((int)-nXDiff);
						else if (nXDiff > 0) this.drawer.getCamera().strafeRight((int)nXDiff);
					} else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
						if (nYDiff < 0) this.drawer.getCamera().lookUp((int)-nYDiff);
						else if (nYDiff > 0) this.drawer.getCamera().lookDown((int)nYDiff);
	
						if (nXDiff < 0) this.drawer.getCamera().rotateLeft((int)-nXDiff);
						else if (nXDiff > 0) this.drawer.getCamera().rotateRight((int)nXDiff);
					}
					break;
			}
		}
		this.lastMousePosition.setX(this.currentMousePosition.getX());
		this.lastMousePosition.setY(this.currentMousePosition.getY());
	}

	public void mouseMoved(MouseEvent e) {
	}

}
