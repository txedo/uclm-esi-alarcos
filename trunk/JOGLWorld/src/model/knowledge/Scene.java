package model.knowledge;

public class Scene implements IConstantes {
	private float xrot;
	private float yrot;
	
	public Scene () {
		xrot = 0.0f;
		yrot = 0.0f;
	}
	
	public float getXRotation () {
		return 360.0f - xrot;
	}
	
	public float getYRotation () {
		return 360.0f - yrot;
	}
	
	private void rotateX (float rot) {
		xrot += rot;
	}
	
	public void rotateUp () {
		this.rotateX(X_ROTATION);
	}
	
	public void rotateDown () {
		this.rotateX(-X_ROTATION);
	}
	
	private void rotateY (float rot) {
		yrot += rot;
	}
	
	public void rotateRight () {
		this.rotateY(-Y_ROTATION);
	}
	
	public void rotateLeft () {
		this.rotateY(Y_ROTATION);
	}
	
}
