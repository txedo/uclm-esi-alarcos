package model.gl.knowledge;

import javax.media.opengl.GL2;

import model.gl.GLSingleton;
import model.util.Color;


import exceptions.GLSingletonNotInitializedException;

public abstract class GLObject {
	protected int id;
	protected float positionX;
	protected float positionY;
	protected Color color;
	
	public GLObject () {
		this.id = -1;
		this.positionX = 0.0f;
		this.positionY = 0.0f;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}
	
	public GLObject (float posx, float posy) {
		this.id = -1;
		this.positionX = posx;
		this.positionY = posy;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}
	
	public abstract void draw() throws GLSingletonNotInitializedException;
	
	protected void enableLight () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL2.GL_LIGHTING);
		GLSingleton.getGL().glEnable(GL2.GL_LIGHT1);			// Habilitamos la iluminacion
	}
	
	protected void disableLight () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glDisable(GL2.GL_LIGHTING);
		GLSingleton.getGL().glDisable(GL2.GL_LIGHT1);			// Deshabilitamos la iluminación
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float origin_x) {
		this.positionX = origin_x;
	}

	public float getPositionZ() {
		return positionY;
	}

	public void setPositionZ(float origin_z) {
		this.positionY = origin_z;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
