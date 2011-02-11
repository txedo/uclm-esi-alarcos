package model.gl.knowledge;

import model.gl.GLSingleton;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactory extends GLObject {
	private int smokestackHeight;
	
	public GLFactory (float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, 0.0f, this.positionY);
			
		GLSingleton.getGL().glPopMatrix();
	}

	public int getSmokestackHeight() {
		return smokestackHeight;
	}

	public void setSmokestackHeight(int smokestackHeight) {
		this.smokestackHeight = smokestackHeight;
	}

}
