package dominio.conocimiento;

import javax.media.opengl.glu.GLU;


public class Camara implements IConstantes {
	private Vector3f position;	// posición de la camara
	private Vector3f viewDir;	// dirección a la que apunta el objetivo de la cámara
	// Los siguientes indican direcciones relativas a la cámara
	private Vector3f frontVector;
	private Vector3f upVector;
	private Vector3f rightVector;
	private float xrot;
	private float yrot;
	
	public Camara() {
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
	
	private void mover(Vector3f direccion) {
		position = position.add(direccion.mult(DESPL));
	}
	
	public void moverIzquierda() {
		mover (rightVector.mult(-1));
	}
	
	public void moverDerecha() {
		mover (rightVector);
	}

	public void moverAdelante() {
		mover (frontVector);
	}

	public void moverAtras() {
		mover (frontVector.mult(-1));
	}

	// TODO para los zooms, mantener una máquina de estados que indica si cada una
	// de las coordenadas anteriores son distintas de 0.
	public void zoomIn() {
		mover (viewDir);
	}

	public void zoomOut() {
		mover (viewDir.mult(-1));
	}

	private void rotacionX (float angulo) {
		xrot += angulo;
		
		// Rotamos viewDir siguiendo la trayectoria de upVector
		Vector3f comp1 = viewDir.mult((float)Math.cos(angulo*Math.PI/180));
		Vector3f comp2 = upVector.mult((float)Math.sin(angulo*Math.PI/180));
		viewDir = comp1.add(comp2).getNormalizedVector();

		// No recalculamos el upVector porque no queremos una cámara totalmente libre
	}
	
	private void rotacionY (float angulo) {
		yrot += angulo;
		
		// Rotamos viewDir siguiendo la trayectoria de rightVector
		Vector3f comp1 = viewDir.mult((float)Math.cos(angulo*Math.PI/180));
		Vector3f comp2 = rightVector.mult((float)Math.sin(angulo*Math.PI/180));
		viewDir = comp1.add(comp2).getNormalizedVector();

		// Recalculamos rightVector con el producto vectorial de viewDir y upVector
		rightVector = viewDir.crossProduct(upVector);
		// Recalculamos frontVector con el producto vectorial de upVector y el nuevo rightVector
		frontVector = rightVector.crossProduct(upVector).mult(-1);
	}
	
	public void rotacionIzquierda() {
		rotacionY(-Y_ROTATION);
	}
	
	public void rotacionDerecha() {
		rotacionY(Y_ROTATION);
	}

	public void rotacionArriba() {
		rotacionX(X_ROTATION);
	}

	public void rotacionAbajo() {
		rotacionX(-X_ROTATION);
	}
	
}
