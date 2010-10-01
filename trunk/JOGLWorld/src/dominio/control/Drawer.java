package dominio.control;

import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import dominio.conocimiento.Camera;
import dominio.conocimiento.Color;
import dominio.conocimiento.Edge;
import dominio.conocimiento.Figure;
import dominio.conocimiento.IConstantes;
import dominio.conocimiento.IEdge;
import dominio.conocimiento.IViewLevels;
import dominio.conocimiento.Node;
import dominio.conocimiento.Spotlight;
import dominio.conocimiento.Tower;
import dominio.conocimiento.Vector3f;

public class Drawer implements GLEventListener, IConstantes, IViewLevels {
	private Vector<Figure> towers;
	private Vector<Figure> nodes;
	private Vector<Figure> edges;
	private Camera cam;
	private Spotlight spotlight;
	
	private int viewLevel = NODE_LEVEL;
	
	private int width;
	private int height;
	
	private GL gl;
	private GLU glu;
	
	Vector3f aux = new Vector3f();
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
		gl.glLoadIdentity();
		
		switch (viewLevel) {
			case NODE_LEVEL:
				this.beginOrtho();
					drawNodes();
					drawEdges();
				this.loadMatrix();
				break;
			case TOWER_LEVEL:
				this.beginPerspective();
					cam.render(glu);
					spotlight.render(cam.getPosition(), cam.getViewDir());
					drawTowers();
				this.loadMatrix();
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
		spotlight = new Spotlight(gl, 1.0f, 1.0f, 1.0f);
		// Habilitamos el color natural de los materiales
		gl.glEnable(GL.GL_COLOR_MATERIAL);

		// Configuramos los parámetros del mundo
		setupNodes();
		setupEdges();
		setupTowers();
		
		// Añadimos los listener de teclado y ratón
		glDrawable.addKeyListener(new MyKeyListener(this.cam, this));
		glDrawable.addMouseListener(new MyMouseListener(this.cam));
		glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.cam));
		glDrawable.addMouseMotionListener(new MyMouseMotionListener(this.cam));
	}

	@Override
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
			int height) {
		this.width = width;
		this.height = height;
		// Qué acción realizar cuando se redimensiona la ventana
		gl.setSwapInterval(1);

		// lower left corner (0,0); upper right corner (width,height)
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		
		switch (viewLevel) {
			case NODE_LEVEL:
				glu.gluOrtho2D(0.0, (double) width, 0.0, (double) height);
				break;
			case TOWER_LEVEL:
				float h = (float) width / (float) height;
				glu.gluPerspective(60.0f, h, 0.1f, 1000.0f);
				break;
		}
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	public static float NEAR = -1;
	public static float FAR = 1;

	/**
	 * Switch to orthographic projection<BR>
	 * The current projection and modelview matrix are saved (push).<BR>
	 * You can loads projection and modelview matrices with endOrtho
	 * @see #endOrtho()
	 */
	public void beginOrtho()
	{
	    /*
	     * We save the current projection matrix and we define a viewing volume
	     * in the orthographic mode.
	     * Projection matrix stack defines how the scene is projected to the screen.
	     */
	    gl.glMatrixMode(GL.GL_PROJECTION);   //select the Projection matrix
	    gl.glPushMatrix();                   //save the current projection matrix
	    gl.glLoadIdentity();                 //reset the current projection matrix to creates a new Orthographic projection
	    //Creates a new orthographic viewing volume
	    glu.gluOrtho2D(0.0, 10.0, 0.0, 10.0);
	   
	    /*
	     * Select, save and reset the modelview matrix.
	     * Modelview matrix stack store transformation like translation, rotation ...
	     */
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	}
	
	public void beginPerspective()
	{
	    /*
	     * We save the current projection matrix and we define a viewing volume
	     * in the orthographic mode.
	     * Projection matrix stack defines how the scene is projected to the screen.
	     */
	    gl.glMatrixMode(GL.GL_PROJECTION);   //select the Projection matrix
	    gl.glPushMatrix();                   //save the current projection matrix
	    gl.glLoadIdentity();                 //reset the current projection matrix to creates a new Orthographic projection
	    //Creates a new orthographic viewing volume
		float h = (float) width / (float) height;
		glu.gluPerspective(60.0f, h, 0.1f, 1000.0f);
	    /*
	     * Select, save and reset the modelview matrix.
	     * Modelview matrix stack store transformation like translation, rotation ...
	     */
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    gl.glPushMatrix();
	    gl.glLoadIdentity();
	}

	/**
	 * Load projection and modelview matrices previously saved by the method beginOrtho
	 * @see #beginOrtho()
	 */
	public void loadMatrix()
	{
	    //Select the Projection matrix stack
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    //Load the previous Projection matrix (Generaly, it is a Perspective projection)
	    gl.glPopMatrix();
	    
	    //Select the Modelview matrix stack
	    gl.glMatrixMode(GL.GL_MODELVIEW);
	    //Load the previous Modelview matrix
	    gl.glPopMatrix();
	}
	
	private void setupNodes() {
		Node n;
		nodes = new Vector<Figure>();
		Color c = new Color (0.0f, 0.0f, 0.0f);
		n = new Node(gl, 0.0f, 0.0f, 1.0f, c);
		nodes.add(n);
		n = new Node(gl, 2.0f, 0.0f, 1.0f, c);
		nodes.add(n);
		n = new Node(gl, 0.0f, 2.0f, 1.0f, c);
		nodes.add(n);
		n = new Node(gl, 2.0f, 2.0f, 1.0f, c);
		nodes.add(n);
	}
	
	private void drawNodes () {
		for (Figure f : nodes) {
			f.draw();
		}
	}
	
	private void setupEdges () {
		Edge e;
		edges = new Vector<Figure>();
		Color c = new Color (0.0f, 0.0f, 0.0f);
		e = new Edge(gl);
		e.connectNodes((Node)nodes.get(0), (Node)nodes.get(1));
		e.setType(IEdge.DOTTED);
		edges.add(e);
		e = new Edge(gl);
		e.connectNodes((Node)nodes.get(0), (Node)nodes.get(2));
		e.setType(IEdge.DASHED);
		edges.add(e);
		e = new Edge(gl);
		e.connectNodes((Node)nodes.get(1), (Node)nodes.get(2));
		e.setType(IEdge.DOT_AND_DASH);
		edges.add(e);
		e = new Edge(gl);
		e.connectNodes((Node)nodes.get(2), (Node)nodes.get(3));
		e.setType(IEdge.SOLID);
		edges.add(e);
	}
	
	private void drawEdges () {
		for (Figure f : edges) {
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

	public int getViewLevel() {
		return viewLevel;
	}

	public void setViewLevel(int viewLevel) {
		this.viewLevel = viewLevel;
	}

}
