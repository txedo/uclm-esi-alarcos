package dominio.control;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

import dominio.conocimiento.Color;
import dominio.conocimiento.Tower;

public class Drawer implements GLEventListener {
	
	private Color c1, c2;
	private Tower t1, t2;
	
	private float xrot;
	private float yrot;
	private float sceneroty;
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		drawScene(glDrawable);
	}

	@Override
	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		gl.glShadeModel(GL.GL_FLAT);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // fondo blanco
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		// Añadir el listener de teclado
		glDrawable.addKeyListener(new MyKeyListener(this));
		// Añadir los listener de ratón
		glDrawable.addMouseListener(new MyMouseListener());
		glDrawable.addMouseMotionListener(new MyMouseMotionListener());
		
		//TODO SetupWorld();
		c1 = new Color(1.0f, 0.0f, 0.0f);
		t1 = new Tower(0.0f, 0.0f, 1.0f, 2.0f, c1);
		c2 = new Color(0.0f, 1.0f, 0.0f);
		t2 = new Tower(-1.0f, -1.0f, 1.0f, 2.0f, c2);
		
		xrot = 0.0f;
		yrot = 0.0f;
		sceneroty = 360.0f - yrot;
	}

	@Override
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
			int height) {
		// Qué acción realizar cuando se redimensiona la ventana
		final GL gl = glDrawable.getGL();
		final GLU glu = new GLU();
		
		gl.setSwapInterval(1);

		// lower left corner (0,0); upper right corner (width,height)
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		float h = (float) width / (float) height;
		glu.gluPerspective(60.0f, h, 0.1f, 1000.0f);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	public void drawScene(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		final GLU glu = new GLU();
		sceneroty = 360.0f - yrot;
		
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
//		gl.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glRotatef(sceneroty, 0.0f, 1.0f, 0.0f);
//		gl.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);

		t1.draw(gl);
		t2.draw(gl);
		
//		gl.glLoadIdentity();
//		gl.glTranslatef(0.0f, 0.0f, -5.0f);
//		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.3f);
//		gl.glRectf(-2, -2, 2, 2);
		
		GLUT a = new GLUT();
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		gl.glLoadIdentity();
		glu.gluLookAt(0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		//gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glScalef(1.0f, 1.0f, 5.0f);
		a.glutWireCube(1.0f);
//		gl.glFlush();
	}

	public float getXrot() {
		return xrot;
	}

	public void setXrot(float xrot) {
		this.xrot = xrot;
	}

	public float getYrot() {
		return yrot;
	}

	public void setYrot(float yrot) {
		this.yrot = yrot;
	}

}
