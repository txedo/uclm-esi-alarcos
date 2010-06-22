package dominio.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	
	Drawer d;
	private final float Y_ROTATION = 1.5f;

	public MyKeyListener(Drawer drawer) {
		this.d = drawer;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// TODO exit();
			System.out.println("ESC");
		} else if (e.getKeyCode() == KeyEvent.VK_ADD) {
			// TODO zoomIn();
			System.out.println("+");
		} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			// TODO zoomOut();
			System.out.println("-");
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// TODO moveLeft();
			d.setYrot(d.getYrot()-Y_ROTATION);
			System.out.println("<-" + "\t" + d.getYrot());
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// TODO moveRight();
			d.setYrot(d.getYrot()+Y_ROTATION);
			System.out.println("->" + "\t" + d.getYrot());
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
