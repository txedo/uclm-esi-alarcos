package model.gl.knowledge;

import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLSingleton;
import model.gl.GLUtils;
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
		this.color = new Color(0.0f, 0.0f, 1.0f);	// This will be used for the parent ball color

		this.subdivisions = 32;
		this.parentBallRadius = 1.0f;
		this.progression = true;
		this.childBallRadius = 0.5f;
		this.leftChildBallColor = new Color (0.0f, 1.0f, 0.0f);
		this.rightChildBallColor = new Color (1.0f, 0.0f, 0.0f);
		
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
		GLSingleton.getGL().glPushMatrix();
			// Move to the parent ball center
			GLSingleton.getGL().glTranslatef(this.positionX, this.parentBallRadius, this.positionY);
			
			// Draw the child balls
			this.drawChildBall(true, this.leftChildBallColor, this.leftChildBallValue);
			this.drawChildBall(false, this.rightChildBallColor, this.rightChildBallValue);
			
			// Write the project label
			try {
				GLSingleton.getGL().glPushMatrix();
					GLSingleton.getGL().glTranslatef(0.0f, -parentBallRadius, parentBallRadius);
					GLFontBuilder.getInstance().glPrint(0, 0, this.label, 1, true);
				GLSingleton.getGL().glPopMatrix();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Drawing the parent ball
			// Enable texture mapping
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_S);
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_T);
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
	
	private void drawChildBall (boolean left, Color color, int value) throws GLSingletonNotInitializedException {
		int x = 1;
		if (left) x = -1;
		GLSingleton.getGL().glLineWidth(ANTENNA_WIDTH);
		// Draw the antenna
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
			GLSingleton.getGL().glBegin(GL.GL_LINES);
				GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(x*parentBallRadius, parentBallRadius*(float)Math.tan(ANTENNA_ANGLE), 0.0f);
			GLSingleton.getGL().glEnd();
			// Draw the child ball
			GLSingleton.getGL().glTranslatef(x*parentBallRadius, parentBallRadius*(float)Math.tan(ANTENNA_ANGLE), 0.0f);
			GLSingleton.getGL().glColor4fv(color.getColorFB());
			GLSingleton.getGLU().gluSphere(this.quadric, this.childBallRadius, this.subdivisions, this.subdivisions);
			// Draw its value
			GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
			GLUtils.renderBitmapString(0, this.childBallRadius*1.10f, 0, 2, ""+value);
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
