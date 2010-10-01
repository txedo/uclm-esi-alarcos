package dominio.conocimiento;

import javax.media.opengl.GL;

public class Edge extends Figure implements IEdge {
	private Vector2f from;
	private Vector2f to;
	private int type;
	private float width;
	private Color color;
	
	private GL gl;
	
	public Edge (GL gl) {
		this.gl = gl;
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
			this.from.setZ(z);
		}
		// Conectamos el nodo destino (destination)
		x = d.origin_x + d.getWidth()/2;
		z = d.origin_z + d.getWidth()/2;
		if (this.to == null) this.to = new Vector2f(x, z);
		else {
			this.to.setX(x);
			this.to.setZ(z);	
		}
	}

	@Override
	public void draw() {
		gl.glColor4fv(color.getColorFB());
	if (this.type != SOLID) gl.glEnable(GL.GL_LINE_STIPPLE);
		switch (this.type) {
			case SOLID:
				gl.glLineStipple(1, (short)0xFFFF);
				break;
			case DOTTED:
				gl.glLineStipple(1, (short)0x0101);
				break;
			case DASHED:
				gl.glLineStipple(1, (short)0x00FF);
				break;
			case DOT_AND_DASH:
				gl.glLineStipple(1, (short)0x1C47);
				break;
		}
		gl.glLineWidth(this.width);
		gl.glBegin(GL.GL_LINES);	
			gl.glVertex2f(this.from.getX(), this.from.getZ());
			gl.glVertex2f(this.to.getX(), this.to.getZ());
		gl.glEnd();
		gl.glDisable(GL.GL_LINE_STIPPLE);
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

	public GL getGl() {
		return gl;
	}

	public void setGl(GL gl) {
		this.gl = gl;
	}
}
