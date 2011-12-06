package presentacion;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;

import com.sun.opengl.util.Animator;

import dominio.control.Drawer;

public class Window extends Frame {
    protected static GLCanvas canvas;
    protected static Animator animator;
	
    public Window() {
	    canvas = new GLCanvas();

	    canvas.addGLEventListener(new Drawer());
	    canvas.setFocusable(true);
	    canvas.requestFocus();
	    this.add(canvas);
	    this.setSize(800, 600);
	    animator = new Animator(canvas);
	    this.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	          new Thread(new Runnable() {
	              public void run() {
	                animator.stop();
	                System.exit(0);
	              }
	            }).start();
	        }
	      });
	    this.setVisible(true);
	    animator.start();
    }
}
