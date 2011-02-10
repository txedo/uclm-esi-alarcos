package model.knowledge;

import java.nio.FloatBuffer;

public class Color {
	float r;
	float g;
	float b;
	float alpha;
	
	public Color(float red, float green, float blue) {
		this.r = red;
		this.g = green;
		this.b = blue;
		this.alpha = 0.0f;
	}
	
	public Color(float red, float green, float blue, float alpha) {
		super();
		this.r = red;
		this.g = green;
		this.b = blue;
		this.alpha = alpha;
	}
	
	
	public FloatBuffer getColorFB () {
		FloatBuffer color = FloatBuffer.allocate(4);
		color.put(0, r);
		color.put(1, g);
		color.put(2, b);
		color.put(3, alpha);
		return color;
	}

}
