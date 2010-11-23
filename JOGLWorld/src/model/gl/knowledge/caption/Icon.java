package model.gl.knowledge.caption;

import javax.media.opengl.GL;

import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.knowledge.Color;
import model.knowledge.Vector2f;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

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
		GLSingleton.getGL().glPushMatrix();
			Vector3f v = GLUtils.getScreen2World(this.width, this.height, true);
			GLSingleton.getGL().glColor3fv (color.getColorFB());
			GLSingleton.getGL().glBegin(GL.GL_QUADS);
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
				GLSingleton.getGL().glVertex2f(0.0f, v.getY());
				GLSingleton.getGL().glVertex2f(v.getX(), v.getY());
				GLSingleton.getGL().glVertex2f(v.getX(), 0.0f);
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
		//System.out.println(v.getX() + " " + v.getY());
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
