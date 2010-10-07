package dominio.conocimiento;

import javax.media.opengl.GL;

public class Node extends Figure {
	// private ... texture?
	private float width;
	
	public Node (GL gl, float origin_x, float origin_z, float width, Color color) {
		this.gl = gl;
		this.origin_x = origin_x;
		this.origin_z = origin_z;
		this.color = color;
		// Base rectangular
		this.width = width;
	}
	
	@Override
	public void draw() {
		gl.glColor4fv(color.getColorFB());
		gl.glBegin(GL.GL_QUADS);	
			gl.glVertex2f(this.origin_x, this.origin_z);
			gl.glVertex2f(this.origin_x, this.origin_z + width);
			gl.glVertex2f(this.origin_x + width, this.origin_z + width);
			gl.glVertex2f(this.origin_x + width, this.origin_z);
		gl.glEnd();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
	
}