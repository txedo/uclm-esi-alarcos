package presentation;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAnimatorControl;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.FPSAnimator;

import model.gl.GLDrawer;


public class GLInit {
	private static GLCanvas canvas;
	private static GLAnimatorControl animator;
	
	public static Canvas getGLCanvas () {
		return canvas;
	}
	
	public static GLAnimatorControl getAnimator () {
		return animator;
	}
	
	public static void setAnimator(GLAnimatorControl animator) {
		GLInit.animator = animator;
	}
	
	public static void init() {
		// Creating an object to manipulate OpenGL parameters.
		GLProfile.initSingleton(false);
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(profile);
 
		// Setting some OpenGL parameters.
		capabilities.setHardwareAccelerated(true);
		capabilities.setDoubleBuffered(true);
		// Set number of stencil bits in order to have stencil buffer working
		capabilities.setStencilBits(8);
		// Sample buffers are used to get polygon anti-aliasing working
		capabilities.setSampleBuffers(true);
		capabilities.setNumSamples(2);
		
		canvas = new GLCanvas(capabilities);
	    canvas.addGLEventListener(new GLDrawer());
	    canvas.setFocusable(true);
	    canvas.requestFocus();
	    
	    // Creating an animator that will redraw the scene 60 times per second
	    // and registering the canvas to the animator
	    animator = new FPSAnimator(canvas, 60);
	}
	
	public static void setContext (Frame mainFrame, Container container, Object containerConstraint) {
		container.add(canvas, containerConstraint);
		canvas.setSize(container.getSize());
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
