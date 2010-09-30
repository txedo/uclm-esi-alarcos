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
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL.GL_POLYGON);	
			gl.glVertex3f(this.origin_x, 0, this.origin_z);
			gl.glVertex3f(this.origin_x, 0, this.origin_z + width);
			gl.glVertex3f(this.origin_x + width, 0, this.origin_z + width);
			gl.glVertex3f(this.origin_x + width, 0, this.origin_z);
		gl.glEnd();
	}
	
}
