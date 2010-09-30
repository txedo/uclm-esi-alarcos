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
import dominio.conocimiento.IViewLevels;
import dominio.conocimiento.Node;
import dominio.conocimiento.Spotlight;
import dominio.conocimiento.Tower;
import dominio.conocimiento.Vector3f;

public class Drawer implements GLEventListener, IConstantes, IViewLevels {
	
	private Vector<Figure> towers;
	private Vector<Figure> nodes;
	private Camera cam;
	private Spotlight spotlight;
	
	private int viewLevel = NODE_LEVEL;
	
	private GL gl;
	private GLU glu;
	
	Vector3f aux = new Vector3f();
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
		gl.glLoadIdentity();
		
		switch (viewLevel) {
			case NODE_LEVEL:
				cam.render(glu);
				spotlight.render(cam.getPosition(), cam.getViewDir());
				drawNodes();
				break;
			case TOWER_LEVEL:
				cam.render(glu);
				spotlight.render(cam.getPosition(), cam.getViewDir());
				drawTowers();
				break;
		}

		gl.glFlush();
	}

	@Override
	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable glDrawable) {		
		this.gl = glDrawable.getGL();
		this.glu = new GLU();
		
		this.viewLevel = NODE_LEVEL;
		
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);		// Really Nice Perspective Calculations
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);			// White Background
		gl.glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
		gl.glClearDepth(1.0f);								// Depth Buffer Setup
		gl.glDepthFunc(GL.GL_LEQUAL);						// The Type Of Depth Testing To Do
		gl.glShadeModel(GL.GL_SMOOTH);						// Enable Smooth Shading
		// Configuración para obtener un antialiasing en las líneas
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		
		// Creamos una cámara y un foco de luz
		cam = new Camera(0.0f, 10.0f, 0.0f, 1.0f, -1.0f, 1.0f);
		cam.render(glu);
		spotlight = new Spotlight(gl, 1.0f, 1.0f, 1.0f);
		spotlight.render(cam.getPosition(), cam.getViewDir());
		// Habilitamos el color natural de los materiales
		gl.glEnable(GL.GL_COLOR_MATERIAL);

		// Configuramos los parámetros del mundo
		setupNodes();
		
		// Añadimos los listener de teclado y ratón
		glDrawable.addKeyListener(new MyKeyListener(this.cam));
		glDrawable.addMouseListener(new MyMouseListener(this.cam));
		glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.cam));
		glDrawable.addMouseMotionListener(new MyMouseMotionListener(this.cam));
	}

	@Override
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
			int height) {
		// Qué acción realizar cuando se redimensiona la ventana
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
	
	private void setupNodes() {
		nodes = new Vector<Figure>();
		Random r = new Random();
		Color c;
		c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
		Node n = new Node(gl, 0.0f, 0.0f, 1.0f, c);
	}
	
	private void drawNodes () {
		this.drawFloor();
		
		for (Figure f : nodes) {
			f.draw();
		}
	}
	
	private void setupTowers() {
		towers = new Vector<Figure>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < 1000; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (gl, r.nextFloat()*100,r.nextFloat()*100,r.nextFloat(),r.nextFloat(),r.nextFloat()*10,c);
			towers.add(t);
		}
	}
	
	public void drawTowers() {
		this.drawFloor();
		
		for (Figure f : towers) {
			f.draw();
		}
	}
	
	private void drawFloor () {
		gl.glColor4f(0.3f, 0.3f, 0.3f, 0.3f);
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_POLYGON);	
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(100, 0, 0);
			gl.glVertex3f(100, 0, 100);
			gl.glVertex3f(0, 0, 100);
		gl.glEnd();
	}

}
