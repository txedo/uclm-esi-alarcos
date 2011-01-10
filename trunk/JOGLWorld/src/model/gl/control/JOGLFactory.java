package model.gl.control;

import model.gl.GLObject;
import model.gl.knowledge.Edge;
import model.gl.knowledge.MetricIndicator;
import model.gl.knowledge.Tower;
import model.knowledge.Color;

public class JOGLFactory extends GLAbstractFactory {
	
	@Override
	public GLObject createTower(float pos_x, float pos_y, float width, float depth,
			float height, Color color) {
		return new Tower (pos_x, pos_y, width, depth, height, color);
	}
	
	@Override
	public GLObject createNode(float pos_x, float pos_y, float width, Color color) {
		return new MetricIndicator (pos_x, pos_y, width, color);
	}

	@Override
	public GLObject createEdge(MetricIndicator s, MetricIndicator d) {
		return new Edge (s, d);
	}

	@Override
	public GLObject createCaption(float pos_x, float pos_y) {
		// TODO Auto-generated method stub
		return null;
	}

}
