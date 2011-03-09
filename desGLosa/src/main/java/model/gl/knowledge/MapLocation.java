package model.gl.knowledge;

import javax.media.opengl.GL;

import exceptions.gl.GLSingletonNotInitializedException;
import model.gl.GLSingleton;
import model.knowledge.Color;

public class MapLocation extends Node {
	private final Color highlightColor = new Color(0.7294f, 0.0f, 1.0f);
	private int id;
	private boolean hightlighted;
	private int highlightTexture;
	private float textureRotation = 0.0f;
	private float frequency = 0.0f;	// higher value implies higher speed
	private float size;
	
	public MapLocation (int id) {
		this.id = id;
		this.hightlighted = false;
		this.color = new Color(0.0f, 0.0f, 0.0f, 0.5f);
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
		this.textureRotation = 0.0f;
		this.frequency = 0.0f;
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
//		GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);
//		GLSingleton.getGL().glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//		GLSingleton.getGL().glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
//		GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, this.highlightTexture);
//		
//		float dim = 0.20f;
//		GLSingleton.getGL().glPushMatrix();
//			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
//			GLSingleton.getGL().glRotatef(textureRotation, 0.0f, 0.0f, 1.0f);
//			GLSingleton.getGL().glBegin(GL.GL_QUADS);
//				GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);	GLSingleton.getGL().glVertex2f(-dim, -dim);
//				GLSingleton.getGL().glTexCoord2f(1.0f, 0.0f);	GLSingleton.getGL().glVertex2f( dim, -dim);
//				GLSingleton.getGL().glTexCoord2f(1.0f, 1.0f);	GLSingleton.getGL().glVertex2f( dim,  dim);
//				GLSingleton.getGL().glTexCoord2f(0.0f, 1.0f);	GLSingleton.getGL().glVertex2f(-dim,  dim);
//			GLSingleton.getGL().glEnd();
//			this.textureRotation -= 10.0f;
//		GLSingleton.getGL().glPopMatrix();
//		GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);
		
		this.size = this.size + (float)Math.sin(this.frequency*180.0f/3.14f)*1.5f;
		this.frequency += 0.005;
		GLSingleton.getGL().glColor3fv(this.highlightColor.getColorFB());
	}

	public void setHighlightTexture(int highlightTexture) {
		this.highlightTexture = highlightTexture;
	}

}
