package model.gl;

import java.io.IOException;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import model.gl.control.EViewLevels;
import model.gl.control.GLFactoryViewManager;
import model.gl.control.GLFontBuilder;
import model.gl.control.GLMapLocationViewManager;
import model.gl.control.GLMetricIndicatorViewManager;
import model.gl.control.GLProjectViewManager;
import model.gl.control.GLTowerViewManager;
import model.gl.control.GLViewManager;
import model.gl.knowledge.Camera;
import model.gl.knowledge.IConstants;
import model.gl.knowledge.Spotlight;
import model.gl.knowledge.caption.Caption;
import model.knowledge.Color;
import model.knowledge.Vector2f;
import model.listeners.MyKeyListener;
import model.listeners.MyMouseListener;
import model.listeners.MyMouseMotionListener;
import model.listeners.MyMouseWheelListener;


import exceptions.gl.GLSingletonNotInitializedException;

public class GLDrawer implements GLEventListener, IConstants {
	/*
	 * A GLViewManager must be declared for each view manager available.
	 * When adding a new view manager, it has to be added and configured to the following sections:
	 * * A switch case in the display() function.
	 * * Instance initialization at the init() function.
	 * * updateProjection() function.
	 * * setSelectionMode() function, in case that the view implements a selection mechanism.
	 * * getGLViewManager() function.
	 */
	private GLViewManager mapLocationView;
	private GLViewManager metricIndicatorView;
	private GLViewManager towerView;
	private GLViewManager projectView;
	private GLViewManager factoryView;
	
	private EViewLevels oldViewLevel;
	private EViewLevels viewLevel;
	
	private Camera camera;
	private Spotlight spotlight;
	private GLLogger log;
	
	private boolean debugMode;
	private Vector2f pickPoint = new Vector2f(0, 0);
	
	private int screenWidth;
	private int screenHeight;
	public final float DIM = IConstants.INIT_DIM;
	
	private Vector<Caption> captions;

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
			// Due to JOGL multi-threading issues, this is used to change to 2D or 3D projection within the main thread (not keyboard or mouse inputs)
			// http://staff.www.ltu.se/~mjt/ComputerGraphics/jogl-doc/jogl_usersguide/index.html
			if (!oldViewLevel.equals(viewLevel)) {
				this.updateProjection();
				if (!oldViewLevel.equals(EViewLevels.UnSetLevel)) getViewManager(oldViewLevel).deconfigureView();
				getViewManager(viewLevel).configureView();
				oldViewLevel = viewLevel;
			}
			if (getViewManager(this.viewLevel).isThreeDimensional()) {
				camera.render();
				spotlight.render(camera.getPosition(), camera.getViewDir());
			}
			// TODO quitar este switch y llamar directmamente a getViewManager(this.viewLevel).manageView()
			switch (this.viewLevel) {
				case MapLevel:
					this.mapLocationView.manageView();
					break;
				case ProjectLevel:
					this.projectView.manageView();
					break;
				case FactoryLevel:
					this.factoryView.manageView();
					break;
				case MetricIndicatorLevel:
					this.metricIndicatorView.manageView();
					drawCaptions();
					break;
				case TowerLevel:
					this.towerView.manageView();
					break;
			}
			if (this.debugMode) {
				GLUtils.beginOrtho(this.screenHeight, this.screenHeight, this.DIM);
				GLUtils.renderBitmapString(0.0f, 0.0f, 1, this.log.toString());
				GLUtils.endOrtho();
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

	 /** Called when the display mode has been changed.  <B>
     * !! CURRENTLY UNIMPLEMENTED IN JOGL !!</B>
     * @param gLDrawable The GLAutoDrawable object.
     * @param modeChanged Indicates if the video mode has changed.
     * @param deviceChanged Indicates if the video device has changed.
     */
	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	  /** Called by the drawable immediately after the OpenGL context is
     * initialized for the first time. Can be used to perform one-time OpenGL
     * initialization such as setup of lights and display lists.
     * @param gLDrawable The GLAutoDrawable object.
     */
	public void init(GLAutoDrawable glDrawable) {
		GLSingleton.getInstance();
		GLSingleton.init(glDrawable);
		
		this.debugMode = false;
		
		this.oldViewLevel = EViewLevels.UnSetLevel;
		this.viewLevel = EViewLevels.MapLevel;
		
		this.mapLocationView = new GLMapLocationViewManager(this, false);			// 2D View
		this.metricIndicatorView = new GLMetricIndicatorViewManager(this, false);	// 2D View
		this.towerView = new GLTowerViewManager(this, true);						// 3D View
		this.projectView = new GLProjectViewManager(this, true);					// 3D View
		this.factoryView = new GLFactoryViewManager(this, true);					// 3D View
		
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
			camera = new Camera(5.0f, 7.0f, 15.0f, 0.0f, -0.5f, -1.0f);
			spotlight = new Spotlight(1.0f, 1.0f, 1.0f);
	
			// Habilitamos el color natural de los materiales
			GLSingleton.getGL().glEnable(GL.GL_COLOR_MATERIAL);
			
			// Configuramos los parámetros del mundo
			((GLMetricIndicatorViewManager)metricIndicatorView).setupItems();
			setupCaptions();
			GLProjectViewManager.setupItems();			
			
			// Añadimos los listener de teclado y ratón
			glDrawable.addKeyListener(new MyKeyListener(this));
			glDrawable.addMouseListener(new MyMouseListener(this));
			glDrawable.addMouseWheelListener(new MyMouseWheelListener(this.camera));
			glDrawable.addMouseMotionListener(new MyMouseMotionListener(this));
			
			GLFontBuilder.getInstance().buildFont();
			
			log = new GLLogger();
			System.err.print(log.toString());
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			GLUtils.setOrthoProjection(this.screenHeight, this.screenWidth, this.DIM);
		else if (viewLevel.equals(EViewLevels.TowerLevel) || viewLevel.equals(EViewLevels.ProjectLevel) || viewLevel.equals(EViewLevels.FactoryLevel))
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

	public EViewLevels getViewLevel() {
		return viewLevel;
	}

	public void setViewLevel(EViewLevels viewLevel) {
		this.oldViewLevel = this.viewLevel;
		this.viewLevel = viewLevel;
		// We reset the camera position in case that the view level is 3D
		if (this.getViewManager(viewLevel).isThreeDimensional()) {
			this.spotlight.reset();
			this.camera.reset();
		}
	}

	public Vector2f getPickPoint() {
		return pickPoint;
	}

	public void setPickPoint(Vector2f pickPoint) {
		this.pickPoint = pickPoint;
	}

	public void setSelectionMode(boolean b) {
		getViewManager(viewLevel).setSelectionMode(true);
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
		return DIM;
	}
	
	public GLViewManager getViewManager (EViewLevels viewLevel) {
		GLViewManager result = null;
			switch (viewLevel) {
			case MapLevel:
				result = this.mapLocationView;
				break;
			case ProjectLevel:
				result = this.projectView;
				break;
			case FactoryLevel:
				result = this.factoryView;
				break;
			case MetricIndicatorLevel:
				result = this.metricIndicatorView;
				break;
			case TowerLevel:
				result = this.towerView;
				break;
			}
		return result;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	
}
