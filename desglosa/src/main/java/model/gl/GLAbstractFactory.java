package model.gl;

import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLMetricIndicator;
import model.util.Color;

public abstract class GLAbstractFactory {
	public abstract GLObject createTower (float pos_x, float pos_y, float width, float depth, float height, Color color);
	public abstract GLObject createMapLocation(int id, float pos_x, float pos_y);
	public abstract GLObject createMetricIndicator(float pos_x, float pos_y);
	public abstract GLObject createMetricIndicator(float pos_x, float pos_y, float width, Color color);
	public abstract GLObject createEdge (GLMetricIndicator s, GLMetricIndicator d);
	public abstract GLObject createCaption (float pos_x, float pos_y);
}
