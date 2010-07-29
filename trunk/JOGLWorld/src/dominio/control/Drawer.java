package dominio.control;


import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import dominio.conocimiento.Camera;
import dominio.conocimiento.Color;
import dominio.conocimiento.IConstantes;
import dominio.conocimiento.Tower;

public class Drawer implements GLEventListener, IConstantes {
	
	private Vector<Tower> torres;
	
	private float xrot, scenerotx;
	private float yrot, sceneroty;
	private Camera cam;
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		final GLU glu = new GLU();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		scenerotx = 360.0f - xrot;
		sceneroty = 360.0f - yrot;
		
		gl.glLoadIdentity();
		
		cam.render(glu);

		gl.glPushMatrix();
			gl.glRotatef(scenerotx, 1.0f, 0.0f, 0.0f);
			gl.glRotatef(sceneroty, 0.0f, 1.0f, 0.0f);
			drawWorld(glDrawable);
		gl.glPopMatrix();
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
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC0_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		// Configuramos los parámetros del mundo
		setupWorld();
		
		// Añadimos los listener de teclado y ratón
		glDrawable.addKeyListener(new MyKeyListener(this.cam));
		glDrawable.addMouseListener(new MyMouseListener(this.cam));
		glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.cam));
		glDrawable.addMouseMotionListener(new MyMouseMotionListener(this.cam));
	}

	private void setupWorld() {
		torres = new Vector<Tower>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < 1000; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (r.nextFloat()*100,r.nextFloat()*100,r.nextFloat(),r.nextFloat()*10,c);
			torres.add(t);
		}
//		c = new Color(1.0f, 0.0f, 0.0f);
//		torres.add(new Tower(0.0f, 0.0f, 1.0f, 2.0f, c));
//		c = new Color(0.0f, 1.0f, 0.0f);
//		torres.add(new Tower(-1.0f, -1.0f, 1.0f, 2.0f, c));
		
		xrot = yrot = 0.0f;
		scenerotx = 360.0f - xrot;
		sceneroty = 360.0f - yrot;
		
		cam = new Camera();
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
	
	public void drawWorld(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();

		for (Tower t : torres) {
			t.draw(gl);
		}
		
		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.3f);
		gl.glBegin(GL.GL_QUADS);	
			gl.glVertex3f(-2, 0, -2);
			gl.glVertex3f(-2, 0, 2);
			gl.glVertex3f(2, 0, 2);
			gl.glVertex3f(2, 0, -2);
		gl.glEnd();

		gl.glFlush();
	}

	public float getXrot() {
		return xrot;
	}
	
	public float getYrot() {
		return yrot;
	}

	public Camera getCam() {
		return cam;
	}

}
