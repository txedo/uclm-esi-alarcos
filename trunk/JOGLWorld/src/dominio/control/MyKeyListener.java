package dominio.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dominio.conocimiento.Camera;

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
				cam.zoomIn();
				System.out.println("+");
				break;
			case KeyEvent.VK_SUBTRACT:
				cam.zoomOut();
				System.out.println("-");
				break;
			case KeyEvent.VK_LEFT:
				if (e.isControlDown()) {
					cam.rotateLeft();
					System.out.println("Ctrl + <-");
				}
				else if (e.isAltDown()) {
					cam.rotateLeftAround();
					System.out.println("Alt + <-");
				}
				else {
					cam.strafeLeft();
					System.out.println("<-\t");
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (e.isControlDown()) {
					cam.rotateRight();
					System.out.println("Ctrl + ->");
				}
				else if (e.isAltDown()) {
					cam.rotateRightAround();
					System.out.println("Alt + ->");
				}
				else {
					cam.strafeRight();
					System.out.println("->");
				}
				break;
			case KeyEvent.VK_UP:
				if (e.isControlDown()) {
					cam.lookUp();
					System.out.println("Ctrl + A\t");
				}
				else if (e.isAltDown()) {
					cam.rotateUpAround();
					System.out.println("Alt + A");
				}
				else {
					cam.moveForward();
					System.out.println("A");
				}
				break;
			case KeyEvent.VK_DOWN:
				if (e.isControlDown()) {
					cam.lookDown();
					System.out.println("Ctrl + V");
				}
				else if (e.isAltDown()) {
					cam.rotateDownAround();
					System.out.println("Alt + V");
				}
				else {
					cam.moveBackward();
					System.out.println("V");
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
