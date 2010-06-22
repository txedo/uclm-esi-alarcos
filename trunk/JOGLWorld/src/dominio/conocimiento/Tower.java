package dominio.conocimiento;

import javax.media.opengl.GL;

public class Tower {
	/*
	 * El origen de coordenadas se toma en una esquina de la base (0, 0, 0)
	 * La base se encuentra en el plano ZX, será cuadrada y tendrá de lado el valor de la variable width.
	 * La altura se extiende a lo largo del eje Y y viene dada por la variable height
	 */
	private float origin_x;
	private float origin_y;
	private float width;
	private float height;
	private Color color;

	public Tower(float origin_x, float origin_y, float width, float height,
			Color color) {
		super();
		this.origin_x = origin_x;
		this.origin_y = origin_y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void draw(GL gl) {
		// Aplicamos el mismo color a todos los vértices
		gl.glColor4fv(color.getColorFB());
		gl.glBegin(GL.GL_QUADS);
			// Base
			gl.glVertex3f(origin_x, origin_y, 0);
			gl.glVertex3f(origin_x + width, origin_y, 0);
			gl.glVertex3f(origin_x + width, origin_y + width, 0);
			gl.glVertex3f(origin_x, origin_y + width, 0);
			// Frente
			gl.glVertex3f(origin_x + width, origin_y, 0);
			gl.glVertex3f(origin_x + width, origin_y + width, 0);
			gl.glVertex3f(origin_x + width, origin_y + width, height);
			gl.glVertex3f(origin_x + width, origin_y, height);
//			// Lado derecho
			gl.glVertex3f(origin_x + width, origin_y + width, 0);
			gl.glVertex3f(origin_x, origin_y + width, 0);
			gl.glVertex3f(origin_x, origin_y + width, height);
			gl.glVertex3f(origin_x + width, origin_y + width, height);
//			// Espalda
			gl.glVertex3f(origin_x, origin_y, 0);
			gl.glVertex3f(origin_x, origin_y + width, 0);
			gl.glVertex3f(origin_x, origin_y + width, height);
			gl.glVertex3f(origin_x, origin_y, height);
//			// Lado izquierdo
			gl.glVertex3f(origin_x + width, origin_y, 0);
			gl.glVertex3f(origin_x, origin_y, 0);
			gl.glVertex3f(origin_x, origin_y, height);
			gl.glVertex3f(origin_x + width, origin_y, height);
//			// Planta (igual que la base pero con eje Z = height
			gl.glVertex3f(origin_x, origin_y, height);
			gl.glVertex3f(origin_x + width, origin_y, height);
			gl.glVertex3f(origin_x + width, origin_y + width, height);
			gl.glVertex3f(origin_x, origin_y + width, height);
		gl.glEnd();
	}

}
