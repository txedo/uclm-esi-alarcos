package model.gl;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;

import model.knowledge.Vector2f;

import com.sun.opengl.util.BufferUtil;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLUtils {

	static public Vector2f GetOGLPos2D (int screenX, int screenY) throws GLSingletonNotInitializedException	{
		IntBuffer viewport = BufferUtil.newIntBuffer(4);
		DoubleBuffer modelview = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer projection = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer pos = BufferUtil.newDoubleBuffer(3);
		long winX, winY, winZ;

		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glLoadIdentity();
			
			GLSingleton.getGL().glGetDoublev( GL.GL_MODELVIEW_MATRIX, modelview );
			GLSingleton.getGL().glGetDoublev( GL.GL_PROJECTION_MATRIX, projection );
			GLSingleton.getGL().glGetIntegerv( GL.GL_VIEWPORT, viewport );

			winX = (long)screenX;
			// Important: gl (0,0) is bottom left but window coordinates (0,0) are top left so we should change this!
			// but since we are working with relative coords (see pushmatrix and loadidentity above) we don't change it
			// winY = (long)viewport.get(3) - (long)screenY;
			winY = (long)screenY; 
			winZ = 0;
		
			//GLSingleton.getGL().glReadPixels( x, (int)winY, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, winZ );
			GLSingleton.getGLU().gluUnProject( (double)winX, (double)winY, (double)winZ, modelview, projection, viewport, pos);
		GLSingleton.getGL().glPopMatrix();

		return new Vector2f((float)pos.get(0), (float)pos.get(1));
	}
	
	static public Vector2f GetScreenPos2D (float objX, float objY) throws GLSingletonNotInitializedException {
		IntBuffer viewport = BufferUtil.newIntBuffer(4);
		DoubleBuffer modelview = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer projection = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer pos = BufferUtil.newDoubleBuffer(3);

		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glLoadIdentity();
			
			GLSingleton.getGL().glGetDoublev( GL.GL_MODELVIEW_MATRIX, modelview );
			GLSingleton.getGL().glGetDoublev( GL.GL_PROJECTION_MATRIX, projection );
			GLSingleton.getGL().glGetIntegerv( GL.GL_VIEWPORT, viewport );
			
			GLSingleton.getGLU().gluProject(objX, objY, 0.0f, modelview, projection, viewport, pos);
		GLSingleton.getGL().glPopMatrix();
			
		return new Vector2f((float)pos.get(0), (float)pos.get(1));
	}
}
