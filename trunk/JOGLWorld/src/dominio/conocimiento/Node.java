package dominio.conocimiento;

import javax.media.opengl.GL;

import dominio.control.GLSingleton;
import exceptions.GLSingletonNotInitializedException;

public class Node extends GLObject {
	// private ... texture?
	private float width;
	
	public Node (float origin_x, float origin_z, float width, Color color) {
		this.origin_x = origin_x;
		this.origin_z = origin_z;
		this.color = color;
		// Base rectangular
		this.width = width;
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor4fv(color.getColorFB());
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.origin_x, this.origin_z, 0.0f);
			GLSingleton.getGL().glBegin(GL.GL_QUADS);	
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
				GLSingleton.getGL().glVertex2f(0.0f, width);
				GLSingleton.getGL().glVertex2f(width, width);
				GLSingleton.getGL().glVertex2f(width, 0.0f);
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
	
}
