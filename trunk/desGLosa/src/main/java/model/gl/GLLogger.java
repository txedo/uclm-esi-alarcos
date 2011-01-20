package model.gl;

import javax.media.opengl.GL;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLLogger {
	private String vendor;
	private String renderer;
	private String version;
	private float [] aliased_point_size_range = new float[2];
	private float [] smooth_point_size_range = new float[2];
	private float [] smooth_point_size_granularity = new float[1];

	public GLLogger () throws GLSingletonNotInitializedException {
		vendor = GLSingleton.getGL().glGetString(GL.GL_VENDOR);
		renderer = GLSingleton.getGL().glGetString(GL.GL_RENDERER);
		version = GLSingleton.getGL().glGetString(GL.GL_VERSION);
		
		GLSingleton.getGL().glGetFloatv(GL.GL_ALIASED_POINT_SIZE_RANGE, aliased_point_size_range, 0);
		GLSingleton.getGL().glGetFloatv(GL.GL_SMOOTH_POINT_SIZE_RANGE, smooth_point_size_range, 0);
		GLSingleton.getGL().glGetFloatv(GL.GL_SMOOTH_POINT_SIZE_GRANULARITY, smooth_point_size_granularity, 0);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("VENDOR: " + vendor + "\n");
		sb.append("RENDERER: " + renderer + "\n");
		sb.append("VERSION: " + version + "\n");
		sb.append("\n");
		sb.append("ALIASED_POINT_SIZE_RANGE: [" + aliased_point_size_range[0] + ", " + aliased_point_size_range[1] + "]\n");
		sb.append("SMOOTH_POINT_SIZE_RANGE: [" + smooth_point_size_range[0] + ", " + smooth_point_size_range[1] + "]\n");
		sb.append("SMOOTH_POINT_SIZE_GRANULARITY: " + smooth_point_size_granularity[0] + "\n");
		sb.append("\n");
		return sb.toString();
	}

}