package dominio.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dominio.conocimiento.Camera;

public class MyKeyListener implements KeyListener {
	
	Camera cam;

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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// TODO exit();
			System.out.println("ESC");
		} else if (e.getKeyCode() == KeyEvent.VK_ADD) {
			cam.zoomIn();
			System.out.println("+");
		} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			cam.zoomOut();
			System.out.println("-");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (e.isControlDown()) {
				cam.rotateLeft();
				System.out.println("Ctrl + <-");
			}
			else {
				cam.moveLeft();
				System.out.println("<-\t");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (e.isControlDown()) {
				cam.rotateRight();
				System.out.println("Ctrl + ->");
			}
			else {
				cam.moveRight();
				System.out.println("->");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (e.isControlDown()) {
				cam.rotateUp();
				System.out.println("Ctrl + A\t");
			}
			else {
				cam.moveForward();
				System.out.println("A");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (e.isControlDown()) {
				cam.rotateDown();
				System.out.println("Ctrl + V");
			}
			else {
				cam.moveBackward();
				System.out.println("V");
			}
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
