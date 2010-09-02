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

	public Tower(float origin_x, float origin_z, float width, float depth,
			float height, Color color) {
		// Base rectangular
		this.origin_x = origin_x;
		this.origin_z = origin_z;
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.color = color;
	}

	public Tower(float origin_x, float origin_z, float width, float height,
			Color color) {
		// Base cuadrada
		this.origin_x = origin_x;
		this.origin_z = origin_z;
		this.width = width;
		this.depth = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw(GL gl) {
		// Aplicamos el mismo color a todos los vértices
		gl.glColor4fv(color.getColorFB());
		gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
		gl.glBegin(GL.GL_QUADS);
			// Base	(en principio no es necesario dibujarla)
			// Frente
			gl.glNormal3f(0.0f, 0.0f, 1.0f);
			gl.glVertex3f(origin_x, 0, origin_z + depth);
			gl.glVertex3f(origin_x, height, origin_z + depth);
			gl.glVertex3f(origin_x + width, height, origin_z + depth);
			gl.glVertex3f(origin_x + width, 0, origin_z + depth);
			// Lado derecho
			gl.glNormal3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3f(origin_x + width, 0, origin_z + depth);
			gl.glVertex3f(origin_x + width, height, origin_z + depth);
			gl.glVertex3f(origin_x + width, height, origin_z);
			gl.glVertex3f(origin_x + width, 0, origin_z);
			// Espalda
			gl.glNormal3f(0.0f, 0.0f, -1.0f);
			gl.glVertex3f(origin_x, 0, origin_z);
			gl.glVertex3f(origin_x + width, 0, origin_z);
			gl.glVertex3f(origin_x + width, height, origin_z);
			gl.glVertex3f(origin_x, height, origin_z);
			// Lado izquierdo
			gl.glNormal3f(-1.0f, 0.0f, 0.0f);
			gl.glVertex3f(origin_x, 0, origin_z);
			gl.glVertex3f(origin_x, height, origin_z);
			gl.glVertex3f(origin_x, height, origin_z + depth);
			gl.glVertex3f(origin_x, 0, origin_z + depth);
			// Planta (igual que la base pero con eje Z = height
			gl.glNormal3f(0.0f, 1.0f, 0.0f);
			gl.glVertex3f(origin_x, height, origin_z);
			gl.glVertex3f(origin_x + width, height, origin_z);
			gl.glVertex3f(origin_x + width, height, origin_z + depth);
			gl.glVertex3f(origin_x, height, origin_z + depth);
		gl.glEnd();
	}

}
