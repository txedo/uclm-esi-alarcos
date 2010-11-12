package model.knowledge;

import javax.media.opengl.GL;

import model.control.GLSingleton;

import exceptions.GLSingletonNotInitializedException;

public class Edge extends GLObject implements IEdge {
	private Vector2f from;
	private Vector2f to;
	private int type;
	private float width;
	private Color color;
	
	public Edge () {
		this.type = SOLID;
		this.width = 1.0f;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}
	
	public void connectNodes (Node s, Node d) {
		// Conectamos el nodo origen (source)
		float x = s.origin_x + s.getWidth()/2;
		float z = s.origin_z + s.getWidth()/2;
		if (this.from == null) this.from = new Vector2f(x, z);
		else {
			this.from.setX(x);
			this.from.setY(z);
		}
		// Conectamos el nodo destino (destination)
		x = d.origin_x + d.getWidth()/2;
		z = d.origin_z + d.getWidth()/2;
		if (this.to == null) this.to = new Vector2f(x, z);
		else {
			this.to.setX(x);
			this.to.setY(z);	
		}
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
			GLSingleton.getGL().glVertex2f(this.from.getX(), this.from.getY());
			GLSingleton.getGL().glVertex2f(this.to.getX(), this.to.getY());
		GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glDisable(GL.GL_LINE_STIPPLE);
	}

	public Vector2f getFrom() {
		return from;
	}

	public void setFrom(Vector2f from) {
		this.from = from;
	}

	public Vector2f getTo() {
		return to;
	}

	public void setTo(Vector2f to) {
		this.to = to;
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
