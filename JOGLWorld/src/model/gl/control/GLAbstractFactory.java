package model.gl.control;

import model.gl.GLObject;
import model.gl.knowledge.Node;
import model.knowledge.Color;

public abstract class GLAbstractFactory {
	public abstract GLObject createTower (float pos_x, float pos_y, float width, float depth, float height, Color color);
	public abstract GLObject createNode (float pos_x, float pos_y, float width, Color color);
	public abstract GLObject createEdge (Node s, Node d);
	public abstract GLObject createCaption (float pos_x, float pos_y);
}
