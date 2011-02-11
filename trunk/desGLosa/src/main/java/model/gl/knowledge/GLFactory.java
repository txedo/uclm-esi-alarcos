package model.gl.knowledge;

import javax.media.opengl.GL;

import model.gl.GLSingleton;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactory extends GLObject {
	// We define the base dimensions
	private float baseLength;
	private float baseWidth;
	private float baseHeight;
	// Smokestack radius and building length will depend on base dimensions
	private int smokestackHeight;
	private float smokestackRadius;
	private float buildingHeight;
	// Roof height will be 1/3 of building height
	private float roofHeight;
	private final float ROOFANGLE = 45.0f;
	
	public GLFactory (float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		
		this.baseLength = 2.0f;
		this.baseWidth = 1.0f;
		this.baseHeight = 0.5f;
		
		this.smokestackHeight = 0;
		if (this.baseLength/2 > this.baseWidth)	this.smokestackRadius = this.baseWidth/2;
		else this.smokestackRadius = this.baseLength/2;
		
		this.buildingHeight = 3.0f;
		this.roofHeight = this.buildingHeight/3;
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGL().glTranslatef(this.positionX, 0.0f, this.positionY);
			// Draw the factory base
			this.drawBase();
			
			
		GLSingleton.getGL().glPopMatrix();
	}
	
	private void drawBase() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, this.baseHeight, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength, this.baseHeight, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength, 0.0f, 0.0f);
			// Right
			GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, this.baseHeight, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, this.baseHeight, this.baseWidth);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, this.baseWidth);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Left
			GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Top
			GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuilding() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Right
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			// Left
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuildingRoof() throws GLSingletonNotInitializedException {
		
	}
	
	private void drawSmokestack() throws GLSingletonNotInitializedException {
		
	}

	public int getSmokestackHeight() {
		return smokestackHeight;
	}

	public void setSmokestackHeight(int smokestackHeight) {
		this.smokestackHeight = smokestackHeight;
	}

}
