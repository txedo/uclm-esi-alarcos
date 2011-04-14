package model.gl;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;

import model.gl.control.GLViewManager;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.IConstants;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLUtils {

	/**
	 * Convert screen coordinates into OpenGL world coordinates. The world
	 * coordinates can be both relative or absolute. This is used to measure
	 * lengths or convert points.
	 * 
	 * @param screenX
	 *            Horizontal screen coordinate in pixels.
	 * @param screenY
	 *            Vertical screen coordinate in pixels.
	 * @param relative
	 *            true for relative coordinates (measurement purposes); false
	 *            for absolute coordinates (conversion purposes)
	 * @return A Vector3f whose components are the world coordinates
	 *         corresponding to the given screen coordinates.
	 * @throws GLSingletonNotInitializedException
	 * @see {@link #getWorld2Screen(float, float)}
	 */
	static public Vector3f getScreen2World(int screenX, int screenY,
			boolean relative) throws GLSingletonNotInitializedException {
		int viewport[] = new int[4];
		double mvmatrix[] = new double[16];
		double projmatrix[] = new double[16];
		double wcoord[] = new double[3];// wx, wy, wz;// returned xyz coords
		double winX, winY, winZ;

		GLSingleton.getGL().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		GLSingleton.getGL().glGetDoublev(GL.GL_MODELVIEW_MATRIX, mvmatrix, 0);
		GLSingleton.getGL()
				.glGetDoublev(GL.GL_PROJECTION_MATRIX, projmatrix, 0);

		winX = (double) screenX;
		winY = (double) screenY;
		if (!relative) {
			// Important: gl (0,0) is bottom left but window coordinates (0,0)
			// are top left so we should change this if we work with no-relative
			// coords
			winY = (double) viewport[3] - (double) screenY - 1;

		}
		FloatBuffer zBuffer = BufferUtil.newFloatBuffer(1);
		GLSingleton.getGL().glReadPixels( (int)winX, (int)winY, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, zBuffer );
		winZ = (float)zBuffer.get();
		
		if (!GLSingleton.getGLU().gluUnProject((double) winX, (double) winY,
				(double) winZ, mvmatrix, 0, projmatrix, 0, viewport, 0, wcoord,
				0)) {
			wcoord[0] = -IConstants.INFINITE;
			wcoord[1] = -IConstants.INFINITE;
			wcoord[2] = -IConstants.INFINITE;
		}

		return new Vector3f((float) wcoord[0], (float) wcoord[1],
				(float) wcoord[2]);
	}

	/**
	 * Converts OpenGL world coordinates into screen pixel coordinates. At the
	 * moment, this only works on a 2D paradigm.
	 * 
	 * @param objX
	 *            X-axis world coordinate.
	 * @param objY
	 *            Y-axis world coordinate
	 * @return A vector whose components are the screen coordinates
	 *         corresponding to the given world coordinates.
	 * @throws GLSingletonNotInitializedException
	 * @see {@link #getScreen2World(int, int, boolean)}
	 */
	static public Vector3f getWorld2Screen(float objX, float objY)
			throws GLSingletonNotInitializedException {
		int viewport[] = new int[4];
		double mvmatrix[] = new double[16];
		double projmatrix[] = new double[16];
		double scoord[] = new double[4];// wx, wy, wz;// returned xyz coords

		GLSingleton.getGL().glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		GLSingleton.getGL().glGetDoublev(GL.GL_MODELVIEW_MATRIX, mvmatrix, 0);
		GLSingleton.getGL()
				.glGetDoublev(GL.GL_PROJECTION_MATRIX, projmatrix, 0);

		GLSingleton.getGLU().gluProject(objX, objY, 0.0f, mvmatrix, 0,
				projmatrix, 0, viewport, 0, scoord, 0);

		return new Vector3f((float) scoord[0], (float) scoord[1],
				(float) scoord[2]);
	}

	/**
	 * Switch to orthographic projection The current projection and modelview
	 * matrix are saved (push). You can loads projection and modelview matrices
	 * with endOrtho
	 * 
	 * @param screenHeight
	 *            The height of the screen.
	 * @param screenWidth
	 *            The width of the screen.
	 * @param glDim
	 *            Horizontal dimension of 2D plane.
	 * @throws GLSingletonNotInitializedException
	 * @see {@link #endOrtho()}
	 */
	static public void beginOrtho(int screenHeight, int screenWidth, float glDim)
			throws GLSingletonNotInitializedException {
		/*
		 * We save the current projection matrix and we define a viewing volume
		 * in the orthographic mode. Projection matrix stack defines how the
		 * scene is projected to the screen.
		 */
		// Disables Depth Testing
		GLSingleton.getGL().glDisable(GL.GL_DEPTH_TEST);
		// Select the Projection matrix
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		// Save the current projection matrix
		GLSingleton.getGL().glPushMatrix();
		// Reset the current projection matrix to creates a new Orthographic
		// projection
		GLSingleton.getGL().glLoadIdentity();
		// Creates a new orthographic viewing volume
		float h = (float) screenHeight / (float) screenWidth;
		// left, right, bottom, top, near, far
		GLSingleton.getGL().glOrtho(0.0f, glDim, 0.0f, glDim * h, -1.0, 1.0);
		/*
		 * Select, save and reset the modelview matrix. Modelview matrix stack
		 * store transformation like translation, rotation ...
		 */
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
		GLSingleton.getGL().glLoadIdentity();
	}

	/**
	 * Load projection and modelview matrices previously saved by the method
	 * beginOrtho
	 * 
	 * @throws GLSingletonNotInitializedException
	 * @see {@link #beginOrtho(int, int, float)}
	 */
	static public void endOrtho() throws GLSingletonNotInitializedException {
		// Select the Projection matrix stack
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		// Load the previous Projection matrix (Generally, it is a Perspective
		// projection)
		GLSingleton.getGL().glPopMatrix();
		// Select the Modelview matrix stack
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
		// Enables Depth Testing
		GLSingleton.getGL().glEnable(GL.GL_DEPTH_TEST);
	}
	
	public static void displacementBegin(
			Vector3f cameraPosition, Vector3f objectPosition, float offset)
	throws GLSingletonNotInitializedException {
		// Save the current modelview matrix
		GLSingleton.getGL().glPushMatrix();
		// Calculate the projection vector from camera to object (y=0)
		Vector3f camToObjectProjection = new Vector3f();
		camToObjectProjection.setX(cameraPosition.getX()
				- objectPosition.getX());
		camToObjectProjection.setY(0.0f);
		camToObjectProjection.setZ(cameraPosition.getZ()
				- objectPosition.getZ());
		// The normalized vector will be used to calculate the dot product
		Vector3f normalizedCamToObjectProjection = camToObjectProjection
				.getNormalizedVector();

		// Apply offset from object position in the direction from object
		// position to camera position
		Vector3f offsetPosition = objectPosition
				.add(normalizedCamToObjectProjection.mult(offset));

		// Now we apply the transformations: first translate, then rotate yaw
		// and pitch
		GLSingleton.getGL().glTranslatef(offsetPosition.getX(), 0.0f,
				offsetPosition.getZ());
	}
	
	static public void displacementEnd() throws GLSingletonNotInitializedException {
		// restore the previously stored modelview matrix
		GLSingleton.getGL().glPopMatrix();
	}

	/**
	 * Apply a set of transformations so objects will be facing the camera while
	 * keeping locked at floor. Before applying the transformations it saves the
	 * current projection and modelview matrix (push). Once finished, you must
	 * manually load the previously saved projection and modelview matrix with
	 * billboardEnd.
	 * 
	 * @param cameraPosition
	 *            A copy of camera position vector.
	 * @param objectPosition
	 *            The position in which the object will be placed.
	 * @param offset
	 *            offset used to turn around the objectPosition.
	 * @throws GLSingletonNotInitializedException
	 * @see {@link #billboardEnd()}
	 */
	public static void billboardSphericalLockedAtFloorBegin(
			Vector3f cameraPosition, Vector3f objectPosition, float offset)
			throws GLSingletonNotInitializedException {
		// Save the current modelview matrix
		GLSingleton.getGL().glPushMatrix();
		// zDirector is an auxiliary vector which indicates z-axis direction
		Vector3f zDirector = new Vector3f(0.0f, 0.0f, 1.0f);
		// Calculate the projection vector from camera to object (y=0)
		Vector3f camToObjectProjection = new Vector3f();
		camToObjectProjection.setX(cameraPosition.getX()
				- objectPosition.getX());
		camToObjectProjection.setY(0.0f);
		camToObjectProjection.setZ(cameraPosition.getZ()
				- objectPosition.getZ());
		// The normalized vector will be used to calculate the dot product
		Vector3f normalizedCamToObjectProjection = camToObjectProjection
				.getNormalizedVector();

		// upVector will indicate if the angle between two vectors is positive
		// or negative
		// for positive angles upAux will point in the positive y direction
		Vector3f upVector = zDirector.cross(normalizedCamToObjectProjection);

		// Calculate yaw and pitch angles
		// For yaw, pitch and roll definition see
		// http://en.wikipedia.org/wiki/Tait-Bryan_angles#Tait-Bryan_angles
		float yaw = zDirector.dot(normalizedCamToObjectProjection);
		float pitch = (float) Math.atan(cameraPosition.getY()
				/ camToObjectProjection.getLength());

		// Apply offset from object position in the direction from object
		// position to camera position
		Vector3f offsetPosition = objectPosition
				.add(normalizedCamToObjectProjection.mult(offset));

		// Now we apply the transformations: first translate, then rotate yaw
		// and pitch
		GLSingleton.getGL().glTranslatef(offsetPosition.getX(), 0.0f,
				offsetPosition.getZ());
		// upVector.getX() and upVector.getZ() will always be 0
		GLSingleton.getGL().glRotatef((float) Math.acos(yaw) * 180 / 3.14f,
				upVector.getX(), upVector.getY(), upVector.getZ());
		GLSingleton.getGL().glRotatef(-pitch * 180 / 3.14f, 1.0f, 0.0f, 0.0f);
	}

	/**
	 * Load projection and modelview matrices previously saved by the method
	 * billboardSphericalLockedAtFloorBegin
	 * 
	 * @throws GLSingletonNotInitializedException
	 * @see {@link #billboardSphericalLockedAtFloorBegin(Vector3f, Vector3f, float)}
	 */
	static public void billboardEnd() throws GLSingletonNotInitializedException {
		// restore the previously stored modelview matrix
		GLSingleton.getGL().glPopMatrix();
	}

	static public void setOrthoProjection(int screenHeight, int screenWidth,
			float glDim) throws GLSingletonNotInitializedException {
		// Disables Depth Testing
		GLSingleton.getGL().glDisable(GL.GL_DEPTH_TEST);
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		GLSingleton.getGL().glLoadIdentity();
		float h = (float) screenHeight / (float) screenWidth;
		GLSingleton.getGL().glOrtho(0.0, glDim, 0.0, glDim * h, -1, 1);
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
	}

	static public void setPerspectiveProjection(int screenHeight,
			int screenWidth) throws GLSingletonNotInitializedException {
		// Enables Depth Testing
		GLSingleton.getGL().glEnable(GL.GL_DEPTH_TEST);
		GLSingleton.getGL().glMatrixMode(GL.GL_PROJECTION);
		GLSingleton.getGL().glLoadIdentity();
		float h = (float) screenWidth / (float) screenHeight;
		GLSingleton.getGLU().gluPerspective(60.0f, h, 0.1f, 50.0f);
		GLSingleton.getGL().glMatrixMode(GL.GL_MODELVIEW);
	}

	static public void debugPrintCoords(int x, int y)
			throws GLSingletonNotInitializedException {
		System.err.println("screen coords: " + x + " " + y);
		Vector3f v = GLUtils.getScreen2World((int) x, (int) y, false);
		System.err.println("world coords: " + v.getX() + " " + v.getY());
		v = GLUtils.getScreen2World((int) x, (int) y, true);
		System.err.println("relative world coords: " + v.getX() + " "
				+ v.getY());
	}

	private static int selectFont(int font) {
		int selectedFont;
		switch (font) {
		case 1:
			selectedFont = GLUT.BITMAP_TIMES_ROMAN_10;
			break;
		case 2:
			selectedFont = GLUT.BITMAP_TIMES_ROMAN_24;
			break;
		case 3:
			selectedFont = GLUT.BITMAP_HELVETICA_10;
			break;
		case 4:
			selectedFont = GLUT.BITMAP_HELVETICA_12;
			break;
		case 5:
			selectedFont = GLUT.BITMAP_HELVETICA_18;
			break;
		case 6:
			selectedFont = GLUT.BITMAP_8_BY_13;
			break;
		case 7:
			selectedFont = GLUT.BITMAP_9_BY_15;
			break;
		default:
			selectedFont = GLUT.BITMAP_TIMES_ROMAN_10;
			break;
		}
		return selectedFont;
	}

	public static void renderBitmapString(float x, float y, int font,
			String string) throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
		GLSingleton.getGL().glRasterPos2f(x, y);
		GLSingleton.getGLUT()
				.glutBitmapString(GLUtils.selectFont(font), string);
	}

	public static void renderBitmapString(float x, float y, float z, int font,
			String string) throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
		GLSingleton.getGL().glRasterPos3f(x, y, z);
		GLSingleton.getGLUT()
				.glutBitmapString(GLUtils.selectFont(font), string);
	}

	public static void renderSpacedBitmapString(float x, float y, int spacing,
			int font, String string) throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
		float x1 = x;
		for (int i = 0; i < string.length(); i++) {
			GLSingleton.getGL().glRasterPos2f(x1, y);
			GLSingleton.getGLUT().glutBitmapCharacter(GLUtils.selectFont(font),
					string.charAt(i));
			x1 = x1
					+ GLSingleton.getGLUT().glutBitmapWidth(
							GLUtils.selectFont(font), string.charAt(i))
					+ spacing;
		}
	}

	public static float[][] getShadowMatrix(float[] groundPlane,
			Vector3f lightPosition) {
		float[][] shadowMatrix = new float[4][4];

		/*
		 * Find dot product between light position vector and ground plane
		 * normal.
		 */
		float dot = groundPlane[0] * lightPosition.getX() + groundPlane[1]
				* lightPosition.getY() + groundPlane[2] * lightPosition.getZ()
				+ groundPlane[3] * 1.0f;

		shadowMatrix[0][0] = dot - lightPosition.getX() * groundPlane[0];
		shadowMatrix[1][0] = 0.f - lightPosition.getX() * groundPlane[1];
		shadowMatrix[2][0] = 0.f - lightPosition.getX() * groundPlane[2];
		shadowMatrix[3][0] = 0.f - 1.0f * groundPlane[3];

		shadowMatrix[0][1] = 0.f - lightPosition.getY() * groundPlane[0];
		shadowMatrix[1][1] = dot - lightPosition.getY() * groundPlane[1];
		shadowMatrix[2][1] = 0.f - lightPosition.getY() * groundPlane[2];
		shadowMatrix[3][1] = 0.f - 1.0f * groundPlane[3];

		shadowMatrix[0][2] = 0.f - lightPosition.getZ() * groundPlane[0];
		shadowMatrix[1][2] = 0.f - lightPosition.getZ() * groundPlane[1];
		shadowMatrix[2][2] = dot - lightPosition.getZ() * groundPlane[2];
		shadowMatrix[3][2] = 0.f - 1.0f * groundPlane[3];

		shadowMatrix[0][3] = 0.f - 1.0f * groundPlane[0];
		shadowMatrix[1][3] = 0.f - 1.0f * groundPlane[1];
		shadowMatrix[2][3] = 0.f - 1.0f * groundPlane[2];
		shadowMatrix[3][3] = dot - 1.0f * groundPlane[3];

		return shadowMatrix;
	}

	/* Find the plane equation given 3 points. */
	public static float[] findPlane(Vector3f A, Vector3f B, Vector3f C) {
		float[] plane = new float[4];
		// http://www.jtaylor1142001.net/calcjat/Solutions/VPlanes/VP3Pts.htm
		Vector3f AB = new Vector3f();
		Vector3f AC = new Vector3f();

		/* Need 2 vectors to find cross product. */
		AB.setX(B.getX() - A.getX());
		AB.setY(B.getY() - A.getY());
		AB.setZ(B.getZ() - A.getZ());

		AC.setX(C.getX() - A.getX());
		AC.setY(C.getY() - A.getY());
		AC.setZ(C.getZ() - A.getZ());
		// Find the cross product
		Vector3f n = AB.cross(AC);

		// Create the plane
		plane[0] = n.getX();
		plane[1] = n.getY();
		plane[2] = n.getZ();
		plane[3] = -(plane[0] * A.getX() + plane[1] * A.getY() + plane[2]
				* A.getZ());

		return plane;
	}

	public static boolean enableMultisample()
			throws GLSingletonNotInitializedException {
		boolean multisampleAvailable = false;
		if (GLSingleton.getGL().isExtensionAvailable("GL_ARB_multisample")) {
			// Do not forget to set sample buffers to true in GLCapabilities
			GLSingleton.getGL().glEnable(GL.GL_MULTISAMPLE);
			multisampleAvailable = true;
		}
		return multisampleAvailable;
	}

	public static void disableMultisample()
			throws GLSingletonNotInitializedException {
		if (GLSingleton.getGL().isExtensionAvailable("GL_ARB_multisample")
				&& GLSingleton.getGL().glIsEnabled(GL.GL_MULTISAMPLE)) {
			GLSingleton.getGL().glDisable(GL.GL_MULTISAMPLE);
		}
	}

	public static FloatBuffer doShadowCalculations(boolean renderShadow,
			boolean stencilShadow, Vector3f lightSource)
			throws GLSingletonNotInitializedException {
		// After we render the new camera and spotlight position we calculate
		// the floor shadow
		// The spotlight and the camera are together, so we use camera position
		// plus an additional offset to calculate the shadow matrix
		float[] floorPlane = { 0.0f, 1.0f, 0.0f, 0.0f };
		float[][] floorShadow = GLUtils
				.getShadowMatrix(floorPlane, lightSource);
		// float [][] floorShadow =
		// GLUtils.getShadowMatrix(GLUtils.findPlane(new
		// Vector3f(0.0f,0.0f,0.0f), new Vector3f(0.0f,0.0f,1.0f), new
		// Vector3f(1.0f,0.0f,0.0f)), lightSource);
		FloatBuffer floorShadowBuf = BufferUtil.newFloatBuffer(16);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				floorShadowBuf.put(floorShadow[i][j]);
			}
		}
		floorShadowBuf.rewind();
		if (renderShadow) {
			if (stencilShadow) {
				/*
				 * Draw the floor with stencil value 3. This helps us only draw
				 * the shadow once per floor pixel (and only on the floor
				 * pixels).
				 */
				GLSingleton.getGL().glEnable(GL.GL_STENCIL_TEST);
				GLSingleton.getGL().glStencilFunc(GL.GL_ALWAYS, 3, 0xffffffff);
				GLSingleton.getGL().glStencilOp(GL.GL_KEEP, GL.GL_KEEP,
						GL.GL_REPLACE);
			}
		}
		return floorShadowBuf;
	}

	public static void renderProjectedShadow(boolean renderShadow,
			boolean stencilShadow, FloatBuffer floorShadowBuf,
			GLViewManager activeViewManager)
			throws GLSingletonNotInitializedException {
		if (renderShadow) {
			/* Render the projected shadow. */
			if (stencilShadow) {
				/*
				 * Now, only render where stencil is set above 2 (ie, 3 where
				 * the top floor is). Update stencil with 2 where the shadow
				 * gets drawn so we don't redraw (and accidently reblend) the
				 * shadow).
				 */
				GLSingleton.getGL().glStencilFunc(GL.GL_LESS, 2, 0xffffffff); /*
																			 * draw
																			 * if
																			 * ==
																			 * 1
																			 */
				GLSingleton.getGL().glStencilOp(GL.GL_REPLACE, GL.GL_REPLACE,
						GL.GL_REPLACE);
			}
			/*
			 * To eliminate depth buffer artifacts, we use polygon offset to
			 * raise the depth of the projected shadow slightly so that it does
			 * not depth buffer alias with the floor.
			 */
			GLSingleton.getGL().glEnable(GL.GL_POLYGON_OFFSET_FILL);
			GLSingleton.getGL().glPolygonOffset(-1.0f, -1.0f); // Negative
																// offset pull
																// the object
																// towards the
																// viewer
			/*
			 * Render 50% black shadow color on top of whatever the floor
			 * appareance is.
			 */
			// GLSingleton.getGL().glEnable(GL.GL_BLEND);
			// GLSingleton.getGL().glBlendFunc(GL.GL_SRC_ALPHA,
			// GL.GL_ONE_MINUS_SRC_ALPHA);
			GLSingleton.getGL().glDisable(GL.GL_LIGHTING); /*
															 * Force the 50%
															 * black.
															 */
			GLSingleton.getGL().glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
			GLSingleton.getGL().glPushMatrix();
			/* Project the shadow. */
			GLSingleton.getGL().glMultMatrixf(floorShadowBuf);
			activeViewManager.drawShadows();
			GLSingleton.getGL().glPopMatrix();
			// GLSingleton.getGL().glDisable(GL.GL_BLEND);
			GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
			GLSingleton.getGL().glDisable(GL.GL_POLYGON_OFFSET_FILL);
			if (stencilShadow) {
				GLSingleton.getGL().glDisable(GL.GL_STENCIL_TEST);
			}
		}
	}
	
	/**
	 * http://www.lampos.net/how-to-sort-hashmap
	 */
	public static Map<GLObject,Float> sortHashMap(HashMap<GLObject,Float> input, boolean descending) {
		Map<GLObject,Float> tempMap = new HashMap<GLObject,Float>();
		for (GLObject glo : input.keySet())
			tempMap.put(glo, input.get(glo));
		
		List<GLObject> mapKeys = new ArrayList<GLObject>(tempMap.keySet());
		List<Float> mapValues = new ArrayList<Float>(tempMap.values());
		Map<GLObject,Float> sortedMap = new LinkedHashMap<GLObject, Float>();
		TreeSet<Float> sortedSet = new TreeSet<Float>(mapValues);
		Object[] sortedArray = sortedSet.toArray();
		
		if (descending) {
			for (int i = sortedArray.length-1; i >= 0; i--) {
				sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), (Float)sortedArray[i]);
			}
		} else {
			for (int i = 0; i < sortedArray.length; i++) {
				sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])), (Float)sortedArray[i]);
			}
		}

		
		return sortedMap;
	}

}
