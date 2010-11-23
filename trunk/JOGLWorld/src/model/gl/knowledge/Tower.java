package model.gl.knowledge;

import javax.media.opengl.GL;

import model.gl.GLObject;
import model.gl.GLSingleton;
import model.knowledge.Color;

import exceptions.gl.GLSingletonNotInitializedException;

public class Tower extends GLObject {
	/*
	 * El origen de coordenadas se toma en una esquina de la base (0, 0, 0)
	 * La base se encuentra en el plano ZX, ser� cuadrada y tendr� de lado el valor de la variable width.
	 * La altura se extiende a lo largo del eje Y y viene dada por la variable height
	 */
	private float width;
	private float depth;
	private float height;
	private float edge_width;

	public Tower(float pos_x, float pos_y, float width, float depth,
			float height, Color color) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = color;
		// Base rectangular
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.edge_width = 1.0f;
	}
	
	public void draw() throws GLSingletonNotInitializedException {
		// Aplicamos el mismo color a todos los v�rtices de la torre
		GLSingleton.getGL().glColor4fv(color.getColorFB());
		// Configuramos la parte s�lida de la torre (el relleno)
		super.enableLight();
		GLSingleton.getGL().glEnable(GL.GL_POLYGON_OFFSET_FILL);		// Habilitamos el modo relleno
		GLSingleton.getGL().glPolygonOffset(0.0f, 0.0f);				// Configuramos el offset del pol�gono sin desfase
		this.drawTower();							// Dibujamos la torre
		GLSingleton.getGL().glDisable(GL.GL_POLYGON_OFFSET_FILL);	// Deshabilitamos el modo relleno
		// Configuramos los bordes del pol�gono (en caso de que la llamada lo solicite)
		if (this.edge_width > 0.0f)
		{
			// TODO para pintar torres sin aristas, pintaremos las aristas del mismo color que las torres y as� obtendremos antialiasing por las l�neas
			super.disableLight();
			GLSingleton.getGL().glLineWidth(edge_width);				// Configuramos el grosor del borde
			GLSingleton.getGL().glEnable(GL.GL_POLYGON_OFFSET_LINE);	// Habilitamos el modo l�nea
			GLSingleton.getGL().glPolygonOffset(-1.0f, -1.0f);		// Desfasamos un poco para no dejar huecos en blanco sin rellenar entre la l�nea y el pol�gono
			GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);			// Configuramos el color NEGRO para todas las l�neas
			GLSingleton.getGL().glPolygonMode(GL.GL_FRONT, GL.GL_LINE);	// Renderizamos �nicamente la parte frontal de la cara por razones de rendimiento
			this.drawTower();						// Dibujamos la torre (s�lo los bordes)
			GLSingleton.getGL().glDisable(GL.GL_POLYGON_OFFSET_LINE);	// Restauramos todo
			GLSingleton.getGL().glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
			GLSingleton.getGL().glPolygonOffset(0.0f, 0.0f);
		}
	}
	
	private void drawTower () throws GLSingletonNotInitializedException {
		/* Dibujamos las caras en sentido contrario a las agujas del reloj
		 * -Counter-ClockWise (CCW)- para especificar la cara frontal.
		 * Con gl.glFrontFace(GL.GL_CW) podr�amos especificarlo al contrario
		 */
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, 0, this.positionY);
			GLSingleton.getGL().glBegin(GL.GL_QUADS);
				// Base	(en principio no es necesario dibujarla)
				// Frente
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
				GLSingleton.getGL().glVertex3f(0, 0, depth);
				GLSingleton.getGL().glVertex3f(width, 0, depth);
				GLSingleton.getGL().glVertex3f(width, height, depth);
				GLSingleton.getGL().glVertex3f(0, height, depth);
				// Lado derecho
				GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(width, 0, depth);
				GLSingleton.getGL().glVertex3f(width, 0, 0);
				GLSingleton.getGL().glVertex3f(width, height, 0);
				GLSingleton.getGL().glVertex3f(width, height, depth);
				// Espalda
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
				GLSingleton.getGL().glVertex3f(0, 0, 0);
				GLSingleton.getGL().glVertex3f(0, height, 0);
				GLSingleton.getGL().glVertex3f(width, height, 0);
				GLSingleton.getGL().glVertex3f(width, 0, 0);
				// Lado izquierdo
				GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(0, 0, 0);
				GLSingleton.getGL().glVertex3f(0, 0, depth);
				GLSingleton.getGL().glVertex3f(0, height, depth);
				GLSingleton.getGL().glVertex3f(0, height, 0);
				// Planta (igual que la base pero con eje Z = height
				GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(0, height, 0);
				GLSingleton.getGL().glVertex3f(0, height, depth);
				GLSingleton.getGL().glVertex3f(width, height, depth);
				GLSingleton.getGL().glVertex3f(width, height, 0);
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
	}

}
