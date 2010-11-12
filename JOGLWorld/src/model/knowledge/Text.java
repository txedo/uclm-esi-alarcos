package model.knowledge;


import model.GLSingleton;

import com.sun.opengl.util.GLUT;

import exceptions.GLSingletonNotInitializedException;

public class Text {
	private String text;
	private int font;
	private Color color;
	
	public Text(String text) {
		this.text = text;
		this.font = GLUT.BITMAP_HELVETICA_10;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}
	
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3fv(this.color.getColorFB());
		GLSingleton.getGL().glRasterPos2f(0.0f, 0.0f);
		GLSingleton.getGLUT().glutBitmapString(this.font, this.text);
	}
	
	public int getLength () throws GLSingletonNotInitializedException {
		return GLSingleton.getGLUT().glutBitmapLength(this.font, this.text);
	}
	
	public int getHeightInPx () throws GLSingletonNotInitializedException {
		return 10;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
