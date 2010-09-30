package dominio.conocimiento;

import javax.media.opengl.GL;

public class Tower extends Figure {
	/*
	 * El origen de coordenadas se toma en una esquina de la base (0, 0, 0)
	 * La base se encuentra en el plano ZX, será cuadrada y tendrá de lado el valor de la variable width.
	 * La altura se extiende a lo largo del eje Y y viene dada por la variable height
	 */
	private float width;
	private float depth;
	private float height;
	private float edge_width = 1.0f;

	public Tower(GL gl, float origin_x, float origin_z, float width, float depth,
			float height, Color color) {
		this.gl = gl;
		this.origin_x = origin_x;
		this.origin_z = origin_z;
		this.color = color;
		// Base rectangular
		this.width = width;
		this.depth = depth;
		this.height = height;
	}
	
	public void draw() {
		// Aplicamos el mismo color a todos los vértices de la torre
		gl.glColor4fv(color.getColorFB());
		// Configuramos la parte sólida de la torre (el relleno)
		super.enableLight();
		gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);		// Habilitamos el modo relleno
		gl.glPolygonOffset(0.0f, 0.0f);				// Configuramos el offset del polígono sin desfase
		this.drawTower();							// Dibujamos la torre
		gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);	// Deshabilitamos el modo relleno
		// Configuramos los bordes del polígono (en caso de que la llamada lo solicite)
		if (this.edge_width > 0.0f)
		{
			super.disableLight();
			gl.glLineWidth(edge_width);				// Configuramos el grosor del borde
			gl.glEnable(GL.GL_POLYGON_OFFSET_LINE);	// Habilitamos el modo línea
			gl.glPolygonOffset(-1.0f, -1.0f);		// Desfasamos un poco para no dejar huecos en blanco sin rellenar entre la línea y el polígono
			gl.glColor3f(0.0f, 0.0f, 0.0f);			// Configuramos el color NEGRO para todas las líneas
			gl.glPolygonMode(GL.GL_FRONT, GL.GL_LINE);	// Renderizamos únicamente la parte frontal de la cara por razones de rendimiento
			this.drawTower();						// Dibujamos la torre (sólo los bordes)
			gl.glDisable(GL.GL_POLYGON_OFFSET_LINE);	// Restauramos todo
			gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
			gl.glPolygonOffset(0.0f, 0.0f);
		}
	}
	
	private void drawTower () {
		/* Dibujamos las caras en sentido contrario a las agujas del reloj
		 * -Counter-ClockWise (CCW)- para especificar la cara frontal.
		 * Con gl.glFrontFace(GL.GL_CW) podríamos especificarlo al contrario
		 */
		gl.glPushMatrix();
			gl.glTranslatef(this.origin_x, 0, this.origin_z);
			gl.glBegin(GL.GL_QUADS);
				// Base	(en principio no es necesario dibujarla)
				// Frente
				gl.glNormal3f(0.0f, 0.0f, 1.0f);
				gl.glVertex3f(0, 0, depth);
				gl.glVertex3f(width, 0, depth);
				gl.glVertex3f(width, height, depth);
				gl.glVertex3f(0, height, depth);
				// Lado derecho
				gl.glNormal3f(1.0f, 0.0f, 0.0f);
				gl.glVertex3f(width, 0, depth);
				gl.glVertex3f(width, 0, 0);
				gl.glVertex3f(width, height, 0);
				gl.glVertex3f(width, height, depth);
				// Espalda
				gl.glNormal3f(0.0f, 0.0f, -1.0f);
				gl.glVertex3f(0, 0, 0);
				gl.glVertex3f(0, height, 0);
				gl.glVertex3f(width, height, 0);
				gl.glVertex3f(width, 0, 0);
				// Lado izquierdo
				gl.glNormal3f(-1.0f, 0.0f, 0.0f);
				gl.glVertex3f(0, 0, 0);
				gl.glVertex3f(0, 0, depth);
				gl.glVertex3f(0, height, depth);
				gl.glVertex3f(0, height, 0);
				// Planta (igual que la base pero con eje Z = height
				gl.glNormal3f(0.0f, 1.0f, 0.0f);
				gl.glVertex3f(0, height, 0);
				gl.glVertex3f(0, height, depth);
				gl.glVertex3f(width, height, depth);
				gl.glVertex3f(width, height, 0);
			gl.glEnd();
		gl.glPopMatrix();
	}

}
