package model.gl.knowledge;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.knowledge.Color;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactory extends GLObject {
	private GLUquadric GLUQuadric;
	// We define the base dimensions
	private float baseLength;
	private float baseWidth;
	private float baseHeight;
	// Smokestack radius and building length will depend on base dimensions
	private int smokestackHeight;
	private float smokestackRadius;
	// Building dimensions
	private float buildingLength;
	private float buildingWidth;
	private float buildingHeight;
	// Roof height will be 1/3 of building height
	private float roofHeight;
	private int ROOFS = 3;
	
	public GLFactory (float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = new Color(0.0f, 0.0f, 1.0f, 1.0f);
		
		this.baseLength = 2.0f;
		this.baseWidth = 1.0f;
		this.baseHeight = 0.5f;
		
		this.buildingLength = this.baseLength/2;
		this.buildingWidth = this.baseWidth;
		this.buildingHeight = 1.2f;
		
		this.smokestackHeight = 2;
		if (this.baseLength/2 > this.baseWidth)	this.smokestackRadius = this.baseWidth/4*0.80f;
		else this.smokestackRadius = this.baseLength/4*0.80f;

		this.roofHeight = 0.3f;
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor4fv(this.color.getColorFB());
		GLSingleton.getGL().glPushMatrix();
			// Base
			GLSingleton.getGL().glTranslatef(this.positionX, 0.0f, this.positionY);
			this.drawBase();
			// Building
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glTranslatef(this.baseLength*1/4, this.baseHeight, 0.0f);
				this.drawBuilding();
			GLSingleton.getGL().glPopMatrix();
			// Roof
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glTranslatef(0.0f, this.baseHeight+this.buildingHeight, 0.0f);
				this.drawBuildingRoof();
			GLSingleton.getGL().glPopMatrix();
			// Smokestack
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glTranslatef(-this.baseLength*1/4, this.baseHeight, 0.0f);
				GLSingleton.getGL().glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				this.drawSmokestack();
			GLSingleton.getGL().glPopMatrix();
			// Draw the smokestak height
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glTranslatef(this.baseLength*1/4, (this.baseHeight+this.buildingHeight+this.roofHeight)*1.20f, 0.0f);
				GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
				GLUtils.renderBitmapString(0.0f, 0.0f, 0, 2, ""+this.smokestackHeight);
			GLSingleton.getGL().glPopMatrix();
		GLSingleton.getGL().glPopMatrix();
		
		GLSingleton.getGL().glDisableClientState(GL.GL_VERTEX_ARRAY);
		GLSingleton.getGL().glDisableClientState(GL.GL_NORMAL_ARRAY);
	}
	
	private void drawBase() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f,            -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, 0.0f,            -this.baseWidth/2);
			// Right
			GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, 0.0f,            -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, this.baseHeight,  this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, 0.0f, 			this.baseWidth/2);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f,            this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, this.baseWidth/2);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, this.baseHeight, this.baseWidth/2);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, 0.0f,            this.baseWidth/2);
			// Left
			GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f,            -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight,  this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f, 			 this.baseWidth/2);
			// Top
			GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f( 0.0f, 			   this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glVertex3f( 0.0f,              this.baseHeight,  this.baseWidth/2);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight,  this.baseWidth/2);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuilding() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f,            -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, 0.0f,            -this.buildingWidth/2);
			// Right
			GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, 0.0f,            -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, this.buildingHeight,  this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, 0.0f, 			this.buildingWidth/2);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f,            this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight, this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, this.buildingHeight, this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, 0.0f,            this.buildingWidth/2);
			// Left
			GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f,            -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight,  this.buildingWidth/2);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f, 			 this.buildingWidth/2);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuildingRoof() throws GLSingletonNotInitializedException {
		float roofLength = this.buildingLength/this.ROOFS;
		float alpha = (float)Math.asin(this.roofHeight/roofLength);
		for (int i = 0; i < this.ROOFS; i++) {
			GLSingleton.getGL().glBegin(GL.GL_QUADS);
				// Top
				GLSingleton.getGL().glNormal3f((float)Math.cos(180.0-alpha), (float)Math.sin(180.0-alpha), 0.0f);
				GLSingleton.getGL().glVertex3f(i*roofLength,     0.0f,            -this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight, -this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight,  this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f(i*roofLength,     0.0f, 			  this.buildingWidth/2);
				// Wall
				GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, 0.0f,             this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight,  this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight, -this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, 0.0f,            -this.buildingWidth/2);
			GLSingleton.getGL().glEnd();
			GLSingleton.getGL().glBegin(GL.GL_TRIANGLES);
				// Back
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
				GLSingleton.getGL().glVertex3f(i*roofLength,     0.0f,             -this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight,  -this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, 0.0f,             -this.buildingWidth/2);
				// Front
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
				GLSingleton.getGL().glVertex3f(i*roofLength,     0.0f,             this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight,  this.buildingWidth/2);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, 0.0f,             this.buildingWidth/2);
			GLSingleton.getGL().glEnd();
		}

	}
	
	private void drawSmokestack() throws GLSingletonNotInitializedException {
		GLSingleton.getGLU().gluCylinder(this.GLUQuadric, this.smokestackRadius, this.smokestackRadius, this.smokestackHeight/5.0, 32, 32);
	}

	public int getSmokestackHeight() {
		return smokestackHeight;
	}

	public void setSmokestackHeight(int smokestackHeight) {
		this.smokestackHeight = smokestackHeight;
	}

	public void setGLUQuadric(GLUquadric gLUQuadric) {
		GLUQuadric = gLUQuadric;
	}

}
