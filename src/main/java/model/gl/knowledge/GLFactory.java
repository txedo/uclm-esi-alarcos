package model.gl.knowledge;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.knowledge.Color;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactory extends GLObject3D {
	// Constants to define object dimensions
	private final float BASE_LENGTH = 2.0f;
	private final float BASE_WIDTH = 1.0f;
	private final float BASE_HEIGHT = 0.5f;
	private final float BUILDING_HEIGHT = 1.2f;
	private final float ROOF_HEIGHT = 0.3f;
	private final float SMOKESTACK_BUILDING_GAP = 0.80f;
	
	private GLUquadric GLUQuadric;
	private int texture;
	// We define the base dimensions
	private float baseLength;
	private float baseWidth;
	private float baseHeight;
	// Smokestack radius and building length will depend on base dimensions
	private int smokestackHeight;	// The number of projects assigned to this factory
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
		this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		
		this.baseLength = this.BASE_LENGTH;
		this.baseWidth = this.BASE_WIDTH;
		this.baseHeight = this.BASE_HEIGHT;
		
		this.buildingLength = this.baseLength/2;
		this.buildingWidth = this.baseWidth;
		this.buildingHeight = this.BUILDING_HEIGHT;
		
		this.smokestackHeight = 0;
		if (this.baseLength/2 > this.baseWidth)	this.smokestackRadius = this.baseWidth/4*SMOKESTACK_BUILDING_GAP;
		else this.smokestackRadius = this.baseLength/4*SMOKESTACK_BUILDING_GAP;

		this.roofHeight = this.ROOF_HEIGHT;
	}
	
	@Override
	protected void draw(boolean shadow) throws GLSingletonNotInitializedException {
		GLUtils.enableMultisample();
		// Only enable texture if you are going to draw the real object, no its shadow
		if (shadow){
			GLSingleton.getGL().glColor4fv(super.SHADOW_COLOR.getColorFB());
		}
		else {
			GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
			GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
			GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D, texture);	
		}
		
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
			// If we enabled textures before, we disable them now to draw the smokestack
			if (!shadow) GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_2D);	
			// Smokestack
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glTranslatef(-this.baseLength*1/4, this.baseHeight, 0.0f);
				GLSingleton.getGL().glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
				this.drawSmokestack(shadow);
			GLSingleton.getGL().glPopMatrix();
			if (!shadow) {
				// Draw the smokestack value (only if we are drawing the real object
				GLSingleton.getGL().glPushMatrix();
					GLSingleton.getGL().glTranslatef(this.baseLength*1/4, (this.baseHeight+this.buildingHeight+this.roofHeight)*1.20f, 0.0f);
					GLUtils.renderBitmapString(0.0f, 0.0f, 0, 2, ""+this.smokestackHeight);
				GLSingleton.getGL().glPopMatrix();
			}
		GLSingleton.getGL().glPopMatrix();
		GLUtils.disableMultisample();
	}
	
	private void drawBase() throws GLSingletonNotInitializedException {	
		GLSingleton.getGL().glBegin(GL2.GL_QUADS);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f,            -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(1.0f, 0.25f);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(1.0f, 0.0f);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, 0.0f,            -this.baseWidth/2);
			// Right
			GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, 0.0f,            -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.5f, 0.25f);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, this.baseHeight,  this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.5f, 0.0f);
			GLSingleton.getGL().glVertex3f(this.baseLength/2, 0.0f, 			this.baseWidth/2);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f,            this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(1.0f, 0.25f);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, this.baseHeight, this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(1.0f, 0.0f);
			GLSingleton.getGL().glVertex3f( this.baseLength/2, 0.0f,            this.baseWidth/2);
			// Left
			GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f,            -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.5f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight,  this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.5f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, 0.0f, 			this.baseWidth/2);
			// Top
			GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f( 0.0f, 			   this.baseHeight, -this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.5f, 0.25f);
			GLSingleton.getGL().glVertex3f( 0.0f,              this.baseHeight,  this.baseWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.5f, 0.0f);
			GLSingleton.getGL().glVertex3f(-this.baseLength/2, this.baseHeight,  this.baseWidth/2);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuilding() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glBegin(GL2.GL_QUADS);
			// Back
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f,                -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.25f);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, 0.0f,                -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.80f);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.8f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			// Right
			GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.25f);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, 0.0f,            -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f*2, 0.25f);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f*2, 0.80f);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, this.buildingHeight,  this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.80f);
			GLSingleton.getGL().glVertex3f(this.buildingLength/2, 0.0f, 			this.buildingWidth/2);
			// Front
			GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f,            this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.25f);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, 0.0f,            this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.80f);
			GLSingleton.getGL().glVertex3f( this.buildingLength/2, this.buildingHeight, this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.0f, 0.8f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight, this.buildingWidth/2);
			// Left
			GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f,            -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f*2, 0.25f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight, -this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f*2, 0.80f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, this.buildingHeight,  this.buildingWidth/2);
			GLSingleton.getGL().glTexCoord2f(0.46875f, 0.80f);
			GLSingleton.getGL().glVertex3f(-this.buildingLength/2, 0.0f, 			 this.buildingWidth/2);
		GLSingleton.getGL().glEnd();
	}
	
	private void drawBuildingRoof() throws GLSingletonNotInitializedException {
		float roofLength = this.buildingLength/this.ROOFS;
		float alpha = (float)Math.asin(this.roofHeight/roofLength);
		for (int i = 0; i < this.ROOFS; i++) {
			GLSingleton.getGL().glBegin(GL2.GL_QUADS);
				// Top
				GLSingleton.getGL().glNormal3f((float)Math.cos(180.0-alpha), (float)Math.sin(180.0-alpha), 0.0f);
				GLSingleton.getGL().glTexCoord2f(0.703125f, 0.80f);
				GLSingleton.getGL().glVertex3f(i*roofLength,     0.0f,            -this.buildingWidth/2);
				GLSingleton.getGL().glTexCoord2f(0.9375f, 0.80f);
				GLSingleton.getGL().glVertex3f(i*roofLength,     0.0f, 			  this.buildingWidth/2);
				GLSingleton.getGL().glTexCoord2f(0.9375f, 1.0f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight,  this.buildingWidth/2);
				GLSingleton.getGL().glTexCoord2f(0.703125f, 1.0f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight, -this.buildingWidth/2);
				// Wall
				GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glTexCoord2f(0.46875f, 0.80f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, 0.0f,             this.buildingWidth/2);
				GLSingleton.getGL().glTexCoord2f(0.703125f, 0.80f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, 0.0f,            -this.buildingWidth/2);
				GLSingleton.getGL().glTexCoord2f(0.703125f, 1.0f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight, -this.buildingWidth/2);
				GLSingleton.getGL().glTexCoord2f(0.46875f, 1.0f);
				GLSingleton.getGL().glVertex3f((i+1)*roofLength, this.roofHeight,  this.buildingWidth/2);
			GLSingleton.getGL().glEnd();
			GLSingleton.getGL().glBegin(GL2.GL_TRIANGLES);
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
	
	private void drawSmokestack(boolean shadow) throws GLSingletonNotInitializedException {
		// Choose a color based on shadow or real object drawing
		if (shadow)
			GLSingleton.getGL().glColor4fv(super.SHADOW_COLOR.getColorFB());
		else
			GLSingleton.getGL().glColor3f(0.3f, 0.3f, 0.3f);
		GLSingleton.getGLU().gluCylinder(this.GLUQuadric, this.smokestackRadius, this.smokestackRadius, this.smokestackHeight/5.0, 32, 32);
	}

	public int getSmokestackHeight() {
		return smokestackHeight;
	}

	public void setSmokestackHeight(int smokestackHeight) {
		this.smokestackHeight = smokestackHeight;
	}
	
	public void setNumberOfProjects (int p) {
		this.setSmokestackHeight(p);
	}
	
	public int getNumberOfProjects () {
		return this.getSmokestackHeight();
	}

	public void setGLUQuadric(GLUquadric gLUQuadric) {
		GLUQuadric = gLUQuadric;
	}

	public void setTexture(int textureName) {
		this.texture = textureName;
	}

}
