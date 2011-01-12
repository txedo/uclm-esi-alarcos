package model.gl.control;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import model.gl.GLLogger;
import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.Camera;
import model.gl.knowledge.IConstants;
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


import exceptions.gl.GLSingletonNotInitializedException;

public class GLDrawer implements GLEventListener, IConstants {
	private Vector<GLObject> towers;
	private GLViewController mlc;
	private GLViewController mic;
	private Vector<Caption> captions;
	private Camera camera;
	private Spotlight spotlight;

	public Vector2f position = new Vector2f (0.375f, 0.375f);
	
	private Vector2f pickPoint = new Vector2f(0, 0);
	
	private EViewLevels oldViewLevel;
	private EViewLevels viewLevel;
	
	Vector3f aux = new Vector3f();
	private int screenWidth;
	private int screenHeight;
	
	public final float dim = IConstants.INIT_DIM;

	
	/**
	 * 
	 */
	public GLDrawer() {
		this.viewLevel = EViewLevels.MapLevel;
		this.oldViewLevel = viewLevel;
		
		this.mlc = new GLMapLocationViewController(this, false);		// 2D View
		this.mic = new GLMetricIndicatorViewController(this, false);	// 2D view
	}

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
			if (!oldViewLevel.equals(viewLevel)) {
				this.updateProjection();
				oldViewLevel = viewLevel;
			}
			if (viewLevel.equals(EViewLevels.MapLevel)) {
				mlc.manageView();
			} else if (viewLevel.equals(EViewLevels.MetricIndicatorLevel)) {
				mic.manageView();
				drawCaptions();
			} else if (viewLevel.equals(EViewLevels.TowerLevel)) {
				camera.render();
				spotlight.render(camera.getPosition(), camera.getViewDir());
				drawTowers();
			}
			glDrawable.swapBuffers();
			GLSingleton.getGL().glFlush();
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			GLSingleton.getGL().glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);		// Really Nice Perspective Calculations
			GLSingleton.getGL().glClearColor(1.0f, 1.0f, 1.0f, 1.0f);			// White Background
			GLSingleton.getGL().glEnable(GL.GL_DEPTH_TEST);						// Enables Depth Testing
			GLSingleton.getGL().glClearDepth(1.0f);								// Depth Buffer Setup
			GLSingleton.getGL().glDepthFunc(GL.GL_LESS);						// The Type Of Depth Testing To Do
			GLSingleton.getGL().glShadeModel(GL.GL_SMOOTH);						// Enable Smooth Shading
			// Textures are enabled and its environment configured just before mapping them
			// Configuración para obtener un antialiasing en las líneas
			GLSingleton.getGL().glEnable(GL.GL_BLEND);
			GLSingleton.getGL().glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			GLSingleton.getGL().glEnable(GL.GL_LINE_SMOOTH);
			GLSingleton.getGL().glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
			GLSingleton.getGL().glEnable(GL.GL_POINT_SMOOTH);
			GLSingleton.getGL().glHint(GL.GL_POINT_SMOOTH_HINT, GL.GL_NICEST);
			
			// Creamos una cámara y un foco de luz
			camera = new Camera(-5.0f, 10.0f, -5.0f, 1.0f, -1.0f, 1.0f);
			spotlight = new Spotlight(1.0f, 1.0f, 1.0f);
	
			// Habilitamos el color natural de los materiales
			GLSingleton.getGL().glEnable(GL.GL_COLOR_MATERIAL);
			
			// Configuramos los parámetros del mundo
			((GLMetricIndicatorViewController)mic).setupItems();
			setupCaptions();
			
			// Añadimos los listener de teclado y ratón
			glDrawable.addKeyListener(new MyKeyListener(this));
			glDrawable.addMouseListener(new MyMouseListener(this));
			glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.camera));
			glDrawable.addMouseMotionListener(new MyMouseMotionListener(this.camera));
			
			GLLogger log = new GLLogger();
			System.err.print(log.toString());
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
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
		if (viewLevel.equals(EViewLevels.MapLevel) || viewLevel.equals(EViewLevels.MetricIndicatorLevel))
			GLUtils.setOrthoProjection(this.screenHeight, this.screenWidth, this.dim);
		else if (viewLevel.equals(EViewLevels.TowerLevel))
			GLUtils.setPerspectiveProjection(this.screenHeight, this.screenWidth);
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
	
	public void setupTowers(int id) {
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

	public EViewLevels getViewLevel() {
		return viewLevel;
	}

	public void setViewLevel(EViewLevels viewLevel) {
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
		if (viewLevel.equals(EViewLevels.MapLevel))
			mlc.setSelectionMode(true);
		else if (viewLevel.equals(EViewLevels.MetricIndicatorLevel))
			mic.setSelectionMode(true);
//		else if (viewLevel.equals(EViewLevels.TowerLevel))
//			tc.setSelectionMode(true);
	}
	
	public Camera getCamera() {
		return camera;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public float getDim() {
		return dim;
	}

}
