package dominio.control;


import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import dominio.conocimiento.Camera;
import dominio.conocimiento.Color;
import dominio.conocimiento.Figure;
import dominio.conocimiento.IConstantes;
import dominio.conocimiento.Scene;
import dominio.conocimiento.Spotlight;
import dominio.conocimiento.Tower;
import dominio.conocimiento.Vector3f;

public class Drawer implements GLEventListener, IConstantes {
	
	private Vector<Figure> torres;
	private Camera cam;
	private Scene sce;
	private Spotlight spotlight;
	
	Vector3f aux = new Vector3f();
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		final GLU glu = new GLU();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();
		cam.render(glu);
		spotlight.render(cam.getPosition(), cam.getViewDir());

		drawWorld(glDrawable);
		gl.glFlush();
	}

	@Override
	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable glDrawable) {		
		final GL gl = glDrawable.getGL();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);						// White Background
		gl.glClearDepth(1.0f);											// Depth Buffer Setup
		gl.glShadeModel(GL.GL_SMOOTH);									// Enable Smooth Shading
		gl.glEnable(GL.GL_DEPTH_TEST);									// Enables Depth Testing
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC0_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		gl.glDepthFunc(GL.GL_LEQUAL);									// The Type Of Depth Testing To Do
		//gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);		// Really Nice Perspective Calculations
		
		cam = new Camera(0.0f, 10.0f, 0.0f, 1.0f, -1.0f, 1.0f);
		spotlight = new Spotlight(gl, 1.0f, 1.0f, 1.0f);
		gl.glEnable(GL.GL_COLOR_MATERIAL);

		// Configuramos los parámetros del mundo
		setupWorld();
		
		// Añadimos los listener de teclado y ratón
		glDrawable.addKeyListener(new MyKeyListener(this.cam));
		glDrawable.addMouseListener(new MyMouseListener(this.cam));
		glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.cam));
		glDrawable.addMouseMotionListener(new MyMouseMotionListener(this.cam));
	}

	private void setupWorld() {
		torres = new Vector<Figure>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < 1000; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (r.nextFloat()*100,r.nextFloat()*100,r.nextFloat(),r.nextFloat()*10,c);
			torres.add(t);
		}
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

		for (Figure f : torres) {
			f.draw(gl);
		}
		
		gl.glColor4f(0.3f, 0.3f, 0.3f, 0.3f);
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_QUADS);	
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(100, 0, 0);
			gl.glVertex3f(100, 0, 100);
			gl.glVertex3f(0, 0, 100);
		gl.glEnd();
	}

	public Camera getCam() {
		return cam;
	}

}
