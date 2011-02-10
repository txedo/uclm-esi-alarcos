package model.gl.knowledge;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLSingleton;
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
	private final float ANTENNA_WIDTH = 3.0f;
	private final float ANTENNA_ANGLE = 45.0f;
	
	private GLUquadric quadric;
	
	public AntennaBall (float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = new Color(0.0f, 0.0f, 1.0f, 1.0f);	// This will be used for the parent ball color

		this.subdivisions = 32;
		this.parentBallRadius = 1.0f;
		this.progression = true;
		this.childBallRadius = 0.5f;
		this.leftChildBallColor = new Color (0.0f, 1.0f, 0.0f);
		this.rightChildBallColor = new Color (1.0f, 0.0f, 0.0f);
	}

	@Override
	public void draw() throws GLSingletonNotInitializedException {	
		GLSingleton.getGL().glLineWidth(ANTENNA_WIDTH);
		// Draw the opaque element first (child balls and antennas)
		GLSingleton.getGL().glPushMatrix();
			// Move to the parent ball center
			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
			
			// Draw left antenna
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glBegin(GL.GL_LINES);
					GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
					GLSingleton.getGL().glVertex3f(parentBallRadius*2, (float)Math.sin(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glEnd();
				// Draw left child ball
				GLSingleton.getGL().glTranslatef(parentBallRadius*2, (float)Math.sin(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glColor4fv(this.leftChildBallColor.getColorFB());
				GLSingleton.getGLU().gluSphere(this.quadric, this.childBallRadius, this.subdivisions, this.subdivisions);
				GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGL().glPopMatrix();
			
			// Draw right antenna and its child ball
			GLSingleton.getGL().glPushMatrix();
				GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glBegin(GL.GL_LINES);
					GLSingleton.getGL().glVertex3f(0.0f, 0.0f, 0.0f);
					GLSingleton.getGL().glVertex3f(-parentBallRadius*2, (float)Math.sin(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glEnd();
				// Draw right child ball
				GLSingleton.getGL().glTranslatef(-parentBallRadius*2, (float)Math.sin(ANTENNA_ANGLE), 0.0f);
				GLSingleton.getGL().glColor4fv(this.rightChildBallColor.getColorFB());
				GLSingleton.getGLU().gluSphere(this.quadric, this.childBallRadius, this.subdivisions, this.subdivisions);
				GLSingleton.getGL().glColor4fv(this.color.getColorFB());
			GLSingleton.getGL().glPopMatrix();
		
			// Draw the parent ball
			GLSingleton.getGL().glEnable(GL.GL_CULL_FACE);
			// Enable texture mapping
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_S);
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_GEN_T);
			// Set Up Sphere Mapping
			GLSingleton.getGL().glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_SPHERE_MAP);
			GLSingleton.getGL().glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_SPHERE_MAP);
            // Bind the APPLY(0) or CANCEL(1) texture
			int texture = this.textures[0];
			if (!this.progression) texture = this.textures[1];
			GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, texture);
			// Disable depth mask (that is why we drawed opaque element first)
			GLSingleton.getGL().glDepthMask(false);
			// Enable Blending for gaining transparencies and image quality
			GLSingleton.getGL().glEnable(GL.GL_BLEND);
			GLSingleton.getGL().glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			// Now we draw the sphere
			GLSingleton.getGLU().gluSphere(this.quadric, this.parentBallRadius, this.subdivisions, this.subdivisions);
			// Disable everything we enabled before
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_GEN_S);
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_GEN_T);
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			GLSingleton.getGL().glDisable(GL.GL_BLEND);							// Disable Blending
			// Enable depth mask again
			GLSingleton.getGL().glDepthMask(true);
		GLSingleton.getGL().glPopMatrix();
	}

	public void setTextures(int[] textureNames) {
		this.textures = textureNames;
	}

	public void setQuadric(GLUquadric quadric) {
		this.quadric = quadric;
	}

}
