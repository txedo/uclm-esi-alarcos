package dominio.conocimiento;

public class Vector3f {
	private float x;
	private float y;
	private float z;
	
	public Vector3f() {}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getLength () {
		return (float) Math.sqrt(x*x+y*y+z*z);
	}
	
	public Vector3f getNormalizedVector () {
		float length = this.getLength();
		Vector3f res;
		if (length == 0) res = new Vector3f(0.0f,0.0f,0.0f);
		else {
			res = new Vector3f();
			res.x = this.x/length;
			res.y = this.y/length;
			res.z = this.z/length;
		}
		
		return res;
	}
	
	public Vector3f add (Vector3f v) {
		Vector3f res = new Vector3f();
		res.x = this.x + v.x;
		res.y = this.y + v.y;
		res.z = this.z + v.z;
		return res;
	}
	
	public Vector3f sub (Vector3f v) {
		Vector3f res = new Vector3f();
		res.x = this.x - v.x;
		res.y = this.y - v.y;
		res.z = this.z - v.z;
		return res;
	}
	
	public Vector3f mult (float r) {
		Vector3f res = new Vector3f();
		res.x = this.x * r;
		res.y = this.y * r;
		res.z = this.z * r;
		return res;
	}
	
	public float dotProduct (Vector3f v) {
		return this.x*v.x + this.y*v.y + this.z+v.z;		
	}
	
	public Vector3f crossProduct (Vector3f v) {
		Vector3f res = new Vector3f();
		res.x = this.y * v.z - this.z * v.y;
		res.y = this.z * v.x - this.x * v.z;
		res.z = this.x * v.y - this.y * v.x;
		return res;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getZ() {
		return z;
	}
	public void setZ(float z) {
		this.z = z;
	}
	
	
}
