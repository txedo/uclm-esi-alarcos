package dominio.conocimiento;

import javax.media.opengl.GL;

import dominio.control.GLSingleton;

import exceptions.GLSingletonNotInitializedException;

public abstract class GLObject {
	protected float origin_x;
	protected float origin_z;
	protected Color color;
	
	public abstract void draw () throws GLSingletonNotInitializedException;
	
	protected void enableLight () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
		GLSingleton.getGL().glEnable(GL.GL_LIGHT1);			// Habilitamos la iluminacion
	}
	
	protected void disableLight () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glDisable(GL.GL_LIGHTING);
		GLSingleton.getGL().glDisable(GL.GL_LIGHT1);			// Deshabilitamos la iluminación
	}

	public float getOrigin_x() {
		return origin_x;
	}

	public void setOrigin_x(float origin_x) {
		this.origin_x = origin_x;
	}

	public float getOrigin_z() {
		return origin_z;
	}

	public void setOrigin_z(float origin_z) {
		this.origin_z = origin_z;
	}
}
