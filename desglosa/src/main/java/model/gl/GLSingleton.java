package model.gl;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.gl2.GLUgl2;

import com.jogamp.opengl.util.gl2.GLUT;

import exceptions.GLSingletonNotInitializedException;

/**
 * http://radio-weblogs.com/0122027/stories/2003/10/20/
 * implementingTheSingletonPatternInJava.html
 * 
 * A utility class of which at most one instance can exist per VM.
 * 
 * Use Singleton.instance() to access this instance.
 */
public class GLSingleton {
    static private boolean initiated;
    static private GLAutoDrawable drawable;
    static private GL2 gl;
    static private GLUgl2 glu;
    static private GLUT glut;

    static public double scale = 1.0;

    /**
     * The constructor could be made private to prevent others from
     * instantiating this class. But this would also make it impossible to
     * create instances of Singleton subclasses.
     */
    protected GLSingleton() {
        initiated = false;
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

    static public void init(GLAutoDrawable glDrawable) {
        drawable = glDrawable;
        gl = glDrawable.getGL().getGL2();
        glu = new GLUgl2();
        glut = new GLUT();
        initiated = true;
    }

    public static boolean isInitiated() {
        return initiated;
    }

    public static GLAutoDrawable getDrawable() {
        return drawable;
    }

    static public GL2 getGL() throws GLSingletonNotInitializedException {
        if (gl == null)
            throw new GLSingletonNotInitializedException();
        else
            return gl;
    }

    static public GLUgl2 getGLU() throws GLSingletonNotInitializedException {
        if (glu == null)
            throw new GLSingletonNotInitializedException();
        else
            return glu;
    }

    static public GLUT getGLUT() throws GLSingletonNotInitializedException {
        if (glut == null)
            throw new GLSingletonNotInitializedException();
        else
            return glut;
    }

}