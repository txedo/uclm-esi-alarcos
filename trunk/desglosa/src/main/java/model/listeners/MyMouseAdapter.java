package model.listeners;

import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

import model.gl.GLDrawer;
import model.util.Vector2f;


public class MyMouseAdapter extends MouseAdapter {
	private final float MAX_DIFF = 5;
	private final int NO_BUTTON = -1;
	private int button;
	private GLDrawer drawer;
	private Vector2f currentMousePosition;
	private Vector2f lastMousePosition;

	public MyMouseAdapter(GLDrawer d) {
		this.button = NO_BUTTON;
		this.drawer = d;
		this.currentMousePosition = new Vector2f();
		this.lastMousePosition = new Vector2f();
	}
	
	public void mouseClicked(MouseEvent e) {
		switch (e.getButton()) {
			case 1:
				this.drawer.getPickPoint().setX((float)e.getX());
				this.drawer.getPickPoint().setY((float)e.getY());
				this.drawer.setSelectionMode(true, e.getClickCount());
				break;
			default:
				break;
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		this.drawer.getCamera().setMousing(true);
		this.button = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		this.drawer.getCamera().setMousing(false);
		this.button = NO_BUTTON;
	}

	public void mouseWheelMoved(MouseEvent e) {
		System.err.println("wheel moved");
        int notches = e.getWheelRotation();
        if (notches < 0) {
            this.drawer.getCamera().zoomIn();
            System.err.println("wheel moved up");
        } else {
        	this.drawer.getCamera().zoomOut();
        	System.err.println("wheel moved down");
        }
	}

	public void mouseDragged(MouseEvent e) {
		this.currentMousePosition.setX((float) e.getX());
		this.currentMousePosition.setY((float) e.getY());
		
//		int width=0, height=0;
		
		if (this.drawer.getCamera().isMousing()) {
			if (drawer.getViewManager(drawer.getViewLevel()).isThreeDimensional()) {
//				Object source = e.getSource();
//		        if(source instanceof Window) {
//		            Window window = (Window) source;
//		            width=window.getWidth();
//		            height=window.getHeight();
//		        } else if (source instanceof Component) {
//		            Component comp = (Component) source;
//		            width=comp.getWidth();
//		            height=comp.getHeight();
//		        } else {
//		            throw new RuntimeException("Event source neither Window nor Component: "+source);
//		        }
		        
				float nXDiff = this.currentMousePosition.getX()	- this.lastMousePosition.getX();
				float nYDiff = this.currentMousePosition.getY()	- this.lastMousePosition.getY();
				if (nYDiff < -MAX_DIFF) nYDiff = -MAX_DIFF;
				if (nYDiff > MAX_DIFF) nYDiff = MAX_DIFF;
				if (nXDiff < -MAX_DIFF) nXDiff = -MAX_DIFF;
				if (nXDiff > MAX_DIFF) nXDiff = MAX_DIFF;
				
				switch (this.button) {
				case 1:
					if (nYDiff < 0) this.drawer.getCamera().moveForward((int)-nYDiff);
					else if (nYDiff > 0) this.drawer.getCamera().moveBackward((int)nYDiff);

					if (nXDiff < 0) this.drawer.getCamera().strafeLeft((int)-nXDiff);
					else if (nXDiff > 0) this.drawer.getCamera().strafeRight((int)nXDiff);
					break;
				case 3:
					if (nYDiff < 0) this.drawer.getCamera().lookUp((int)-nYDiff);
					else if (nYDiff > 0) this.drawer.getCamera().lookDown((int)nYDiff);

					if (nXDiff < 0) this.drawer.getCamera().rotateLeft((int)-nXDiff);
					else if (nXDiff > 0) this.drawer.getCamera().rotateRight((int)nXDiff);
					break;
				default:
					break;
				}
			}
		}
		this.lastMousePosition.setX(this.currentMousePosition.getX());
		this.lastMousePosition.setY(this.currentMousePosition.getY());
	}
	

}
