package model.gl.knowledge;

import model.util.Color;
import exceptions.GLSingletonNotInitializedException;

public abstract class GLObject3D extends GLObject {
	protected final Color SHADOW_COLOR = new Color(0.0f, 0.0f, 0.0f, 0.4f);
	
	public GLObject3D () {
		super();
	}
	
	public GLObject3D (float posx, float posy) {
		super(posx, posy);
	}
	
	@Override
	public void draw() throws GLSingletonNotInitializedException {
		this.draw(false);
	}
	
	public void drawShadow() throws GLSingletonNotInitializedException {
		this.draw(true);
	}
	
	protected abstract void draw(boolean shadow) throws GLSingletonNotInitializedException;

}
