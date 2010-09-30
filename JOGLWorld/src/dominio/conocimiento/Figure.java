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
}
