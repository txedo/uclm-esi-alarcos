package model.gl.knowledge;

import javax.media.opengl.GL;

import model.gl.GLObject;
import model.gl.GLSingleton;
import model.knowledge.Color;

import exceptions.gl.GLSingletonNotInitializedException;

public class Edge extends GLObject implements IEdge {
	private Node source;
	private Node destination;
	private int type;
	private float width;
	
	public Edge (Node s, Node d) {
		this.source = s;
		this.destination = d;
		this.type = SOLID;
		this.width = 1.0f;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}

	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor4fv(color.getColorFB());
		if (this.type != SOLID) GLSingleton.getGL().glEnable(GL.GL_LINE_STIPPLE);
		switch (this.type) {
			case SOLID:
				GLSingleton.getGL().glLineStipple(1, (short)0xFFFF);
				break;
			case DOTTED:
				GLSingleton.getGL().glLineStipple(1, (short)0x0101);
				break;
			case DASHED:
				GLSingleton.getGL().glLineStipple(1, (short)0x00FF);
				break;
			case DOT_AND_DASH:
				GLSingleton.getGL().glLineStipple(1, (short)0x1C47);
				break;
		}
		GLSingleton.getGL().glLineWidth(this.width);
		GLSingleton.getGL().glBegin(GL.GL_LINES);	
			GLSingleton.getGL().glVertex2f(this.source.getCenterOfArea().getX(), this.source.getCenterOfArea().getY());
			GLSingleton.getGL().glVertex2f(this.destination.getCenterOfArea().getX(), this.destination.getCenterOfArea().getY());
		GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glDisable(GL.GL_LINE_STIPPLE);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}
}
