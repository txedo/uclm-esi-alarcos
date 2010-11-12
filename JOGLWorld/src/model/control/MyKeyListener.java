package model.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.GL;

import model.knowledge.Camera;
import model.knowledge.IViewLevels;
import model.knowledge.Vector3f;


public class MyKeyListener implements KeyListener {
	
	private Camera cam;
	private Drawer d;

	public MyKeyListener(Camera c, Drawer d) {//, Scene s) {
		this.cam = c;
		this.d = d;
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
				//System.out.println("+");
				break;
			case KeyEvent.VK_SUBTRACT:
				cam.zoomOut();
				//System.out.println("-");
				break;
			case KeyEvent.VK_A:
				cam.rotateLeft();
				//System.out.println("Ctrl + <-");
				break;
			case KeyEvent.VK_Q:
				cam.strafeLeft();
				//System.out.println("<-\t");
				break;
			case KeyEvent.VK_D:
				cam.rotateRight();
				//System.out.println("Ctrl + ->");
				break;
			case KeyEvent.VK_E:
				cam.strafeRight();
				//System.out.println("->");
				break;
			case KeyEvent.VK_PAGE_UP:
				cam.lookUp();
				//System.out.println("Ctrl + A\t");
				break;
			case KeyEvent.VK_W:
				cam.moveForward();
				//System.out.println("A");
				break;
			case KeyEvent.VK_PAGE_DOWN:
				cam.lookDown();
				//System.out.println("Ctrl + V");
				break;
			case KeyEvent.VK_S:
				cam.moveBackward();
				//System.out.println("V");
				break;
			case KeyEvent.VK_X:
				cam.lookBackward();
				break;
			case KeyEvent.VK_SPACE:
				cam.moveUp();
				break;
			case KeyEvent.VK_C:
				cam.moveDown();
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
