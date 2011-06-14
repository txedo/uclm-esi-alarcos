package model.gl;

import model.gl.knowledge.GLObject;
import model.gl.knowledge.MetricIndicator;
import model.util.Color;

public abstract class GLAbstractFactory {
	public abstract GLObject createTower (float pos_x, float pos_y, float width, float depth, float height, Color color);
	public abstract GLObject createMapLocation(int id, float pos_x, float pos_y);
	public abstract GLObject createMetricIndicator(float pos_x, float pos_y);
	public abstract GLObject createMetricIndicator(float pos_x, float pos_y, float width, Color color);
	public abstract GLObject createEdge (MetricIndicator s, MetricIndicator d);
	public abstract GLObject createCaption (float pos_x, float pos_y);
}
