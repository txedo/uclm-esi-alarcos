package model.gl.knowledge;

import javax.media.opengl.GL2;

import model.gl.GLSingleton;
import model.util.Color;

import exceptions.GLSingletonNotInitializedException;

public class GLEdge extends GLObject implements IGLEdge {
	private GLMetricIndicator source;
	private GLMetricIndicator destination;
	private int type;
	private float width;
	
	public GLEdge (GLMetricIndicator s, GLMetricIndicator d) {
		this.source = s;
		this.destination = d;
		this.type = SOLID;
		this.width = 1.0f;
		this.color = new Color(0.0f, 0.0f, 0.0f);
	}

	@Override
	public void draw() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glColor3fv(color.getColorFB());
		if (this.type != SOLID) GLSingleton.getGL().glEnable(GL2.GL_LINE_STIPPLE);
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
		GLSingleton.getGL().glBegin(GL2.GL_LINES);	
			GLSingleton.getGL().glVertex2f(this.source.getGravityPoint().getX(), this.source.getGravityPoint().getY());
			GLSingleton.getGL().glVertex2f(this.destination.getGravityPoint().getX(), this.destination.getGravityPoint().getY());
		GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glDisable(GL2.GL_LINE_STIPPLE);
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

	public GLMetricIndicator getSource() {
		return source;
	}

	public void setSource(GLMetricIndicator source) {
		this.source = source;
	}

	public GLMetricIndicator getDestination() {
		return destination;
	}

	public void setDestination(GLMetricIndicator destination) {
		this.destination = destination;
	}
}
