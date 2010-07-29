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
		Vector3f viewPoint = position.add(viewDir);

		glu.gluLookAt(position.getX(), position.getY(), position.getZ(),
				viewPoint.getX(), viewPoint.getY(), viewPoint.getZ(),
				upVector.getX(), upVector.getY(), upVector.getZ());
	}
	
	private void move(Vector3f direccion) {
		position = position.add(direccion.mult(DESPL));
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
		Vector3f viewPoint = position.add(viewDir);
		/* Pasamos de coordenadas cartesianas a coordenadas polares
		 * r = sqrt(x^2 + y^2)
		 * teta = atan (y/x)
		 */
		double r = viewPoint.getLength();
		double teta = Math.atan(position.getZ()/position.getX());
		/* http://www.vitutor.net/2/1/22.html
		 * Longitud de un arco de una circunferencia
		 * L = (2*pi*r*alfa)/360
		 */
		//double alfa = (360*longitudArco)/(2*Math.PI*r);
		/* Las dos rotaciones posibles son (r, teta+alfa) y (r, teta-alfa)
		 */
		double rotacion = teta+angle;
		position.setX((float)(r*Math.cos(rotacion*Math.PI/180)));
		position.setZ((float)(r*Math.sin(rotacion*Math.PI/180)));

		viewDir = viewPoint.sub(position);

		// Recalculamos rightVector con el producto vectorial de viewDir y upVector
		rightVector = viewDir.cross(upVector);
		// Recalculamos frontVector con el producto vectorial de upVector y el nuevo rightVector
		frontVector = rightVector.cross(upVector).mult(-1);
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
	
}
