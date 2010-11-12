package model.knowledge;

import javax.media.opengl.GL;

import model.GLSingleton;

import exceptions.GLSingletonNotInitializedException;

public class Edge extends GLObject implements IEdge {
	private Vector2f source;
	private Vector2f destination;
	private int type;
	private float width;
	private Color color;
	
	public Edge (Node s, Node d) {
		// Conectamos el nodo origen (source)
		float x = s.origin_x + s.getWidth()/2;
		float z = s.origin_z + s.getWidth()/2;
		if (this.source == null) this.source = new Vector2f(x, z);
		else {
			this.source.setX(x);
			this.source.setY(z);
		}
		// Conectamos el nodo destino (destination)
		x = d.origin_x + d.getWidth()/2;
		z = d.origin_z + d.getWidth()/2;
		if (this.destination == null) this.destination = new Vector2f(x, z);
		else {
			this.destination.setX(x);
			this.destination.setY(z);	
		}
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
			GLSingleton.getGL().glVertex2f(this.source.getX(), this.source.getY());
			GLSingleton.getGL().glVertex2f(this.destination.getX(), this.destination.getY());
		GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glDisable(GL.GL_LINE_STIPPLE);
	}

	public Vector2f getSource() {
		return source;
	}

	public void setSource(Vector2f s) {
		this.source = s;
	}

	public Vector2f getDestination() {
		return destination;
	}

	public void setDestination(Vector2f d) {
		this.destination = d;
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
}
