package model.gl.knowledge;

import javax.media.opengl.GL;

import model.gl.GLSingleton;
import model.knowledge.Color;
import model.knowledge.Vector2f;

import exceptions.gl.GLSingletonNotInitializedException;

public class Node extends GLObject {
	// private ... texture?
	private float width;
	
	public Node (float pos_x, float pos_y, float width, Color color) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = color;
		// Base rectangular
		this.width = width;
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor4fv(color.getColorFB());
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
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
	
	public Vector2f getCenterOfArea () {
		float x = this.positionX + this.width/2;
		float y = this.positionY + this.width/2;
		return new Vector2f (x, y);
	}
	
}
