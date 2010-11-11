package dominio.conocimiento;

import javax.media.opengl.glu.GLU;

import dominio.control.GLSingleton;
import exceptions.GLSingletonNotInitializedException;

public class Camera implements IConstantes {
	private Vector3f position;	// posici�n de la camara
	private Vector3f viewDir;	// direcci�n a la que apunta el objetivo de la c�mara
	// Los siguientes indican direcciones relativas a la c�mara
	private Vector3f frontVector;
	private Vector3f upVector;
	private Vector3f rightVector;
	private float xrot;
	private float yrot;
	
	public Camera(float xpos, float ypos, float zpos, float xviewdir, float yviewdir, float zviewdir) {
		// TODO Inicializar xpos, ypos y zpos en funci�n de lo que haya hecho SetupWorld
		position = new Vector3f(xpos, ypos, zpos);
		// Inicialmente la camara apunta al origen de coordenadas (0,0,0)
		viewDir = new Vector3f(xviewdir, yviewdir, zviewdir);
		upVector = new Vector3f(0.0f, 1.0f, 0.0f);
		rightVector = viewDir.cross(upVector);
		frontVector = rightVector.cross(upVector).mult(-1);
		xrot = yrot = 0.0f;
	}

	public void render() throws GLSingletonNotInitializedException {
		Vector3f viewPoint = this.getViewPoint();

		GLSingleton.getGLU().gluLookAt(position.getX(), position.getY(), position.getZ(),
				viewPoint.getX(), viewPoint.getY(), viewPoint.getZ(),
				upVector.getX(), upVector.getY(), upVector.getZ());
	}
	
	private void move(Vector3f direccion) {
		Vector3f aux = new Vector3f();
		aux = position.add(direccion.mult(DESPL));
		if (aux.getY() < 0) aux.setY(0);
		position = aux;
	}
	
	public void strafeLeft() {
		move (rightVector.mult(-1));
	}
	
	public void strafeRight() {
		move (rightVector);
	}

	public void moveForward() {
		move (frontVector);
	}

	public void moveBackward() {
		move (frontVector.mult(-1));
	}

	// TODO para los zooms, mantener una m�quina de estados que indica si cada una
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

		// No recalculamos el upVector porque no queremos una c�mara totalmente libre
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
		rotateY(-Y_ROTATION);
	}
	
	public void rotateRight() {
		rotateY(Y_ROTATION);
	}

	public void lookUp() {
		rotateX(X_ROTATION);
	}

	public void lookDown() {
		rotateX(-X_ROTATION);
	}
	
	private void rotateAroundX() {

	}
	
	private void rotateAroundY(float angle) {

	}

	public void rotateLeftAround() {
		rotateAroundY(-Y_ROTATION);
	}

	public void rotateRightAround() {
		rotateAroundY(Y_ROTATION);
	}

	public void rotateUpAround() {
		// TODO Auto-generated method stub
		
	}

	public void rotateDownAround() {
		// TODO Auto-generated method stub
		
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
	
}
