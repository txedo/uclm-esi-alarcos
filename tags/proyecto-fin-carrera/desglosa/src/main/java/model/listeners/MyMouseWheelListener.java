package model.listeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import model.gl.GLCamera;

public class MyMouseWheelListener implements MouseWheelListener {

    private GLCamera cam;

    public MyMouseWheelListener(GLCamera c) {
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
