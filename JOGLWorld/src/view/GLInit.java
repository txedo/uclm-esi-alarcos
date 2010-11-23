package view;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import model.gl.control.GLDrawer;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

public class GLInit {
	private static GLCanvas canvas;
	private static Animator animator;
	
	public static Canvas getGLCanvas () {
		return canvas;
	}
	
	public static void init() {
		// Creating an object to manipulate OpenGL parameters.
		GLCapabilities capabilities = new GLCapabilities();
 
		// Setting some OpenGL parameters.
		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);
		
		canvas = new GLCanvas();
	    canvas.addGLEventListener(new GLDrawer());
	    canvas.setFocusable(true);
	    canvas.requestFocus();
	    
	    // Creating an animator that will redraw the scene 40 times per second
	    animator = new FPSAnimator(40);
	    // Registering the canvas to the animator
	    animator.add(canvas);
	}
	
	public static void setContext (Frame mainFrame, Container container, Object containerConstraint) {
		container.add(canvas, containerConstraint);
		canvas.setSize(container.getSize());
	    mainFrame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          new Thread(new Runnable() {
	              public void run() {
	                animator.stop();
	                System.exit(0);
	              }
	            }).start();
	        }
	      });
	    mainFrame.setVisible(true);
	    // Starting the animator
	    animator.start();
	}
}
