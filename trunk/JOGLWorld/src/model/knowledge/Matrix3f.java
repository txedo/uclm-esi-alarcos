package model.knowledge;

public class Matrix3f {
	
	private float x1, y1, z1;
	private float x2, y2, z2;
	private float x3, y3, z3;
	
	public Matrix3f(){}
	
	public Matrix3f (float x1, float y1, float z1,
			         float x2, float y2, float z2,
			         float x3, float y3, float z3) {
		this.x1 = x1; this.y1 = y1;	this.z1 = z1;
		this.x2 = x2; this.y2 = y2;	this.z2 = z2;
		this.x3 = x3; this.y3 = y3;	this.z3 = z3;
	}
	
	public void setIdentity() {
		this.x1 = 1.0f; this.y1 = 0.0f; this.z1 = 0.0f;
		this.x2 = 0.0f; this.y2 = 1.0f; this.z2 = 0.0f;
		this.x3 = 0.0f; this.y3 = 0.0f; this.z3 = 1.0f;
	}
	
	public void sum (Matrix3f m) {
		this.x1 += m.x1; this.y1 += m.y1; this.z1 += m.z1;
		this.x2 += m.x2; this.y2 += m.y2; this.z2 += m.z2;
		this.x3 += m.x3; this.y3 += m.y3; this.z3 += m.z3;
	}
	
	public void mult (float scalar) {
		this.x1 *= scalar; this.y1 *= scalar; this.z1 *= scalar;
		this.x2 *= scalar; this.y2 *= scalar; this.z2 *= scalar;
		this.x3 *= scalar; this.y3 *= scalar; this.z3 *= scalar;
	}

	public void mult (Matrix3f m) {
		// TODO comprobar que son matrices de iguales dimensiones y tirar excepcion
		Matrix3f aux = new Matrix3f();
		// X
		aux.x1 = this.x1 * m.x1 + this.y1 + m.x2 + this.z1 * m.x3;
		aux.x2 = this.x2 * m.x1 + this.y2 + m.x2 + this.z2 * m.x3;
		aux.x3 = this.x3 * m.x1 + this.y3 + m.x2 + this.z3 * m.x3;
		// Y
		aux.y1 = this.x1 * m.y1 + this.y1 + m.y2 + this.z1 * m.y3;
		aux.y2 = this.x2 * m.y1 + this.y2 + m.y2 + this.z2 * m.y3;
		aux.y3 = this.x3 * m.y1 + this.y3 + m.y2 + this.z3 * m.y3;
		// Z
		aux.z1 = this.x1 * m.z1 + this.y1 + m.z2 + this.z1 * m.z3;
		aux.z2 = this.x2 * m.z1 + this.y2 + m.z2 + this.z2 * m.z3;
		aux.z3 = this.x3 * m.z1 + this.y3 + m.z2 + this.z3 * m.z3;
		
		// Asignamos valores
		this.x1 = m.x1; this.y1 = m.y1;	this.z1 = m.z1;
		this.x2 = m.x2; this.y2 = m.y2;	this.z2 = m.z2;
		this.x3 = m.x3; this.y3 = m.y3;	this.z3 = m.z3;
	}
}
