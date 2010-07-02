package dominio.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	
	Drawer d;

	public MyKeyListener(Drawer drawer) {
		this.d = drawer;
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
			d.getCam().zoomIn();
			System.out.println("+");
		} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			d.getCam().zoomOut();
			System.out.println("-");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (e.isControlDown()) {
				d.getCam().rotateLeft();
				System.out.println("Ctrl + <-");
			}
			else {
				d.getCam().moveLeft();
				System.out.println("<-\t");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (e.isControlDown()) {
				d.getCam().rotateRight();
				System.out.println("Ctrl + ->");
			}
			else {
				d.getCam().moveRight();
				System.out.println("->");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (e.isControlDown()) {
				d.getCam().rotateUp();
				System.out.println("Ctrl + A\t");
			}
			else {
				d.getCam().moveForward();
				System.out.println("A");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (e.isControlDown()) {
				d.getCam().rotateDown();
				System.out.println("Ctrl + V");
			}
			else {
				d.getCam().moveBackward();
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
