package dominio.conocimiento.caption;

import javax.media.opengl.GL;

import dominio.conocimiento.Color;
import dominio.control.GLSingleton;
import exceptions.GLSingletonNotInitializedException;

public class Icon {
	private int width;
	private int height;	// width and height in pixels
	private Color color;
	
	public Icon(int width, int height, Color color) {
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3fv (color.getColorFB());
		GLSingleton.getGL().glBegin(GL.GL_QUADS);
			GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
			GLSingleton.getGL().glVertex2f(this.height/100.0f, 0.0f);
			GLSingleton.getGL().glVertex2f(this.height/100.0f, this.width/100.0f);
			GLSingleton.getGL().glVertex2f(0.0f, this.width/100.0f);
		GLSingleton.getGL().glEnd();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
		
}
