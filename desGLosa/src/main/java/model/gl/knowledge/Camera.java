package model.gl.knowledge;

import model.gl.GLSingleton;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

public class Camera {
	private boolean mousing;
	private Vector3f position;	// posición de la camara
	private Vector3f viewDir;	// dirección a la que apunta el objetivo de la cámara
	// Los siguientes indican direcciones relativas a la cámara
	private Vector3f frontVector;
	private Vector3f upVector;
	private Vector3f rightVector;
	private float xrot;
	private float yrot;
	private final Vector3f initialPosition;
	private final Vector3f initialViewDir;
	
	public Camera(float xpos, float ypos, float zpos, float xviewdir, float yviewdir, float zviewdir) {
		this.mousing = false;
		initialPosition = new Vector3f(xpos, ypos, zpos);
		// Inicialmente la camara apunta al origen de coordenadas (0,0,0)
		initialViewDir = new Vector3f(xviewdir, yviewdir, zviewdir);
		this.reset();
	}
	
	private void calibrate () {
		upVector = new Vector3f(0.0f, 1.0f, 0.0f);
		rightVector = viewDir.cross(upVector);
		frontVector = rightVector.cross(upVector).mult(-1);
		xrot = yrot = 0.0f;
	}
	
	public void reset () {
		position = initialPosition.clone();
		viewDir = initialViewDir.clone();
		this.calibrate();
	}

	public void render() throws GLSingletonNotInitializedException {
		Vector3f viewPoint = this.getViewPoint();

		GLSingleton.getGLU().gluLookAt(position.getX(), position.getY(), position.getZ(),
				viewPoint.getX(), viewPoint.getY(), viewPoint.getZ(),
				upVector.getX(), upVector.getY(), upVector.getZ());
	}
	
	private void move(Vector3f direccion) {
		move (direccion, 1);
	}
	
	private void move(Vector3f direccion, int multiplicator) {
		Vector3f aux = new Vector3f();
		aux = position.add(direccion.mult(IConstants.DESPL*multiplicator));
		if (aux.getY() < 0) aux.setY(0);
		position = aux.clone();
	}
	
	public void strafeLeft() {
		strafeLeft (1);
	}
	
	public void strafeLeft(int multiplicator) {
		move (rightVector.mult(-1), multiplicator);
	}
	
	public void strafeRight() {
		strafeRight (1);
	}
	
	public void strafeRight(int multiplicator) {
		move (rightVector, multiplicator);
	}
	
	public void moveForward() {
		moveForward (1);
	}

	public void moveForward(int multiplicator) {
		move (frontVector, multiplicator);
	}

	public void moveBackward() {
		moveBackward (1);
	}
	
	public void moveBackward(int multiplicator) {
		move (frontVector.mult(-1), multiplicator);
	}

	// TODO para los zooms, mantener una máquina de estados que indica si cada una
	// de las coordenadas anteriores son distintas de 0.
	public void zoomIn() {
		move (viewDir);
	}

	public void zoomOut() {
		move (viewDir.mult(-1));
	}

	private void rotateX (float angle) {
		xrot += angle; // Expresado en grados
		
		// rad = deg*PI/180
		// Rotamos viewDir siguiendo la trayectoria de upVector
		Vector3f comp1 = viewDir.mult((float)Math.cos(angle*Math.PI/180));
		Vector3f comp2 = upVector.mult((float)Math.sin(angle*Math.PI/180));
		viewDir = comp1.add(comp2).getNormalizedVector();

		// No recalculamos el upVector porque no queremos una cámara totalmente libre
	}
	
	private void rotateY (float angle) {
		yrot += angle;
		
		// Rotamos viewDir siguiendo la trayectoria de rightVector
		Vector3f comp1 = viewDir.mult((float)Math.cos(angle*Math.PI/180));
		Vector3f comp2 = rightVector.mult((float)Math.sin(angle*Math.PI/180));
		viewDir = comp1.add(comp2).getNormalizedVector();

		// Recalculamos rightVector con el producto vectorial de viewDir y upVector
		rightVector = viewDir.cross(upVector);
		// Recalculamos frontVector con el producto vectorial de upVector y el nuevo rightVector
		frontVector = rightVector.cross(upVector).mult(-1);
	}
	
	public void rotateLeft() {
		rotateLeft(1);
	}
	
	public void rotateLeft(int multiplicator) {
		rotateY(-IConstants.Y_ROTATION*multiplicator);
	}
	
	public void rotateRight() {
		rotateRight(1);
	}
	
	public void rotateRight(int multiplicator) {
		rotateY(IConstants.Y_ROTATION*multiplicator);
	}
	
	public void lookUp() {
		lookUp(1);
	}

	public void lookUp(int multiplicator) {
		rotateX(IConstants.X_ROTATION*multiplicator);
	}
	
	public void lookDown() {
		lookDown(1);
	}

	public void lookDown(int multiplicator) {
		rotateX(-IConstants.X_ROTATION*multiplicator);
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getViewDir() {
		return viewDir;
	}
	
	public Vector3f getViewPoint() {
		return position.add(viewDir);
	}

	public void lookBackward() {
		// Invertimos el sentido de los vectores directores Right y Front
		rightVector = rightVector.mult(-1);
		frontVector = frontVector.mult(-1);
		// Multiplicamos las componentes X y Z por -1 para cambiar el cuadrante
		viewDir = new Vector3f(viewDir.getX()*-1, viewDir.getY(), viewDir.getZ()*-1);
	}

	public void moveUp() {
		move (upVector);
	}
	
	public void moveDown() {
		move (upVector.mult(-1));
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setViewDir(Vector3f viewDir) {
		this.viewDir = viewDir;
		rightVector = viewDir.cross(upVector);
		frontVector = rightVector.cross(upVector).mult(-1);
	}

	public void setMousing(boolean b) {
		this.mousing = b;
	}

	public boolean isMousing() {
		return mousing;
	}
	
}
