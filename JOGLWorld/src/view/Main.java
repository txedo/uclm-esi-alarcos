package view;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import model.control.Drawer;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;


public class Main extends Frame {
	
	private static final long serialVersionUID = -8016019048808055367L;
	protected static GLCanvas canvas;
	protected static Animator animator;
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public static void main(String[] args) {
		// Creating an object to manipulate OpenGL parameters.
		GLCapabilities capabilities = new GLCapabilities();
 
		// Setting some OpenGL parameters.
		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);

		Frame frame = new Frame("");
	    canvas = new GLCanvas();

	    frame.add(canvas);
	    frame.setSize(WIDTH, HEIGHT);
	    canvas.addGLEventListener(new Drawer());
	    canvas.setFocusable(true);
	    canvas.requestFocus();
	    // Creating an animator that will redraw the scene 40 times per second
	    animator = new FPSAnimator(40);
	    // Registering the canvas to the animator
	    animator.add(canvas);
	    //animator = new Animator(canvas);
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
	    // Starting the animator
	    animator.start();
	}
}
