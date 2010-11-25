package model.gl;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

import exceptions.gl.GLSingletonNotInitializedException;

/**
 * http://radio-weblogs.com/0122027/stories/2003/10/20/
 * implementingTheSingletonPatternInJava.html
 * 
 * A utility class of which at most one instance can exist per VM.
 * 
 * Use Singleton.instance() to access this instance.
 */
public class GLSingleton {
	static private GLAutoDrawable drawable;
	static private GL gl;
	static private GLU glu;
	static private GLUT glut;
	
	static public double scale = 1.0;

	/**
	 * The constructor could be made private to prevent others from
	 * instantiating this class. But this would also make it impossible to
	 * create instances of Singleton subclasses.
	 */
	protected GLSingleton() {
		drawable = null;
		gl = null;
		glu = null;
		glut = null;
	}

	/**
	 * A handle to the unique Singleton instance.
	 */
	static private GLSingleton _instance = null;

	/**
	 * @return The unique instance of this class.
	 */
	static public GLSingleton getInstance() {
		if (null == _instance) {
			_instance = new GLSingleton();
		}
		return _instance;
	}
	
	static public void init (GLAutoDrawable glDrawable) {
		drawable = glDrawable;
		gl = glDrawable.getGL();
		glu = new GLU();
		glut = new GLUT();
	}

	public static GLAutoDrawable getDrawable() {
		return drawable;
	}

	static public GL getGL() throws GLSingletonNotInitializedException {
		if (gl == null) throw new GLSingletonNotInitializedException();
		else return drawable.getGL();
	}

	static public GLU getGLU() throws GLSingletonNotInitializedException {
		if (gl == null) throw new GLSingletonNotInitializedException();
		else return glu;
	}

	static public GLUT getGLUT() throws GLSingletonNotInitializedException {
		if (gl == null) throw new GLSingletonNotInitializedException();
		else return glut;
	}

}