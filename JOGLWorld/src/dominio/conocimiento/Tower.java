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
	
	public void draw(GL gl, boolean wired) {
		// Aplicamos el mismo color a todos los vértices
		gl.glColor4fv(color.getColorFB());
		// Relleno
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_POLYGON_OFFSET_FILL);
		gl.glPolygonOffset(0.0f, 0.0f);
		this.drawTower(gl);
		gl.glDisable(GL.GL_POLYGON_OFFSET_FILL);
		// Bordes
		if (wired)
		{
			gl.glDisable(GL.GL_LIGHTING);
			gl.glDisable(GL.GL_LIGHT1);
			gl.glLineWidth(edge_width);
			gl.glEnable(GL.GL_POLYGON_OFFSET_LINE);
			gl.glPolygonOffset(-1.0f, -1.0f);
			gl.glColor3f(0.0f, 0.0f, 0.0f);
			gl.glPolygonMode(GL.GL_FRONT, GL.GL_LINE);
			this.drawTower(gl);
			gl.glDisable(GL.GL_POLYGON_OFFSET_LINE);
			gl.glPolygonMode(GL.GL_FRONT, GL.GL_FILL);
			gl.glPolygonOffset(0.0f, 0.0f);
		}
	}
	
	private void drawTower (GL gl) {
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
