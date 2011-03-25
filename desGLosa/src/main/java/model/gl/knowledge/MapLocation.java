package model.gl.knowledge;

import javax.media.opengl.GL;

import exceptions.gl.GLSingletonNotInitializedException;
import model.gl.GLSingleton;
import model.knowledge.Color;

public class MapLocation extends Node {
	public static final float SIZE_INIT = 10.0f;
	private int id;
	private float size;
	private float frequency = 0.0f;	// higher value implies higher speed
	private boolean hightlighted;
	private final Color highlightColor = new Color(1.0f, 1.0f, 1.0f);
	private boolean faded;
	private final Color fadeColor = new Color(0.7f, 0.7f, 0.7f);
	//private final Color fadeColor = new Color(22.0f/255.0f, 141.0f/255.0f, 165.0f/255.0f);
	//private final Color fadeColor = new Color(9.0f/255.0f, 19.0f/255.0f, 52.0f/255.0f);
	
	public MapLocation (int id, float pos_x, float pos_y) {
		this.id = id;
		this.hightlighted = false;
		this.faded = false;
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = new Color(1.0f, 1.0f, 1.0f);
		this.size = MapLocation.SIZE_INIT;
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
		this.frequency = 0.0f;
	}

	public boolean isFaded() {
		return faded;
	}

	public void setFaded(boolean faded) {
		this.faded = faded;
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
		else {
			if (this.faded)
				GLSingleton.getGL().glColor3fv(this.fadeColor.getColorFB());
			else
				GLSingleton.getGL().glColor3fv(this.color.getColorFB());
		}

		GLSingleton.getGL().glPointSize(this.size);
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
			GLSingleton.getGL().glBegin(GL.GL_POINTS);
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
	}

	private void handleHighlighting() throws GLSingletonNotInitializedException {	
		this.size = MapLocation.SIZE_INIT + (float)Math.sin(this.frequency*180.0f/3.14f)*1.5f;
		this.frequency += 0.005;
		GLSingleton.getGL().glColor3fv(this.highlightColor.getColorFB());
	}

}
