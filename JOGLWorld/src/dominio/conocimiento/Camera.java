package dominio.conocimiento;

import javax.media.opengl.glu.GLU;


public class Camera implements IConstantes {
	private Vector3f position;	// posición de la camara
	private Vector3f viewDir;	// dirección a la que apunta el objetivo de la cámara
	// Los siguientes indican direcciones relativas a la cámara
	private Vector3f frontVector;
	private Vector3f upVector;
	private Vector3f rightVector;
	private float xrot;
	private float yrot;
	
	public Camera() {
		// TODO Inicializar xpos, ypos y zpos en función de lo que haya hecho SetupWorld
		position = new Vector3f(0.0f, 4.0f, 5.0f);
		// Inicialmente la camara apunta al origen de coordenadas (0,0,0)
		viewDir = new Vector3f(0.0f, -1.0f, -1.0f);
		frontVector = new Vector3f(0.0f, 0.0f, -1.0f);
		upVector = new Vector3f(0.0f, 1.0f, 0.0f);
		rightVector = new Vector3f(1.0f, 0.0f, 0.0f);
		
		xrot = yrot = 0.0f;
	}

	public void render(GLU glu) {
		// La cámara se sitúa en (xpos, ypos, zpos)
		// El viewPoint de la cámara se indica con (xsight, ysight, zsight)
		// El vector (0, 1, 0) indica la posición de la cámara
		Vector3f viewPoint = position.add(viewDir);
		glu.gluLookAt(position.getX(), position.getY(), position.getZ(),
				viewPoint.getX(), viewPoint.getY(), viewPoint.getZ(),
				upVector.getX(), upVector.getY(), upVector.getZ());
	}
	
	private void move(Vector3f direction) {
		position = position.add(direction.mult(DESPL));
	}
	
	public void moveLeft() {
		move (rightVector.mult(-1));
	}
	
	public void moveRight() {
		move (rightVector);
	}

	public void moveForward() {
		move (frontVector);
	}

	public void moveBackward() {
		move (frontVector.mult(-1));
	}

	// TODO para los zooms, mantener una máquina de estados que indica si cada una
	// de las coordenadas anteriores son distintas de 0.
	public void zoomIn() {
		move (viewDir);
	}

	public void zoomOut() {
		move (viewDir.mult(-1));
	}
	
//	RotateX = Rotate about the X axis = Pitch
//	RotateY = Rotate about the Y axis = Yaw
//	RotateZ = Rotate about the Z axis = Roll

	private void rotateX (float angle) {
		xrot += angle;
		
		// Rotamos viewDir siguiendo la trayectoria de upVector
		Vector3f comp1 = viewDir.mult((float)Math.cos(angle*Math.PI/180));
		Vector3f comp2 = upVector.mult((float)Math.sin(angle*Math.PI/180));
		viewDir = comp1.add(comp2).getNormalizedVector();

		// No recalculamos el upVector porque no queremos una cámara totalmente libre
		//upVector = viewDir.crossProduct(rightVector).mult(-1);
	}
	
	private void rotateY (float angle) {
		yrot += angle;
		
		// Rotamos viewDir siguiendo la trayectoria de rightVector
		Vector3f comp1 = viewDir.mult((float)Math.cos(angle*Math.PI/180));
		Vector3f comp2 = rightVector.mult((float)Math.sin(angle*Math.PI/180));
		viewDir = comp1.add(comp2).getNormalizedVector();

		// Recalculamos rightVector con el producto vectorial de viewDir y upVector
		rightVector = viewDir.crossProduct(upVector);
		// Recalculamos frontVector con el producto vectorial de upVector y el nuevo rightVector
		frontVector = rightVector.crossProduct(upVector).mult(-1);
	}
	
// Hay que rotar la cámara. Nunca la escena
	public void rotateLeft() {
		rotateY(-Y_ROTATION);
	}
	
	
	public void rotateRight() {
		rotateY(Y_ROTATION);
	}

	public void rotateUp() {
		rotateX(X_ROTATION);
	}

	public void rotateDown() {
		rotateX(-X_ROTATION);
	}
	


	public float getXpos() {
		return position.getX();
	}
	
	public float getYpos() {
		return position.getY();
	}
	
	public float getZpos() {
		return position.getZ();
	}

	public float getXsight() {
		return viewDir.getX();
	}
	
	public float getYsight() {
		return viewDir.getY();
	}
	
	public float getZsight() {
		return viewDir.getZ();
	}
}
