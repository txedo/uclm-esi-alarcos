package model.gl.knowledge;

import model.knowledge.Color;

public class GLObjectFactory extends GLAbstractFactory {
	
	@Override
	public GLObject Tower(float pos_x, float pos_y, float width, float depth,
			float height, Color color) {
		return new Tower (pos_x, pos_y, width, depth, height, color);
	}
	
	@Override
	public GLObject Node(float pos_x, float pos_y, float width, Color color) {
		return new Node (pos_x, pos_y, width, color);
	}

	@Override
	public GLObject Edge(Node s, Node d) {
		return new Edge (s, d);
	}

	@Override
	public GLObject Caption(float pos_x, float pos_y) {
		// TODO Auto-generated method stub
		return null;
	}

}
