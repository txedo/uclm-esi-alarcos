package model.gl;

import javax.media.opengl.GL;

import model.gl.knowledge.IConstants;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLUtils {

	static public Vector3f getScreen2World (int screenX, int screenY, boolean relative) throws GLSingletonNotInitializedException	{
	    int viewport[] = new int[4];
	    double mvmatrix[] = new double[16];
	    double projmatrix[] = new double[16];
	    double wcoord[] = new double[4];// wx, wy, wz;// returned xyz coords
		double winX, winY, winZ;

		GLSingleton.getGL().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		GLSingleton.getGL().glGetDoublev(GL.GL_MODELVIEW_MATRIX, mvmatrix, 0);		
		GLSingleton.getGL().glGetDoublev(GL.GL_PROJECTION_MATRIX, projmatrix, 0);
		
		winX = (double)screenX;
		winY = (double)screenY;
		if (!relative) {
			// Important: gl (0,0) is bottom left but window coordinates (0,0) are top left so we should change this!
			// but since we are working with relative coords (see pushmatrix and loadidentity above) we don't change it
			winY = (double)viewport[3] - (double)screenY - 1;
			
		}
		winZ = 0.0;

//			GLSingleton.getGL().glReadPixels( (int)winX, (int)winY, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, winZ );
		if (!GLSingleton.getGLU().gluUnProject( (double)winX, (double)winY, (double)winZ,
				mvmatrix, 0,
				projmatrix, 0,
				viewport, 0,
				wcoord, 0)) {
			wcoord[0] = -IConstants.INFINITE;
			wcoord[1] = -IConstants.INFINITE;
			wcoord[2] = -IConstants.INFINITE;
		}

		return new Vector3f((float)wcoord[0], (float)wcoord[1], (float)wcoord[2]);
	}
	
	static public Vector3f getWorld2Screen (float objX, float objY) throws GLSingletonNotInitializedException {
	    int viewport[] = new int[4];
	    double mvmatrix[] = new double[16];
	    double projmatrix[] = new double[16];
	    double scoord[] = new double[4];// wx, wy, wz;// returned xyz coords

	    GLSingleton.getGL().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		GLSingleton.getGL().glGetDoublev(GL.GL_MODELVIEW_MATRIX, mvmatrix, 0);
		GLSingleton.getGL().glGetDoublev(GL.GL_PROJECTION_MATRIX, projmatrix, 0);
		
		GLSingleton.getGLU().gluProject(objX, objY, 0.0f,
				mvmatrix, 0,
				projmatrix, 0,
				viewport, 0,
				scoord, 0);

		return new Vector3f((float)scoord[0], (float)scoord[1], (float)scoord[2]);
	}
	
	/**
	 * Switch to orthographic projection
	 * The current projection and modelview matrix are saved (push).
	 * You can loads projection and modelview matrices with endOrtho
	 * @throws GLSingletonNotInitializedException 
	 * @see #endOrtho()
	 */
	static public void beginOrtho(int screenHeight, int screenWidth, float glDim) throws GLSingletonNotInitializedException
	{
	    /*
	     * We save the current projection matrix and we define a viewing volume
	     * in the orthographic mode.
	     * Projection matrix stack defines how the scene is projected to the screen.
	     */
		GLSingleton.getGL().glDisable(GL.GL_DEPTH_TEST);		  // Disables Depth Testing
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);   //select the Projection matrix
		GLSingleton.getGL().glPushMatrix();                   //save the current projection matrix
		GLSingleton.getGL().glLoadIdentity();                 //reset the current projection matrix to creates a new Orthographic projection
	    //Creates a new orthographic viewing volume
	    float h = (float) screenHeight/ (float) screenWidth ;
	    GLSingleton.getGL().glOrtho(0.0f, glDim, 0.0f, glDim*h, -1.0, 1.0);	// left, right, bottom, top, near, far
	   
	    /*
	     * Select, save and reset the modelview matrix.
	     * Modelview matrix stack store transformation like translation, rotation ...
	     */
	    GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
	    GLSingleton.getGL().glLoadIdentity();
	}

	/**
	 * Load projection and modelview matrices previously saved by the method beginOrtho
	 * @throws GLSingletonNotInitializedException 
	 * @see #beginOrtho()
	 */
	static public void endOrtho() throws GLSingletonNotInitializedException
	{
		// Enables Depth Testing
		GLSingleton.getGL().glEnable(GL.GL_DEPTH_TEST);
	    //Select the Projection matrix stack
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
	    //Load the previous Projection matrix (Generaly, it is a Perspective projection)
		GLSingleton.getGL().glPopMatrix();
	    //Select the Modelview matrix stack
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
	}
	
	static public void billboardCheatSphericalBegin() throws GLSingletonNotInitializedException {
		double mvmatrix[] = new double[16];

		// save the current modelview matrix
		GLSingleton.getGL().glPushMatrix();

		// get the current modelview matrix
		GLSingleton.getGL().glGetDoublev(GL.GL_MODELVIEW_MATRIX , mvmatrix, 0);

		// undo all rotations
		// beware all scaling is lost as well 
		for(int i=0; i<3; i++ ) 
		    for(int j=0; j<3; j++ ) {
			if ( i==j )
				mvmatrix[i*4+j] = 1.0;
			else
				mvmatrix[i*4+j] = 0.0;
		    }

		// set the modelview with no rotations
		GLSingleton.getGL().glLoadMatrixd(mvmatrix, 0);
	}
	
	static public void billboardEnd() throws GLSingletonNotInitializedException {
		// restore the previously 
		// stored modelview matrix
		GLSingleton.getGL().glPopMatrix();
	}
	
	static public void setOrthoProjection (int screenHeight, int screenWidth, float glDim) throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glDisable(GL.GL_DEPTH_TEST);		  // Disables Depth Testing
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		GLSingleton.getGL().glLoadIdentity();
		//float h = (float) screenHeight/ (float) screenWidth;
		//GLSingleton.getGLU().gluOrtho2D(0.0, glDim, 0.0, glDim*h);
		GLSingleton.getGLU().gluOrtho2D(0.0, glDim, 0.0, glDim);
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
		GLSingleton.getGL().glLoadIdentity();
	}
	
	static public void setPerspectiveProjection (int screenHeight, int screenWidth) throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL.GL_DEPTH_TEST);			// Enables Depth Testing
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		GLSingleton.getGL().glLoadIdentity();		
		float h = (float) screenWidth / (float) screenHeight;
		GLSingleton.getGLU().gluPerspective(60.0f, h, 0.1f, 1000.0f);
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
		GLSingleton.getGL().glLoadIdentity();
	}

	static public void debugPrintCoords (int x, int y) throws GLSingletonNotInitializedException {
		System.err.println("screen coords: " + x + " " + y);
		Vector3f v = GLUtils.getScreen2World((int)x, (int)y, false);
		System.err.println("world coords: " + v.getX() + " " + v.getY());
		v = GLUtils.getScreen2World((int)x, (int)y, true);
		System.err.println("relative world coords: " + v.getX() + " " + v.getY());
	}

}
