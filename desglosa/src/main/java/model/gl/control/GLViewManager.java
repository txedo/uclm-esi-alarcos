package model.gl.control;

import java.io.IOException;
import java.nio.IntBuffer;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

import exceptions.gl.GLSingletonNotInitializedException;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;

public abstract class GLViewManager {
	protected final int BUFFSIZE = 512;
	/* http://www.cgtextures.com/ */
	private final String FLOOR_TEXTURE = "src/main/resources/gl/metal-floor-texture-01.jpg";
	
	protected GLDrawer drawer;
	protected boolean threeDimensional;
	protected boolean selectionMode;
	protected static double pickingRegion;
	private TextureLoader textureLoader;
	
	protected boolean shadowSupport;
	protected boolean drawingShadows = false;

	public GLViewManager (GLDrawer d, boolean is3D) {
		this.drawer = d;
		this.threeDimensional = is3D;
		this.shadowSupport = false;
		this.selectionMode = false;
		pickingRegion = 0.1;
		textureLoader = new TextureLoader(new String[]{FLOOR_TEXTURE});
	}
	
	 /** Called by the GLDrawer.setViewLevel() function to configure OpenGL properties.
     */
	public abstract void configureView() throws GLSingletonNotInitializedException;
	
	 /** Called by the GLDrawer.setViewLevel() function to deconfigure previously configured OpenGL properties.
     */
	public abstract void deconfigureView() throws GLSingletonNotInitializedException;
	
	 /** Called by the GLDrawer.display() function to manage view functions such as drawing and selection.
     */
	public abstract void manageView() throws GLSingletonNotInitializedException, IOException ;
	
	public abstract void drawItems () throws GLSingletonNotInitializedException;
	
	public void drawShadows () throws GLSingletonNotInitializedException {
		if (this.shadowSupport) {
			this.drawingShadows = true;
			this.drawItems();
			this.drawingShadows = false;
		}
	}
	
	public void selectItem () throws GLSingletonNotInitializedException {
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
			GLSingleton.getGLU().gluPickMatrix(this.drawer.getPickPoint().getX(),viewport[3]-this.drawer.getPickPoint().getY(), pickingRegion, pickingRegion, viewport, 0);
			if (threeDimensional) {
				float h = (float) this.drawer.getScreenWidth() / this.drawer.getScreenHeight();
				GLSingleton.getGLU().gluPerspective(60.0f, h, 0.1f, 1000.0f);
			}
			else {
				float h = (float) this.drawer.getScreenHeight()/ (float) this.drawer.getScreenWidth();
				GLSingleton.getGL().glOrtho(0.0, this.drawer.getDim(), 0.0, this.drawer.getDim()*h, -1, 1);
			}
		// 6. Draw the objects with their names
			GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
			GLSingleton.getGL().glPushMatrix(); ////////////////////////////////////////////////
			this.drawItems();
			GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
			GLSingleton.getGL().glPopMatrix();
			GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
			GLSingleton.getGL().glPopMatrix(); ////////////////////////////////////////////////
			GLSingleton.getGL().glFlush();
		// 7. Get the number of hits
		hits = GLSingleton.getGL().glRenderMode(GL.GL_RENDER);
		// 8. Handle the hits, and get the picked object 
		selectBuffer.get(selectBuff);
		this.handleHits(hits, selectBuff);
		selectionMode = false;
	}
	
	 /** Called by the selectItem function.
	 * @param hits The number of items clicked or hitted.
	 * @param data A 4-multiple array containing blocks of data. Each block of data has four units of information.
	 * The first one indicates the number of the element which goes from 1 to <i>hits</i>. If the value is 0, then no items has been clicked.
	 * The second one indicates the minimum depth in axis Z. The third one indicates the maxinum depth in axis Z.
	 * The depthless object should be usually picked up.
	 * The fourth and last one indicates the name of the object, previously setted with the glLoadName(int) primitive.
     */
	protected void handleHits (int hits, int[] data) {
		int offset = 0;
		System.out.println("Number of hits = " + hits);
		if (hits > 0) {											// If There Were More Than 0 Hits
			int choose = data[3];								// Make Our Selection The First Object
			int depth = data[1];								// Store How Far Away It Is
			for (int loop = 1; loop < hits; loop++) {			// Loop Through All The Detected Hits
				// If This Object Is Closer To Us Than The One We Have Selected
				if (data[loop*4+1] <= depth) {
					choose = data[loop*4+3];					// Select The Closer Object
					depth = data[loop*4+1];						// Store How Far Away It Is
				}
			}
			selectedObjectHandler(choose);
			offset++;
		}
	}
	
	protected void drawFloor () throws GLSingletonNotInitializedException, IOException {
		final float FLOOR_DIMENSION = 100.0f;
		if (!textureLoader.isTexturesLoaded()) textureLoader.loadTexures(true, true, true);
		GLUtils.enableMultisample();
		GLSingleton.getGL().glDisable(GL.GL_LIGHTING);
		GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);
		GLSingleton.getGL().glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
		GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, textureLoader.getTextureNames()[0]);
		GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
		GLSingleton.getGL().glColor3f(0.9f, 0.9f, 0.9f);
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			GLSingleton.getGL().glTexCoord2f(0.0f, 				FLOOR_DIMENSION);	GLSingleton.getGL().glVertex3f(-FLOOR_DIMENSION, 0.0f, -FLOOR_DIMENSION);
			GLSingleton.getGL().glTexCoord2f(0.0f, 				0.0f);				GLSingleton.getGL().glVertex3f(-FLOOR_DIMENSION, 0.0f,  FLOOR_DIMENSION);
			GLSingleton.getGL().glTexCoord2f(FLOOR_DIMENSION, 	0.0f);				GLSingleton.getGL().glVertex3f( FLOOR_DIMENSION, 0.0f,  FLOOR_DIMENSION);
			GLSingleton.getGL().glTexCoord2f(FLOOR_DIMENSION, 	FLOOR_DIMENSION);	GLSingleton.getGL().glVertex3f( FLOOR_DIMENSION, 0.0f, -FLOOR_DIMENSION);
		GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);
		GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
		GLUtils.disableMultisample();
	}

	protected abstract void selectedObjectHandler(int selectedObject);

	public boolean isSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		this.selectionMode = selectionMode;
	}
	
	public static void setPickingRegion(double pickRegion) {
		pickingRegion = pickRegion;
	}

	public boolean isThreeDimensional() {
		return threeDimensional;
	}

	public boolean isShadowSupport() {
		return shadowSupport;
	}

	public void setShadowSupport(boolean shadowSupport) {
		this.shadowSupport = shadowSupport;
	}
	
}

