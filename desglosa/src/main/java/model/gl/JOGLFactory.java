package model.gl;

import model.gl.knowledge.GLEdge;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLMapLocation;
import model.gl.knowledge.GLMetricIndicator;
import model.gl.knowledge.GLTower;
import model.util.Color;

public class JOGLFactory extends GLAbstractFactory {
	
	@Override
	public GLObject createTower(float pos_x, float pos_y, float width, float depth,
			float height, Color color) {
		return new GLTower (pos_x, pos_y, width, depth, height, color);
	}
	
	@Override
	public GLObject createMapLocation(int id, float pos_x, float pos_y) {
		GLObject mapLocation = new GLMapLocation(id, pos_x, pos_y);
		mapLocation.setPositionX(pos_x);
		mapLocation.setPositionZ(pos_y);
		return mapLocation;
	}
	
	@Override
	public GLObject createMetricIndicator(float pos_x, float pos_y) {
		return new GLMetricIndicator (pos_x, pos_y);
	}
	
	@Override
	public GLObject createMetricIndicator(float pos_x, float pos_y, float width, Color color) {
		return new GLMetricIndicator (pos_x, pos_y, width, color);
	}

	@Override
	public GLObject createEdge(GLMetricIndicator s, GLMetricIndicator d) {
		return new GLEdge (s, d);
	}

	@Override
	public GLObject createCaption(float pos_x, float pos_y) {
		// TODO Auto-generated method stub
		return null;
	}

}
