package model.gl.knowledge.caption;

import javax.media.opengl.GL;

import exceptions.gl.GLSingletonNotInitializedException;
import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.knowledge.Vector3f;

public class Frame extends GLObject {
	private int width;
	private int height;

	public Frame (int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glLoadIdentity();
			Vector3f v = GLUtils.getScreen2World(this.width, this.height, true);
		GLSingleton.getGL().glPopMatrix();
		GLSingleton.getGL().glBegin(GL.GL_LINE_LOOP);
			GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex2f(v.getX(), 0.0f);
			GLSingleton.getGL().glVertex2f(v.getX(), -v.getY());
			GLSingleton.getGL().glVertex2f(0.0f, -v.getY());
		GLSingleton.getGL().glEnd();
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
