package model.gl.knowledge;

import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLSingleton;
import model.gl.control.GLFontBuilder;
import model.knowledge.Color;
import exceptions.gl.GLSingletonNotInitializedException;

public class AntennaBall extends GLObject {
	private int[] textures;
	
	private int subdivisions;
	private float parentBallRadius;
	private boolean progression;
	private float childBallRadius;
	private Color leftChildBallColor;
	private Color rightChildBallColor;
	private int leftChildBallValue;
	private int rightChildBallValue;
	private final float ANTENNA_WIDTH = 3.0f;
	private final float ANTENNA_ANGLE = 45.0f;

	private String label;
	
	private GLUquadric quadric;
	
	public AntennaBall (float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = new Color(0.0f, 0.0f, 1.0f, 1.0f);	// This will be used for the parent ball color

		this.subdivisions = 32;
		this.parentBallRadius = 1.0f;
		this.progression = true;
		this.childBallRadius = 0.5f;
		this.leftChildBallColor = new Color (0.0f, 1.0f, 0.0f, 0.5f);
		this.rightChildBallColor = new Color (1.0f, 0.0f, 0.0f, 0.5f);
		
		this.leftChildBallValue = 0;
		this.rightChildBallValue = 0;
	}

	public boolean isProgression() {
		return progression;
	}

	public void setProgression(boolean progression) {
		this.progression = progression;
	}

	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glLineWidth(ANTENNA_WIDTH);
		// Draw the opaque element first (child balls and antennas)
		GLSingleton.getGL().glPushMatrix();
			// Move to the parent ball center
			GLSingleton.getGL().glTranslatef(this.positionX, this.parentBallRadius, this.positionY);
			
			// Draw left antenna
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glBegin(GL.GL_LINES);
					GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
					GLSingleton.getGL().glVertex3f(-parentBallRadius, parentBallRadius*(float)Math.tan(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glEnd();
				// Draw right child ball
				GLSingleton.getGL().glTranslatef(-parentBallRadius, parentBallRadius*(float)Math.tan(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glColor4fv(this.leftChildBallColor.getColorFB());
				GLSingleton.getGLU().gluSphere(this.quadric, this.childBallRadius, this.subdivisions, this.subdivisions);
			GLSingleton.getGL().glPopMatrix();
			
			// Draw right antenna and its child ball
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glBegin(GL.GL_LINES);
					GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
					GLSingleton.getGL().glVertex3f(parentBallRadius, parentBallRadius*(float)Math.tan(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glEnd();
				// Draw left child ball
				GLSingleton.getGL().glTranslatef(parentBallRadius, parentBallRadius*(float)Math.tan(ANTENNA_ANGLE), 0.0f);
				/**************/
				// Enable texture mapping
				GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);
				GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_S);
				GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_T);
				// Draw the parent ball
				// Set Up Sphere Mapping
				GLSingleton.getGL().glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_SPHERE_MAP);
				GLSingleton.getGL().glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_SPHERE_MAP);
				try {
					GLFontBuilder.getInstance().glPrint(0, 0, "5555", 1, true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/*******************/
				GLSingleton.getGL().glColor4fv(this.rightChildBallColor.getColorFB());
				GLSingleton.getGLU().gluSphere(this.quadric, this.childBallRadius, this.subdivisions, this.subdivisions);
				/********************/
				// Disable everything we enabled before
				GLSingleton.getGL().glDisable(GL.GL_TEXTURE_GEN_S);
				GLSingleton.getGL().glDisable(GL.GL_TEXTURE_GEN_T);
				GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);
				/*******************/
			GLSingleton.getGL().glPopMatrix();
			
			// Write the label
			try {
				GLSingleton.getGL().glPushMatrix();
					GLSingleton.getGL().glTranslatef(0.0f, -parentBallRadius, parentBallRadius);
					GLFontBuilder.getInstance().glPrint(0, 0, this.label, 1, true);
				GLSingleton.getGL().glPopMatrix();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Enable texture mapping
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_S);
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_T);
			// Draw the parent ball
			// Set Up Sphere Mapping
			GLSingleton.getGL().glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_SPHERE_MAP);
			GLSingleton.getGL().glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_SPHERE_MAP);
            // Bind the APPLY(0) or CANCEL(1) texture
			int texture = this.textures[0];
			if (!this.progression) texture = this.textures[1];
			GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, texture);			
			// Now we draw the sphere
			GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGLU().gluSphere(this.quadric, this.parentBallRadius, this.subdivisions, this.subdivisions);
			
			// Disable everything we enabled before
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_GEN_S);
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_GEN_T);
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);
		GLSingleton.getGL().glPopMatrix();
	}

	public void setTextures(int[] textureNames) {
		this.textures = textureNames;
	}

	public void setQuadric(GLUquadric quadric) {
		this.quadric = quadric;
	}

	public void setParentBallRadius(float parentBallRadius) {
		this.parentBallRadius = parentBallRadius;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getLeftChildBallValue() {
		return leftChildBallValue;
	}

	public void setLeftChildBallValue(int leftChildBallValue) {
		this.leftChildBallValue = leftChildBallValue;
	}

	public int getRightChildBallValue() {
		return rightChildBallValue;
	}

	public void setRightChildBallValue(int rightChildBallValue) {
		this.rightChildBallValue = rightChildBallValue;
	}
	
}
