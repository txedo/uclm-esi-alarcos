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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// TODO exit();
			System.out.println("ESC");
		} else if (e.getKeyCode() == KeyEvent.VK_ADD) {
			d.zoomIn();
			System.out.println("+\t(" + d.getXpos() + "," + d.getYpos() + "," + d.getZpos() + ")");
		} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			d.zoomOut();
			System.out.println("-\t(" + d.getXpos() + "," + d.getYpos() + "," + d.getZpos() + ")");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			d.moveLeft();
			System.out.println("<-\t" + d.getYrot());
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			d.moveRight();
			System.out.println("->\t" + d.getYrot());
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			d.moveUp();
			System.out.println("A\t" + d.getXrot());
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			d.moveDown();
			System.out.println("V\t" + d.getXrot());
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			d.lookUp();
			System.out.println("lookUp\t(" + d.getXsight() + "," + d.getYsight() + "," + d.getZsight() + ")");
		} else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			d.lookDown();
			System.out.println("lookDown\t(" + d.getXsight() + "," + d.getYsight() + "," + d.getZsight() + ")");
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
