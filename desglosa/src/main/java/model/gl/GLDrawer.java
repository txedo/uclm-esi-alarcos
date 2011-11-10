package model.gl;

import java.awt.Component;
import java.io.IOException;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import com.jogamp.common.nio.Buffers;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import model.gl.control.EViewLevels;
import model.gl.control.GLFontBuilder;
import model.gl.control.GLViewManager;
import model.gl.control.IViewManagerFactoryImpl;
import model.listeners.MyKeyAdapter;
import model.listeners.MyMouseAdapter;
import model.util.Synchronizer;
import model.util.Vector2f;
import model.util.Vector3f;


import exceptions.GLSingletonNotInitializedException;

public class GLDrawer implements GLEventListener, IGLConstants {
	/*
	 * A GLViewManager must be declared for each view manager available.
	 * When adding a new view manager, it has to be added and configured to the following sections:
	 * * A switch case in the display() function.
	 * * Instance initialization at the init() function.
	 * * updateProjection() function.
	 * * setSelectionMode() function, in case that the view implements a selection mechanism.
	 * * getGLViewManager() function.
	 */
	private GLViewManager towerView;
	private GLViewManager antennaBallView;
	private GLViewManager buildingView;
	
	private EViewLevels oldViewLevel;
	private EViewLevels viewLevel;
	
	private GLCamera camera;
	private GLSpotlight spotlight;
	private GLLogger log;
	
	private boolean debugMode;
	private boolean renderShadow;
	private boolean stencilShadow;
	private Vector2f pickPoint = new Vector2f(0, 0);
	
	private int screenWidth;
	private int screenHeight;
	public final float DIM = IGLConstants.INIT_DIM;

