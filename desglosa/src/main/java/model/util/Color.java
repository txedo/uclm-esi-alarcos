package model.util;

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
		this.alpha = 1.0f;
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

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(alpha);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (Float.floatToIntBits(alpha) != Float.floatToIntBits(other.alpha))
			return false;
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
			return false;
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
			return false;
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
			return false;
		return true;
	}

	
}
