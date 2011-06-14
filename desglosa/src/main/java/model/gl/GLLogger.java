package model.gl;

import javax.media.opengl.GL2;

import exceptions.GLSingletonNotInitializedException;

public class GLLogger {
	private String vendor;
	private String renderer;
	private String version;
	private String extensions;
	private float [] aliased_point_size_range = new float[2];
	private float [] smooth_point_size_range = new float[2];
	private float [] smooth_point_size_granularity = new float[1];
	private int [] elements_vertices = new int[1];
	private int [] elements_indices = new int[1];
	private int [] stencilbuffer_bits = new int[1];
	private int [] sample_buffers = new int[1];
	private int [] samples = new int[1];

	public GLLogger () throws GLSingletonNotInitializedException {
		vendor = GLSingleton.getGL().glGetString(GL2.GL_VENDOR);
		renderer = GLSingleton.getGL().glGetString(GL2.GL_RENDERER);
		version = GLSingleton.getGL().glGetString(GL2.GL_VERSION);
		extensions = GLSingleton.getGL().glGetString(GL2.GL_EXTENSIONS);
		
		GLSingleton.getGL().glGetFloatv(GL2.GL_ALIASED_POINT_SIZE_RANGE, aliased_point_size_range, 0);
		GLSingleton.getGL().glGetFloatv(GL2.GL_SMOOTH_POINT_SIZE_RANGE, smooth_point_size_range, 0);
		GLSingleton.getGL().glGetFloatv(GL2.GL_SMOOTH_POINT_SIZE_GRANULARITY, smooth_point_size_granularity, 0);
		
		GLSingleton.getGL().glGetIntegerv(GL2.GL_MAX_ELEMENTS_VERTICES, elements_vertices, 0);
		GLSingleton.getGL().glGetIntegerv(GL2.GL_MAX_ELEMENTS_INDICES, elements_indices, 0);
		
		GLSingleton.getGL().glGetIntegerv(GL2.GL_STENCIL_BITS, stencilbuffer_bits, 0);
		
		GLSingleton.getGL().glGetIntegerv(GL2.GL_SAMPLE_BUFFERS, sample_buffers, 0);
		GLSingleton.getGL().glGetIntegerv(GL2.GL_SAMPLES, samples, 0);
	}

	public String toString() {
		//extensions = extensions.replace(" ", "\n\t");
		StringBuffer sb = new StringBuffer();
		sb.append("VENDOR: " + vendor + "\n");
		sb.append("RENDERER: " + renderer + "\n");
		sb.append("VERSION: " + version + "\n");
		sb.append("EXTENSIONS: " + extensions + "\n");
		sb.append("\n");
		sb.append("ALIASED_POINT_SIZE_RANGE: [" + aliased_point_size_range[0] + ", " + aliased_point_size_range[1] + "]\n");
		sb.append("SMOOTH_POINT_SIZE_RANGE: [" + smooth_point_size_range[0] + ", " + smooth_point_size_range[1] + "]\n");
		sb.append("SMOOTH_POINT_SIZE_GRANULARITY: " + smooth_point_size_granularity[0] + "\n");
		sb.append("\n");
		sb.append("MAX_ELEMENTS_VERTICES: " + elements_vertices[0] + "\n");
		sb.append("MAX_ELEMENTS_INDICES: " + elements_indices[0] + "\n");
		sb.append("\n");
		sb.append("SUPPORTED_STENCIL_BITS: " + stencilbuffer_bits[0] + "\n");
		sb.append("\n");
		sb.append("NUMBER_OF_SAMPLE_BUFFERS: " + sample_buffers[0] + "\n");
		sb.append("NUMBER_OF_SAMPLES: " + samples[0] + "\n");
		return sb.toString();
	}
	
	public void printToGL(int h, int w, float d) throws GLSingletonNotInitializedException {
		String [] lines = this.toString().split("\n");
		int counter = 0;
		GLUtils.beginOrtho(h, w, d);
			float vgap = GLUtils.getScreen2World(0, 10, true).getY();
			for (String line : lines) {
				GLUtils.renderBitmapString(0.0f, (vgap*lines.length)-(vgap*counter), 1, line);
				counter++;
			}
		GLUtils.endOrtho();
	}

}
