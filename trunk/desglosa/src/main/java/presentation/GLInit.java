package presentation;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import model.gl.GLDrawer;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;


public class GLInit {
	private static GLCanvas glCanvas;
	private static Animator animator;
	
	public static Canvas getGLCanvas () {
		return glCanvas;
	}
	
	public static Animator getAnimator () {
		return animator;
	}
	
	public static void init() {
		// Creating an object to manipulate OpenGL parameters.
		GLCapabilities capabilities = new GLCapabilities();
 
		// Setting some OpenGL parameters.
		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);
		// Set number of stencil bits in order to have stencil buffer working
		capabilities.setStencilBits(8);
		// Sample buffers are used to get polygon anti-aliasing working
		capabilities.setSampleBuffers(true);
		capabilities.setNumSamples(2);
		
		glCanvas = new GLCanvas(capabilities);
	    glCanvas.addGLEventListener(new GLDrawer());
	    glCanvas.setFocusable(true);
	    glCanvas.requestFocus();
	    
	    // Creating an animator that will redraw the scene 40 times per second
	    animator = new FPSAnimator(glCanvas, 40, true);
	    // Registering the canvas to the animator
	    animator.add(glCanvas);
	}
	
	public static void setContext (Frame mainFrame, Container container, Object containerConstraint) {
		container.add(glCanvas, containerConstraint);
		glCanvas.setSize(container.getSize());
	    mainFrame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	        	// Use a dedicate thread to run the stop() to ensure that the
	        	// animator stops before program exits.
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
