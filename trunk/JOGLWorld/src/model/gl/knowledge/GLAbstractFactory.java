package model.gl.knowledge;

import model.knowledge.Color;

public abstract class GLAbstractFactory {
	public abstract GLObject Tower (float pos_x, float pos_y, float width, float depth, float height, Color color);
	public abstract GLObject Node (float pos_x, float pos_y, float width, Color color);
	public abstract GLObject Edge (Node s, Node d);
	public abstract GLObject Caption (float pos_x, float pos_y);
}
