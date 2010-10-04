package dominio.control;

import java.nio.IntBuffer;
import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.BufferUtil;

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
import dominio.conocimiento.Vector2f;
import dominio.conocimiento.Vector3f;

public class Drawer implements GLEventListener, IConstantes, IViewLevels {
	private Vector<Figure> towers;
	private Vector<Figure> nodes;
	private Vector<Figure> edges;
	private Camera cam;
	private Spotlight spotlight;
	private Vector2f pickPoint = new Vector2f(0, 0);
	
	private int viewLevel = NODE_LEVEL;
	private boolean selectionMode = false;
	private final int BUFFSIZE = 512;
	
	private GL gl;
	private GLU glu;
	
	Vector3f aux = new Vector3f();
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
		gl.glLoadIdentity();
		
		switch (viewLevel) {
			case NODE_LEVEL:
				if (selectionMode) selectNode();
				this.beginOrtho();
					drawNodes();
					drawEdges();
				this.endOrtho();
				glDrawable.swapBuffers();
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
		gl.glDepthFunc(GL.GL_LESS);						// The Type Of Depth Testing To Do
		gl.glShadeModel(GL.GL_SMOOTH);						// Enable Smooth Shading
		// Configuración para obtener un antialiasing en las líneas
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		
		// Creamos una cámara y un foco de luz
		cam = new Camera(-5.0f, 10.0f, -5.0f, 1.0f, -1.0f, 1.0f);
		spotlight = new Spotlight(gl, 1.0f, 1.0f, 1.0f);
		// Habilitamos el color natural de los materiales
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		// Configuramos los parámetros del mundo
		setupNodes();
		setupEdges();
		
		// Añadimos los listener de teclado y ratón
		glDrawable.addKeyListener(new MyKeyListener(this.cam, this));
		glDrawable.addMouseListener(new MyMouseListener(this.cam, this));
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
	
	/**
	 * Switch to orthographic projection
	 * The current projection and modelview matrix are saved (push).
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

	/**
	 * Load projection and modelview matrices previously saved by the method beginOrtho
	 * @see #beginOrtho()
	 */
	public void endOrtho()
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
		int cont = 1;
		for (Figure f : nodes) {
			if (selectionMode) {
				gl.glLoadName(cont);
				cont++;
			}
			f.draw();
		}
	}
	
	public void selectNode () {
		int[] selectBuff = new int[BUFFSIZE];
		IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFFSIZE);
		int hits = 0;
		int[] viewport = new int[4];
		// Save somewhere the info about the current viewport
		gl.glGetIntegerv(gl.GL_VIEWPORT, viewport, 0);
		// Initialize a selection buffer, which will contain data about selected objects
		gl.glSelectBuffer(BUFFSIZE, selectBuffer);
		// Switch to GL_SELECT mode
		gl.glRenderMode(GL.GL_SELECT);
		// Initialize the name stack
		gl.glInitNames();
		// Now fill the stack with one element (or glLoadName will generate an error)
		gl.glPushName(-1);
		// Restrict the drawing area around the mouse position (5x5 pixel region)
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
			gl.glLoadIdentity();
			//Important: gl (0,0) ist bottom left but window coords (0,0) are top left so we have to change this!
			glu.gluPickMatrix(pickPoint.getX(),viewport[3]-pickPoint.getZ(), 5.0, 5.0, viewport, 0);
			gl.glOrtho(0.0, 10.0, 0.0, 10.0, -0.5, 2.5);
		// 6. Draw the objects with their names
			gl.glMatrixMode(GL.GL_MODELVIEW);
			this.drawNodes();
			gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glFlush();
		// 7. Get the number of hits
		hits = gl.glRenderMode(GL.GL_RENDER);
		// 8. Handle the hits, and get the picked object 
		selectBuffer.get(selectBuff);
		this.handleHits(hits, selectBuff);
		selectionMode = false;
	}
	
	private void handleHits (int hits, int[] data) {
		int offset = 0;
		if (hits > 0) {
			System.out.println("Number of hits = " + hits);
			// TODO quedarse con la que está más cerca del viewpoint en el eje Z
			for (int i = 0; i < hits; i++) {
				System.out.println("number " + data[offset++]);
				System.out.println("minZ " + data[offset++]);
				System.out.println("maxZ " + data[offset++]);
				System.out.println("stackName " + data[offset]);
				int pickedNode = data[offset];
				setupTowers(pickedNode);
				viewLevel = TOWER_LEVEL;
				offset++;
			}
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
	
	private void setupTowers(int id) {
		towers = new Vector<Figure>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < id; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (gl, r.nextFloat()*10,r.nextFloat()*10,r.nextFloat(),r.nextFloat(),r.nextFloat()*10,c);
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
			gl.glVertex3f(10, 0, 0);
			gl.glVertex3f(10, 0, 10);
			gl.glVertex3f(0, 0, 10);
		gl.glEnd();
	}

	public int getViewLevel() {
		return viewLevel;
	}

	private void setViewLevel(int viewLevel) {
		this.viewLevel = viewLevel;
	}
	
	public void setNodeLevel() {
		this.setViewLevel(NODE_LEVEL);
	}
	
	public void setTowerLevel() {
		this.setViewLevel(TOWER_LEVEL);
	}

	public Vector2f getPickPoint() {
		return pickPoint;
	}

	public void setPickPoint(Vector2f pickPoint) {
		this.pickPoint = pickPoint;
	}

	public void setSelectionMode(boolean b) {
		this.selectionMode  = b;
	}

}
