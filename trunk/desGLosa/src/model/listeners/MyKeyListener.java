package model.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.gl.knowledge.Camera;

public class MyKeyListener implements KeyListener {
	
	private Camera cam;

	public MyKeyListener(Camera c) {
		this.cam = c;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * FLECHA_ARRIBA			desplazar hacia delante
		 * FLECHA_ABAJO				desplazar hacia atrás
		 * FECHA_IZQUIERDA			desplazar a la izquierda
		 * FELCHA_DERECHA			desplazar a la derecha
		 * CTRL + FLECHA_ARRIBA		rotar la cámara hacia arriba
		 * CTRL + FLECHA_ABAJO		rotar la cámara hacia abajo
		 * CTRL + FLECHA_IZQUIERDA	rotar la cámara hacia la izquierda
		 * CTRL + FLECHA_DERECHA	rotar la cámara hacia la derecha
		 * SUMA						aumentar zoom
		 * RESTA					disminuir zoom
		 */
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				// TODO exit();
				System.out.println("ESC");
				break;
			case KeyEvent.VK_ADD:
				this.cam.zoomIn();
				//System.out.println("+");
				break;
			case KeyEvent.VK_SUBTRACT:
				this.cam.zoomOut();
				//System.out.println("-");
				break;
			case KeyEvent.VK_A:
				this.cam.rotateLeft();
				//System.out.println("Ctrl + <-");
				break;
			case KeyEvent.VK_Q:
				this.cam.strafeLeft();
				//System.out.println("<-\t");
				break;
			case KeyEvent.VK_D:
				this.cam.rotateRight();
				//System.out.println("Ctrl + ->");
				break;
			case KeyEvent.VK_E:
				this.cam.strafeRight();
				//System.out.println("->");
				break;
			case KeyEvent.VK_PAGE_UP:
				this.cam.lookUp();
				//System.out.println("Ctrl + A\t");
				break;
			case KeyEvent.VK_W:
				this.cam.moveForward();
				//System.out.println("A");
				break;
			case KeyEvent.VK_PAGE_DOWN:
				this.cam.lookDown();
				//System.out.println("Ctrl + V");
				break;
			case KeyEvent.VK_S:
				this.cam.moveBackward();
				//System.out.println("V");
				break;
			case KeyEvent.VK_X:
				this.cam.lookBackward();
				break;
			case KeyEvent.VK_SPACE:
				this.cam.moveUp();
				break;
			case KeyEvent.VK_C:
				this.cam.moveDown();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
