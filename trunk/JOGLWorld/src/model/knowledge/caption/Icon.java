package model.knowledge.caption;

import javax.media.opengl.GL;

import model.control.GLSingleton;
import model.knowledge.Color;
import model.knowledge.GLObject;
import model.knowledge.GLUtils;
import model.knowledge.Vector2f;

import exceptions.GLSingletonNotInitializedException;

public class Icon extends GLObject {
	private int width;
	private int height;	// width and height in pixels
	private Color color;
	
	public Icon(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw () throws GLSingletonNotInitializedException {
		// The icon will be drawn in pixels (screen coords) looking at width and height, never in opengl coords
		// so the icon area will be constant independently of the screen resolution or window size
		Vector2f v = GLUtils.GetOGLPos2D(this.width, this.height);
		GLSingleton.getGL().glColor3fv (color.getColorFB());
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex2f(0.0f, v.getY());
			GLSingleton.getGL().glVertex2f(v.getX(), v.getY());
			GLSingleton.getGL().glVertex2f(v.getX(), 0.0f);
		GLSingleton.getGL().glEnd();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
		
}
