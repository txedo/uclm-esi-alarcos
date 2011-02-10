package model.gl.knowledge;

import javax.media.opengl.GL;

import exceptions.gl.GLSingletonNotInitializedException;
import model.gl.GLSingleton;
import model.knowledge.Color;

public class MapLocation extends Node {
	private final Color highlightColor = new Color(1.0f, 0.0f, 0.0f);
	private int id;
	private boolean hightlighted;
	private float size;
	
	public MapLocation (int id) {
		this.id = id;
		this.hightlighted = false;
		this.color = new Color(0.0f, 0.0f, 0.0f);
		this.size = 20.0f;
	}
	
	public MapLocation (int id, float pos_x, float pos_y, float size, Color color) {
		this.id = id;
		this.hightlighted = false;
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = color;
		// Base rectangular
		this.size = size;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHightlighted() {
		return hightlighted;
	}

	public void setHightlighted(boolean hightlighted) {
		this.hightlighted = hightlighted;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	@Override
	public void draw() throws GLSingletonNotInitializedException {
		if (this.hightlighted) 
			this.handleHighlighting();
		else
			GLSingleton.getGL().glColor3fv(this.color.getColorFB());
		GLSingleton.getGL().glPointSize(this.size);
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
			GLSingleton.getGL().glBegin(GL.GL_POINTS);
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
	}

	private void handleHighlighting() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3fv(this.highlightColor.getColorFB());
	}

}
