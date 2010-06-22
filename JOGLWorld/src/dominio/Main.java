package dominio;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;

import com.sun.opengl.util.Animator;

import dominio.control.Drawer;

public class Main extends Frame {
	
	private static final long serialVersionUID = -8016019048808055367L;
	protected static GLCanvas canvas;
	protected static Animator animator;
	
	public static void main(String[] args) {
		Frame frame = new Frame("");
	    canvas = new GLCanvas();

	    canvas.addGLEventListener(new Drawer());
	    frame.add(canvas);
	    frame.setSize(800, 600);
	    animator = new Animator(canvas);
	    frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          new Thread(new Runnable() {
	              public void run() {
	                animator.stop();
	                System.exit(0);
	              }
	            }).start();
	        }
	      });
	    frame.setVisible(true);
	    animator.start();
	}
}
