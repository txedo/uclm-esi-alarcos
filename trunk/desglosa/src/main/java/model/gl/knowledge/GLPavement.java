package model.gl.knowledge;

import javax.media.opengl.GL2;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import exceptions.GLSingletonNotInitializedException;

public class GLPavement extends GLObject3D {
	private int texture;
	private float width;
	private float depth;
	
	public GLPavement () {
		this.positionX = 0.0f;
		this.positionY = 0.0f;
		this.width = 0.0f;
		this.depth = 0.0f;
		this.texture = -1;
	}
		
	@Override
	protected void draw(boolean shadow)	throws GLSingletonNotInitializedException {
		GLUtils.enableMultisample();
		GLSingleton.getGL().glDisable(GL2.GL_LIGHTING);
		GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
		GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D, texture);
		GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
		GLSingleton.getGL().glBegin(GL2.GL_QUADS);
			GLSingleton.getGL().glColor3f(1.0f, 1.0f, 1.0f);
			GLSingleton.getGL().glTexCoord2f( 0.0f, depth);	GLSingleton.getGL().glVertex3f(this.positionX, 0.001f,  this.positionY+depth);
			GLSingleton.getGL().glTexCoord2f( 0.0f, 0.0f);	GLSingleton.getGL().glVertex3f(this.positionX, 0.001f, this.positionY);
			GLSingleton.getGL().glTexCoord2f(width, 0.0f);	GLSingleton.getGL().glVertex3f(this.positionX+width, 0.001f,  this.positionY);
			GLSingleton.getGL().glTexCoord2f(width, depth);	GLSingleton.getGL().glVertex3f(this.positionX+width, 0.001f, this.positionY+depth);
		GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_2D);
		GLSingleton.getGL().glEnable(GL2.GL_LIGHTING);
		GLUtils.disableMultisample();
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}

	public float getWidth() {
		return width;
	}

	public float getDepth() {
		return depth;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public int getTexture() {
		return texture;
	}
	
	
}
