package model.gl.control;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import model.TextureLoader;
import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.Camera;
import model.gl.knowledge.Edge;
import model.gl.knowledge.IConstants;
import model.gl.knowledge.IEdge;
import model.gl.knowledge.IViewLevels;
import model.gl.knowledge.Node;
import model.gl.knowledge.Spotlight;
import model.gl.knowledge.Tower;
import model.gl.knowledge.caption.Caption;
import model.knowledge.Color;
import model.knowledge.Vector2f;
import model.knowledge.Vector3f;
import model.listeners.MyKeyListener;
import model.listeners.MyMouseListener;
import model.listeners.MyMouseMotionListener;
import model.listeners.MyMouseWheelListener;

import com.sun.opengl.util.BufferUtil;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLDrawer implements GLEventListener, IConstants, IViewLevels {
	private TextureLoader textureLoader;
	
	private Vector<GLObject> towers;
	private Vector<GLObject> nodes;
	private Vector<GLObject> edges;
	private Vector<Caption> captions;
	private Camera camera;
	private Spotlight spotlight;
	public Camera getCamera() {
		return camera;
	}

	public Vector2f position = new Vector2f (0.375f, 0.375f);
	
	private Vector2f pickPoint = new Vector2f(0, 0);
	
	private int oldViewLevel;
	private int viewLevel = NODE_LEVEL;
	private boolean selectionMode = false;
	private final int BUFFSIZE = 512;
	
	Vector3f aux = new Vector3f();
	private int screenWidth;
	private int screenHeight;
	
	public final float dim = IConstants.INIT_DIM;
	
	@Override
	 /** Called by the drawable to initiate OpenGL rendering by the client.
     * After all GLEventListeners have been notified of a display event, the
     * drawable will swap its buffers if necessary.
     * @param gLDrawable The GLAutoDrawable object.
     */
	public void display(GLAutoDrawable glDrawable) {
		try {
			GLSingleton.getGL().glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			GLSingleton.getGL().glLoadIdentity();
			// This state machine prevents calls to updateProjection every time display() is called
			// It it used to change to 2D or 3D projection within the main thread (not keyboard or mouse inputs) since JOGL has multithreading issues
			// http://staff.www.ltu.se/~mjt/ComputerGraphics/jogl-doc/jogl_usersguide/index.html
			if (oldViewLevel != viewLevel) {
				this.updateProjection();
				oldViewLevel = viewLevel;
			}
			switch (viewLevel) {
				case MAP_LEVEL:
					if (selectionMode) {
						debugPrintCoords((int)pickPoint.getX(), (int)pickPoint.getY());
						selectionMode = false;
					}
					float h = (float)this.screenHeight/(float)this.screenWidth;
					GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, textureLoader.getTextures()[0]);
					GLSingleton.getGL().glBegin(GL.GL_QUADS);            // Draw Our First Texture Mapped Quad
						GLSingleton.getGL().glTexCoord2d(0.0f, 0.0f);        // First Texture Coord
						GLSingleton.getGL().glVertex2f(0.0f, 0.0f);          	// First Vertex
						GLSingleton.getGL().glTexCoord2d(1.0f, 0.0f);        // Second Texture Coord
						GLSingleton.getGL().glVertex2f(this.dim, 0.0f);         // Second Vertex
						GLSingleton.getGL().glTexCoord2d(1.0f, 1.0f);        // Third Texture Coord
						GLSingleton.getGL().glVertex2f(this.dim, this.dim*h);   // Third Vertex
						GLSingleton.getGL().glTexCoord2d(0.0f, 1.0f);        // Fourth Texture Coord
						GLSingleton.getGL().glVertex2f(0.0f, this.dim*h);       // Fourth Vertex
					GLSingleton.getGL().glEnd();                         // Done Drawing The First Quad
					// Bind the texture to null to avoid color issues
					GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, 0);

//					GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
//					GLSingleton.getGL().glBegin(GL.GL_QUADS);            // Draw Our First Texture Mapped Quad
//						GLSingleton.getGL().glVertex2f(0.0f, 0.0f);          	// First Vertex
//						GLSingleton.getGL().glVertex2f(0.0f, 1.0f);         // Second Vertex
//						GLSingleton.getGL().glVertex2f(1.0f, 1.0f);   // Third Vertex
//						GLSingleton.getGL().glVertex2f(1.0f, 0.0f);       // Fourth Vertex
//					GLSingleton.getGL().glEnd();                         // Done Drawing The First Quad
					
					break;
				case NODE_LEVEL:
					if (selectionMode) selectNode();
					drawNodes();
					drawEdges();
					drawCaptions();
					break;
				case TOWER_LEVEL:
					camera.render();
					spotlight.render(camera.getPosition(), camera.getViewDir());
					drawTowers();
					break;
			}
			glDrawable.swapBuffers();
			GLSingleton.getGL().glFlush();
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
		}
	}

	@Override
	 /** Called when the display mode has been changed.  <B>
     * !! CURRENTLY UNIMPLEMENTED IN JOGL !!</B>
     * @param gLDrawable The GLAutoDrawable object.
     * @param modeChanged Indicates if the video mode has changed.
     * @param deviceChanged Indicates if the video device has changed.
     */
	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	  /** Called by the drawable immediately after the OpenGL context is
     * initialized for the first time. Can be used to perform one-time OpenGL
     * initialization such as setup of lights and display lists.
     * @param gLDrawable The GLAutoDrawable object.
     */
	public void init(GLAutoDrawable glDrawable) {	
		GLSingleton.getInstance();
		GLSingleton.init(glDrawable);
		
		try {
			this.viewLevel = MAP_LEVEL;
			this.textureLoader = new TextureLoader (new String[]{"presentation/resources/maps/world-map.png"});
			textureLoader.loadTexures();
			
			GLSingleton.getGL().glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);		// Really Nice Perspective Calculations
			GLSingleton.getGL().glClearColor(1.0f, 1.0f, 1.0f, 1.0f);			// White Background
			GLSingleton.getGL().glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
			GLSingleton.getGL().glClearDepth(1.0f);								// Depth Buffer Setup
			GLSingleton.getGL().glDepthFunc(GL.GL_LESS);						// The Type Of Depth Testing To Do
			GLSingleton.getGL().glShadeModel(GL.GL_SMOOTH);						// Enable Smooth Shading
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			// Configuración para obtener un antialiasing en las líneas
			GLSingleton.getGL().glEnable(GL.GL_BLEND);
			GLSingleton.getGL().glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			GLSingleton.getGL().glEnable(GL.GL_LINE_SMOOTH);
			GLSingleton.getGL().glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
			
			// Creamos una cámara y un foco de luz
			camera = new Camera(-5.0f, 10.0f, -5.0f, 1.0f, -1.0f, 1.0f);
			spotlight = new Spotlight(1.0f, 1.0f, 1.0f);
	
			// Habilitamos el color natural de los materiales
			GLSingleton.getGL().glEnable(GL.GL_COLOR_MATERIAL);
			
			// Configuramos los parámetros del mundo
			setupNodes();
			setupEdges();
			setupCaptions();
			
			// Añadimos los listener de teclado y ratón
			glDrawable.addKeyListener(new MyKeyListener(this));
			glDrawable.addMouseListener(new MyMouseListener(this));
			glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.camera));
			glDrawable.addMouseMotionListener(new MyMouseMotionListener(this.camera));
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	 /** Called by the drawable during the first repaint after the component has
     * been resized. The client can update the viewport and view volume of the
     * window appropriately, for example by a call to
     * GL.glViewport(int, int, int, int); note that for convenience the component
     * has already called GL.glViewport(int, int, int, int)(x, y, width, height)
     * when this method is called, so the client may not have to do anything in
     * this method.
     * @param gLDrawable The GLAutoDrawable object.
     * @param x The X Coordinate of the viewport rectangle.
     * @param y The Y coordinate of the viewport rectanble.
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
			int height) {
		try {
			// Qué acción realizar cuando se redimensiona la ventana
			GLSingleton.getGL().setSwapInterval(1);
			this.screenWidth = width;
			this.screenHeight = height;
			// lower left corner (0,0); upper right corner (width,height)
			GLSingleton.getGL().glViewport(0, 0, this.screenWidth, this.screenHeight);
			this.updateProjection();
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
		}
	}
	
	private void updateProjection() throws GLSingletonNotInitializedException {
		if (viewLevel == MAP_LEVEL || viewLevel == NODE_LEVEL)
			GLUtils.setOrthoProjection(this.screenHeight, this.screenWidth, this.dim);
		else if (viewLevel == TOWER_LEVEL)
			GLUtils.setPerspectiveProjection(this.screenHeight, this.screenWidth);
	}

	private void setupNodes() {
		Node n;
		nodes = new Vector<GLObject>();
		Color c = new Color (0.0f, 0.0f, 0.0f);
		n = new Node(0.0f, 0.0f, 1.0f, c);
		nodes.add(n);
		n = new Node(2.0f, 0.0f, 1.0f, c);
		nodes.add(n);
		n = new Node(0.0f, 2.0f, 1.0f, c);
		nodes.add(n);
		n = new Node(2.0f, 2.0f, 1.0f, c);
		nodes.add(n);
	}
	
	private void drawNodes () throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject f : nodes) {
			if (selectionMode) {
				GLSingleton.getGL().glLoadName(cont);
				cont++;
			}
			f.draw();
		}
	}
	
	public void selectNode () throws GLSingletonNotInitializedException {
		int[] selectBuff = new int[BUFFSIZE];
		IntBuffer selectBuffer = BufferUtil.newIntBuffer(BUFFSIZE);
		int hits = 0;
		int[] viewport = new int[4];
		// Save somewhere the info about the current viewport
		GLSingleton.getGL().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		// Initialize a selection buffer, which will contain data about selected objects
		GLSingleton.getGL().glSelectBuffer(BUFFSIZE, selectBuffer);
		// Switch to GL_SELECT mode
		GLSingleton.getGL().glRenderMode(GL.GL_SELECT);
		// Initialize the name stack
		GLSingleton.getGL().glInitNames();
		// Now fill the stack with one element (or glLoadName will generate an error)
		GLSingleton.getGL().glPushName(-1);
		// Restrict the drawing area around the mouse position (5x5 pixel region)
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		GLSingleton.getGL().glPushMatrix();
		GLSingleton.getGL().glLoadIdentity();
			//Important: gl (0,0) is bottom left but window coordinates (0,0) are top left so we have to change this!
			GLSingleton.getGLU().gluPickMatrix(pickPoint.getX(),viewport[3]-pickPoint.getY(), 1.0, 1.0, viewport, 0);
			float h = (float) this.screenHeight/ (float) this.screenWidth;
			GLSingleton.getGL().glOrtho(0.0, this.dim, 0.0, this.dim*h, -1, 1);
		// 6. Draw the objects with their names
			GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
			this.drawNodes();
			GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
			GLSingleton.getGL().glPopMatrix();
			GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
			GLSingleton.getGL().glFlush();
		// 7. Get the number of hits
		hits = GLSingleton.getGL().glRenderMode(GL.GL_RENDER);
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
				this.setViewLevel(TOWER_LEVEL);
				offset++;
			}
		}
	}
	
	private void setupEdges () {
		Edge e;
		edges = new Vector<GLObject>();
		e = new Edge((Node)nodes.get(0), (Node)nodes.get(1));
		e.setType(IEdge.DOTTED);
		edges.add(e);
		e = new Edge((Node)nodes.get(0), (Node)nodes.get(2));
		e.setType(IEdge.DASHED);
		edges.add(e);
		e = new Edge((Node)nodes.get(1), (Node)nodes.get(2));
		e.setType(IEdge.DOT_AND_DASH);
		edges.add(e);
		e = new Edge((Node)nodes.get(2), (Node)nodes.get(3));
		e.setType(IEdge.SOLID);
		edges.add(e);
	}
	
	private void drawEdges () throws GLSingletonNotInitializedException {
		for (GLObject f : edges) {
			f.draw();
		}
	}
	
	private void setupCaptions (){
		captions = new Vector<Caption>();
		Caption c = new Caption(3.2f, 2.8f);
		c.addLine(new Color (1.0f, 0.0f, 1.0f), "Hello World!");
		c.addLine(new Color (0.0f, 1.0f, 1.0f), "Goodbye World!");
		captions.add(c);
	}
	
	private void drawCaptions () throws GLSingletonNotInitializedException {
		for (Caption c : captions) {
			c.draw();
		}
	}
	
	private void setupTowers(int id) {
		towers = new Vector<GLObject>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < id; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (r.nextFloat()*9,r.nextFloat()*9,r.nextFloat(),r.nextFloat(),r.nextFloat()*10,c);
			towers.add(t);
		}
	}
	
	public void drawTowers() throws GLSingletonNotInitializedException {
		this.drawFloor();
		
		for (GLObject f : towers) {
			f.draw();
		}
	}
	
	private void drawFloor () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor4f(0.3f, 0.3f, 0.3f, 0.3f);
		GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
		GLSingleton.getGL().glBegin(GL.GL_POLYGON);	
			GLSingleton.getGL().glVertex3f(0, 0, 0);
			GLSingleton.getGL().glVertex3f(10, 0, 0);
			GLSingleton.getGL().glVertex3f(10, 0, 10);
			GLSingleton.getGL().glVertex3f(0, 0, 10);
		GLSingleton.getGL().glEnd();
	}

	public int getViewLevel() {
		return viewLevel;
	}

	public void setViewLevel(int viewLevel) {
		this.oldViewLevel = this.viewLevel;
		this.viewLevel = viewLevel;
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

	public void debugPrintCoords (int x, int y) throws GLSingletonNotInitializedException {
		System.out.println("screen coords: " + x + " " + y);
		Vector3f v = GLUtils.getScreen2World((int)x, (int)y, false);
		System.out.println("world coords: " + v.getX() + " " + v.getY());
		v = GLUtils.getScreen2World((int)x, (int)y, true);
		System.out.println("relative world coords: " + v.getX() + " " + v.getY());
	}

}
