package dominio.conocimiento;

import javax.media.opengl.GL;

public abstract class Figure {
	protected float origin_x;
	protected float origin_z;
	protected Color color;
	protected GL gl;
	
	public abstract void draw ();
	
	protected void enableLight () {
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT1);			// Habilitamos la iluminacion
	}
	
	protected void disableLight () {
		gl.glDisable(GL.GL_LIGHTING);
		gl.glDisable(GL.GL_LIGHT1);			// Deshabilitamos la iluminación
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