	 /** Called by the drawable to initiate OpenGL rendering by the client.
     * After all GLEventListeners have been notified of a display event, the
     * drawable will swap its buffers if necessary.
     * @param gLDrawable The GLAutoDrawable object.
     */
	public void display(GLAutoDrawable glDrawable) {
		if (!viewLevel.equals(EViewLevels.UnSetLevel)) {
			try {
				// This state machine prevents calls to updateProjection every time display() is called
				// Due to JOGL multi-threading issues, this is used to change to 2D or 3D projection within the main thread (not keyboard or mouse inputs)
				// http://staff.www.ltu.se/~mjt/ComputerGraphics/jogl-doc/jogl_usersguide/index.html
				if (!oldViewLevel.equals(viewLevel)) {
					this.updateProjection();
					if (!oldViewLevel.equals(EViewLevels.UnSetLevel)) getViewManager(oldViewLevel).deconfigureView();
					getViewManager(viewLevel).configureView();
					oldViewLevel = viewLevel;
				}
				// If the active view supports shadows, the stencil buffer is cleared in order to draw them
				if (this.stencilShadow && this.renderShadow && this.getViewManager(viewLevel).isShadowSupport()) {
					GLSingleton.getGL().glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT);
				}
				else {
					/* Avoid clearing stencil when not using it. */
					GLSingleton.getGL().glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
				}
				GLSingleton.getGL().glLoadIdentity();
				// Render the new camera and spotlight position if the active view is three-dimensional
				if (getViewManager(this.viewLevel).isThreeDimensional()) {
					camera.render();
					spotlight.render(camera.getPosition(), camera.getViewDir());
				}
				// Perform shadow calculations if shadows are enabled
				FloatBuffer floorShadowBuf = Buffers.newDirectFloatBuffer(16);
				if (this.getViewManager(viewLevel).isShadowSupport()) {
					Vector3f lightSource = this.camera.getPosition().clone();
					lightSource.setX(lightSource.getX()-1.0f);
					lightSource.setY(lightSource.getY()+1.0f);
					floorShadowBuf = GLUtils.doShadowCalculations(this.renderShadow, this.stencilShadow, lightSource);
				}
				// Render the normal scene
				this.getViewManager(viewLevel).manageView();
				// Render the scene shadow if shadows are enabled
				if (this.getViewManager(viewLevel).isShadowSupport()) {
					GLUtils.renderProjectedShadow(this.renderShadow, this.stencilShadow, floorShadowBuf, this.getViewManager(viewLevel));
				}
				if (this.debugMode) {
					this.log.printToGL(this.screenHeight, this.screenWidth-100, this.DIM);
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
		this.renderShadow = true;
		this.stencilShadow = true;
		
		this.oldViewLevel = null;
		this.viewLevel = EViewLevels.UnSetLevel;
		
		this.towerView = IViewManagerFactoryImpl.getInstance().createTowerViewManager(this);
		this.antennaBallView = IViewManagerFactoryImpl.getInstance().createProjectViewManager(this);
		this.buildingView = IViewManagerFactoryImpl.getInstance().createFactoryViewManager(this);
		
		try {
			GLSingleton.getGL().glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);		// Really Nice Perspective Calculations
			GLSingleton.getGL().glClearColor(1.0f, 1.0f, 1.0f, 1.0f);			// White Background
			GLSingleton.getGL().glEnable(GL2.GL_DEPTH_TEST);						// Enables Depth Testing
			GLSingleton.getGL().glClearDepth(1.0f);								// Depth Buffer Setup
			GLSingleton.getGL().glDepthFunc(GL2.GL_LESS);						// The Type Of Depth Testing To Do
			GLSingleton.getGL().glShadeModel(GL2.GL_SMOOTH);						// Enable Smooth Shading
			// Enable blending
			GLSingleton.getGL().glEnable(GL2.GL_BLEND);
			GLSingleton.getGL().glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
			// Point antialiasing
			GLSingleton.getGL().glEnable(GL2.GL_POINT_SMOOTH);
			GLSingleton.getGL().glHint(GL2.GL_POINT_SMOOTH_HINT, GL2.GL_NICEST);
			// Line antialiasing
			GLSingleton.getGL().glEnable(GL2.GL_LINE_SMOOTH);
			GLSingleton.getGL().glHint(GL2.GL_LINE_SMOOTH_HINT, GL2.GL_NICEST);
			// Polygon antialiasing (in case this does not work, use GLUtils.enableMultisample())
			GLSingleton.getGL().glEnable(GL2.GL_POLYGON_SMOOTH);
			GLSingleton.getGL().glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);
			// Textures are enabled and its environment configured just before mapping them, not here
			
			// Creamos una cámara y un foco de luz
			camera = new GLCamera(20.0f, 10.0f, 20.0f, -1.0f, -0.5f, -1.0f);
			spotlight = new GLSpotlight(1.0f, 1.0f, 1.0f);
	
			// Habilitamos el color natural de los materiales
			GLSingleton.getGL().glEnable(GL2.GL_COLOR_MATERIAL);
			GLSingleton.getGL().glEnable(GL2.GL_NORMALIZE);
			
			// Añadimos los listener de teclado y ratón
			MouseListener myMouse = new MyMouseAdapter(this);
			KeyListener myKey = new MyKeyAdapter(this);
		    if (glDrawable instanceof Component) {
		        Component comp = (Component) glDrawable;
		        new AWTMouseAdapter(myMouse).addTo(comp);
		        new AWTKeyAdapter(myKey).addTo(comp);
		    } else if (glDrawable instanceof Window) {
		        Window window = (Window) glDrawable;
		        window.addMouseListener(myMouse);
		        window.addKeyListener(myKey);
		    }
			
//			GLFontBuilder.getInstance().buildFont();
			
			log = new GLLogger();
			System.err.print(log.toString());
			Synchronizer.getInstance().conceder();
		} catch (GLSingletonNotInitializedException e) {
			GLSingleton.init(glDrawable);
			this.init(glDrawable);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	 /** Called by the drawable during the first repaint after the component has
     * been resized. The client can update the viewport and view volume of the
     * window appropriately, for example by a call to
     * gl.glViewport(int, int, int, int); note that for convenience the component
     * has already called gl.glViewport(int, int, int, int)(x, y, width, height)
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
	
	@Override
	public void dispose(GLAutoDrawable glDrawable) {
		System.err.println("Desglosa: Dispose");
	}
	
	private void updateProjection() throws GLSingletonNotInitializedException {
		if (!viewLevel.equals(EViewLevels.UnSetLevel)) {
			if (this.getViewManager(viewLevel).isThreeDimensional())
				GLUtils.setPerspectiveProjection(this.screenHeight, this.screenWidth);
			else
				GLUtils.setOrthoProjection(this.screenHeight, this.screenWidth, this.DIM);
		} else {
			GLUtils.setOrthoProjection(this.screenHeight, this.screenWidth, this.DIM);
		}

	}
	
	public EViewLevels getViewLevel() {
		return viewLevel;
	}

	public void setViewLevel(EViewLevels viewLevel) {
		this.oldViewLevel = this.viewLevel;
		this.viewLevel = viewLevel;
		// We reset the camera position in case that the view level is 3D
		if (this.viewLevel != EViewLevels.UnSetLevel) {
			if (this.getViewManager(viewLevel).isThreeDimensional()) {
				this.spotlight.reset();
				this.camera.reset();
			}
		}
	}
	
	public GLViewManager getViewManager (EViewLevels viewLevel) {
		GLViewManager result = null;
		switch (viewLevel) {
			case AntennaBallLevel:
				result = this.antennaBallView;
				break;
			case BuildingLevel:
				result = this.buildingView;
				break;
			case TowerLevel:
				result = this.towerView;
				break;
		}
		return result;
	}

	public Vector2f getPickPoint() {
		return pickPoint;
	}

	public void setPickPoint(Vector2f pickPoint) {
		this.pickPoint = pickPoint;
	}

	public void setSelectionMode(boolean b, int clickButton, int clickCount) {
		GLViewManager vm = getViewManager(viewLevel);
		if (vm != null) getViewManager(viewLevel).setSelectionMode(true, clickButton, clickCount);
	}
	
	public GLCamera getCamera() {
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

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public boolean isRenderShadow() {
		return renderShadow;
	}

	public void setRenderShadow(boolean renderShadow) {
		this.renderShadow = renderShadow;
	}

	public boolean isStencilShadow() {
		return stencilShadow;
	}

	public void setStencilShadow(boolean stencilShadow) {
		this.stencilShadow = stencilShadow;
	}
	
}
