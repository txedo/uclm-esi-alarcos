package model.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.gl.GLSingleton;
import model.gl.control.GLDrawer;

public class MyKeyListener implements KeyListener {
	
	private GLDrawer drawer;

	public MyKeyListener(GLDrawer d) {//, Scene s) {
		this.drawer = d;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		/*
		 * FLECHA_ARRIBA			desplazar hacia delante
		 * FLECHA_ABAJO				desplazar hacia atr�s
		 * FECHA_IZQUIERDA			desplazar a la izquierda
		 * FELCHA_DERECHA			desplazar a la derecha
		 * CTRL + FLECHA_ARRIBA		rotar la c�mara hacia arriba
		 * CTRL + FLECHA_ABAJO		rotar la c�mara hacia abajo
		 * CTRL + FLECHA_IZQUIERDA	rotar la c�mara hacia la izquierda
		 * CTRL + FLECHA_DERECHA	rotar la c�mara hacia la derecha
		 * SUMA						aumentar zoom
		 * RESTA					disminuir zoom
		 */
		switch (e.getKeyCode()) {
			case KeyEvent.VK_7:
//				this.drawer.dim += 0.1f;
				GLSingleton.scale -= 0.1;
				System.out.println("left: " + GLSingleton.scale);
				break;
			case KeyEvent.VK_8:
//				this.drawer.dim -= 0.1f;
				GLSingleton.scale += 0.1;
				System.out.println("left: " + GLSingleton.scale);
				break;
			case KeyEvent.VK_UP:
				this.drawer.position.setY(this.drawer.position.getY()+0.1f);
				break;
			case KeyEvent.VK_DOWN:
				this.drawer.position.setY(this.drawer.position.getY()-0.1f);
				break;
			case KeyEvent.VK_LEFT:
				this.drawer.position.setX(this.drawer.position.getX()-0.1f);
				break;
			case KeyEvent.VK_RIGHT:
				this.drawer.position.setX(this.drawer.position.getX()+0.1f);
				break;
			case KeyEvent.VK_ESCAPE:
				// TODO exit();
				System.out.println("ESC");
				break;
			case KeyEvent.VK_ADD:
				this.drawer.getCamera().zoomIn();
				//System.out.println("+");
				break;
			case KeyEvent.VK_SUBTRACT:
				this.drawer.getCamera().zoomOut();
				//System.out.println("-");
				break;
			case KeyEvent.VK_A:
				this.drawer.getCamera().rotateLeft();
				//System.out.println("Ctrl + <-");
				break;
			case KeyEvent.VK_Q:
				this.drawer.getCamera().strafeLeft();
				//System.out.println("<-\t");
				break;
			case KeyEvent.VK_D:
				this.drawer.getCamera().rotateRight();
				//System.out.println("Ctrl + ->");
				break;
			case KeyEvent.VK_E:
				this.drawer.getCamera().strafeRight();
				//System.out.println("->");
				break;
			case KeyEvent.VK_PAGE_UP:
				this.drawer.getCamera().lookUp();
				//System.out.println("Ctrl + A\t");
				break;
			case KeyEvent.VK_W:
				this.drawer.getCamera().moveForward();
				//System.out.println("A");
				break;
			case KeyEvent.VK_PAGE_DOWN:
				this.drawer.getCamera().lookDown();
				//System.out.println("Ctrl + V");
				break;
			case KeyEvent.VK_S:
				this.drawer.getCamera().moveBackward();
				//System.out.println("V");
				break;
			case KeyEvent.VK_X:
				this.drawer.getCamera().lookBackward();
				break;
			case KeyEvent.VK_SPACE:
				this.drawer.getCamera().moveUp();
				break;
			case KeyEvent.VK_C:
				this.drawer.getCamera().moveDown();
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