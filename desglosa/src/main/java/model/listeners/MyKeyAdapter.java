package model.listeners;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

import model.gl.GLDrawer;
import model.gl.control.EViewLevels;

public class MyKeyAdapter extends KeyAdapter {
	
	private GLDrawer drawer;

	public MyKeyAdapter(GLDrawer d) {
		this.drawer = d;
	}

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
			case KeyEvent.VK_2:
				this.drawer.setViewLevel(EViewLevels.MetricIndicatorLevel);
				break;
			case KeyEvent.VK_3:
				this.drawer.setViewLevel(EViewLevels.ProjectLevel);
				break;
			case KeyEvent.VK_4:
				this.drawer.setViewLevel(EViewLevels.FactoryLevel);
				break;
			case KeyEvent.VK_F10:
				this.drawer.setStencilShadow(!this.drawer.isStencilShadow());
				System.err.println("Stencil shadow: " + this.drawer.isStencilShadow());
				break;
			case KeyEvent.VK_F11:
				this.drawer.setRenderShadow(!this.drawer.isRenderShadow());
				System.err.println("Render shadow: " + this.drawer.isRenderShadow());
				break;
			case KeyEvent.VK_F12:
				this.drawer.setDebugMode(!this.drawer.isDebugMode());
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

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
