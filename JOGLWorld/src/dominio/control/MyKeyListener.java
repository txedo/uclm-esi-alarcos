package dominio.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dominio.conocimiento.Camara;

public class MyKeyListener implements KeyListener {
	
	Camara cam;

	public MyKeyListener(Camara c) {
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
				cam.rotacionIzquierda();
				System.out.println("Ctrl + <-");
			}
			else if (e.isAltDown()) {
				System.out.println("Alt + <-");
			}
			else {
				cam.moverIzquierda();
				System.out.println("<-\t");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (e.isControlDown()) {
				cam.rotacionDerecha();
				System.out.println("Ctrl + ->");
			}
			else if (e.isAltDown()) {
				System.out.println("Alt + ->");
			}
			else {
				cam.moverDerecha();
				System.out.println("->");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (e.isControlDown()) {
				cam.rotacionArriba();
				System.out.println("Ctrl + A\t");
			}
			else {
				cam.moverAdelante();
				System.out.println("A");
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (e.isControlDown()) {
				cam.rotacionAbajo();
				System.out.println("Ctrl + V");
			}
			else {
				cam.moverAtras();
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
