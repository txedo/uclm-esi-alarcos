package model.gl.knowledge;

import javax.media.opengl.GL;

import model.gl.GLSingleton;
import model.knowledge.Color;


import exceptions.gl.GLSingletonNotInitializedException;

public abstract class GLObject {
	protected float positionX;
	protected float positionY;
	protected Color color;
	
	public GLObject () {
		this.positionX = 0.0f;
		this.positionY = 0.0f;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}
	
	public GLObject (float posx, float posy) {
		this.positionX = posx;
		this.positionY = posy;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}
	
	public abstract void draw() throws GLSingletonNotInitializedException;
	
	protected void enableLight () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
		GLSingleton.getGL().glEnable(GL.GL_LIGHT1);			// Habilitamos la iluminacion
	}
	
	protected void disableLight () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glDisable(GL.GL_LIGHTING);
		GLSingleton.getGL().glDisable(GL.GL_LIGHT1);			// Deshabilitamos la iluminación
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
