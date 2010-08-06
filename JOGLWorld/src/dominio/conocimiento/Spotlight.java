package dominio.conocimiento;

import java.nio.FloatBuffer;

public class Spotlight {
	private float[] color;
	private float[] position;
	private Vector3f direction;
	private final float DIRECTIONAL = 0.0f;
	private final float POSITIONAL = 1.0f;
	
	public Spotlight (float r, float g, float b) {
		color = new float[4];
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = 1.0f;
		position = new float[4];
		position[3] = POSITIONAL;
		direction = new Vector3f();
	}
	
	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}

	public Vector3f getDirection3f() {
		return direction;
	}

	public void setDirection3f(Vector3f direction) {
		this.direction = direction;
	}
	
	public float[] getDirection () {
		return this.direction.toArray();
	}
	
	public void setDirection (float [] dir) {
		this.direction.setX(dir[0]);
		this.direction.setY(dir[1]);
		this.direction.setZ(dir[2]);
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}

	public Vector3f getPosition3f() {
		Vector3f res = new Vector3f();
		res.setX(position[0]);
		res.setY(position[1]);
		res.setZ(position[2]);
		return res;
	}

	public void setPosition3f(Vector3f position) {
		this.position[0] = position.getX();
		this.position[1] = position.getY();
		this.position[2] = position.getZ();
		this.position[3] = 1.0f;
	}

}
