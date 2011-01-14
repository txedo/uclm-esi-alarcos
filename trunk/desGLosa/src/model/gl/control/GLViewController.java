package model.gl.control;

import java.io.IOException;
import java.nio.IntBuffer;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

import exceptions.gl.GLSingletonNotInitializedException;

import model.gl.GLSingleton;

public abstract class GLViewController {
	protected final int BUFFSIZE = 512;
	
	protected GLDrawer drawer;
	protected boolean isThreeDimensional;
	protected boolean selectionMode;
	protected double pickingRegion;

	public GLViewController (GLDrawer d, boolean is3D) {
		this.drawer = d;
		this.isThreeDimensional = is3D;
		this.selectionMode = false;
		this.pickingRegion = 0.1;
	}
	
	public abstract void manageView() throws GLSingletonNotInitializedException, IOException ;
	
	public abstract void drawItems () throws GLSingletonNotInitializedException;
	
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
			GLSingleton.getGLU().gluPickMatrix(this.drawer.getPickPoint().getX(),viewport[3]-this.drawer.getPickPoint().getY(), this.pickingRegion, this.pickingRegion, viewport, 0);
			if (isThreeDimensional) {
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
	protected abstract void handleHits (int hits, int[] data);

	public boolean isSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(boolean selectionMode) {
		this.selectionMode = selectionMode;
	}
	
	public void setPickingRegion(double pickingRegion) {
		this.pickingRegion = pickingRegion;
	}
}

