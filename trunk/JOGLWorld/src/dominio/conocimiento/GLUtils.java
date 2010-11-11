package dominio.conocimiento;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL;

import com.sun.opengl.util.BufferUtil;

import dominio.control.GLSingleton;
import exceptions.GLSingletonNotInitializedException;

public class GLUtils {

	static public Vector2f GetOGLPos2D (int screenX, int screenY) throws GLSingletonNotInitializedException	{
		IntBuffer viewport = BufferUtil.newIntBuffer(4);
		DoubleBuffer modelview = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer projection = BufferUtil.newDoubleBuffer(16);
		
		long winX, winY, winZ;
		DoubleBuffer pos = BufferUtil.newDoubleBuffer(3);

		GLSingleton.getGL().glGetDoublev( GL.GL_MODELVIEW_MATRIX, modelview );
		GLSingleton.getGL().glGetDoublev( GL.GL_PROJECTION_MATRIX, projection );
		GLSingleton.getGL().glGetIntegerv( GL.GL_VIEWPORT, viewport );

		winX = (long)screenX;
		winY = (long)viewport.get(3) - (long)screenY;
		winZ = 0;
		//gl.glReadPixels( x, (int)winY, 1, 1, GL.GL_DEPTH_COMPONENT, GL.GL_FLOAT, winZ );
		GLSingleton.getGLU().gluUnProject( (double)winX, (double)winY, (double)winZ, modelview, projection, viewport, pos);

		return new Vector2f((float)pos.get(0), (float)pos.get(1));
	}
	
	static public Vector2f GetScreenPos2D (float objX, float objY) throws GLSingletonNotInitializedException {
		IntBuffer viewport = BufferUtil.newIntBuffer(4);
		DoubleBuffer modelview = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer projection = BufferUtil.newDoubleBuffer(16);
		DoubleBuffer pos = BufferUtil.newDoubleBuffer(3);

		GLSingleton.getGL().glGetDoublev( GL.GL_MODELVIEW_MATRIX, modelview );
		GLSingleton.getGL().glGetDoublev( GL.GL_PROJECTION_MATRIX, projection );
		GLSingleton.getGL().glGetIntegerv( GL.GL_VIEWPORT, viewport );
		GLSingleton.getGLU().gluProject(objX, objY, 0.0f, modelview, projection, viewport, pos);
		
		return new Vector2f((float)pos.get(0), (float)pos.get(1));
	}
}
