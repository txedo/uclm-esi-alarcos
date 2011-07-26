package model.gl.knowledge;

import javax.media.opengl.GL2;

import util.GLDimension;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.util.Color;

import exceptions.GLSingletonNotInitializedException;

public class GLTower extends GLObject3D {
	public static final float MAX_WIDTH = 3.0f;
	public static final float MAX_HEIGHT = 12.0f;
	public static final float MAX_DEPTH = 3.0f;
	/*
	 * El origen de coordenadas se toma en una esquina de la base (0, 0, 0)
	 * La base se encuentra en el plano ZX, será cuadrada y tendrá de lado el valor de la variable width.
	 * La altura se extiende a lo largo del eje Y y viene dada por la variable height
	 */
	@GLDimension(name="width",type="float")
	private float width;
	@GLDimension(name="depth",type="float")
	private float depth;
	@GLDimension(name="height",type="float")
	private float height;
	@GLDimension(name="innerHeight",type="float")
	private float innerHeight;
	private float edgeWidth;
	private final float ALPHA = 0.25f;
	
	public GLTower () {
		this(0.0f, 0.0f, MAX_WIDTH, MAX_DEPTH, MAX_HEIGHT, new Color(0.0f, 0.0f, 0.0f));
	}

	public GLTower(float pos_x, float pos_y, float width, float depth,
			float height, Color color) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.color = color;
		// Base rectangular
		this.width = width;
		this.depth = depth;
		this.height = height;
		this.innerHeight = this.height;
		this.edgeWidth = 1.0f;
		
		this.maxWidth = GLTower.MAX_WIDTH;
		this.maxDepth = GLTower.MAX_DEPTH;
	}
	
	@Override
	protected void draw(boolean shadow) throws GLSingletonNotInitializedException {
		if (!shadow) {
			// Pintamos las aristas de la torre
			// Si se especifica el grosor de la arista, la pintaremos de negro
			// Si no se especifica el grosor de la arista, la pintaremos del mismo color de la torre para tener antialiasing
			if (this.edgeWidth > 0.0f)
				GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);			// Configuramos el color NEGRO para todas las líneas
			// Configuración y pintado de aristas
			GLSingleton.getGL().glDisable(GL2.GL_POLYGON_OFFSET_FILL);	// Deshabilitamos el modo relleno
			super.disableLight();
			GLSingleton.getGL().glLineWidth(edgeWidth > 0.0f? edgeWidth : 1.0f);	// Configuramos el grosor de la arista
			GLSingleton.getGL().glEnable(GL2.GL_POLYGON_OFFSET_LINE);	// Habilitamos el modo línea
			GLSingleton.getGL().glPolygonOffset(-1.0f, -1.0f);		// Desfasamos un poco para no dejar huecos en blanco sin rellenar entre la línea y el polígono
			GLSingleton.getGL().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);	// Renderizamos únicamente la parte frontal de la cara por razones de rendimiento
			this.drawTower(0.0f, this.innerHeight, true);						// Dibujamos la torre (sólo los bordes)
			GLSingleton.getGL().glDisable(GL2.GL_POLYGON_OFFSET_LINE);	// Restauramos todo
			GLSingleton.getGL().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			GLSingleton.getGL().glPolygonOffset(0.0f, 0.0f);// Configuramos el offset del polígono sin desfase
			GLSingleton.getGL().glEnable(GL2.GL_POLYGON_OFFSET_FILL);		// Habilitamos el modo relleno
			super.enableLight();
			GLSingleton.getGL().glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			// Aplicamos el mismo color a todos los vértices de la torre
			GLSingleton.getGL().glColor4fv(color.getColorFB());
			// Dibujamos la torre con relleno with multisample enabled to get polygons antialiased
			GLUtils.enableMultisample();
				this.drawTower(0.0f, this.height, false);
				float oldAlpha = color.getAlpha();
				this.color.setAlpha(this.ALPHA);
				GLSingleton.getGL().glColor4fv(color.getColorFB());
				this.drawTower(this.height, this.innerHeight, false);
				// Restore old values
				this.color.setAlpha(oldAlpha);
				GLSingleton.getGL().glColor4fv(color.getColorFB());
			GLUtils.disableMultisample();
		} else {
			GLSingleton.getGL().glColor4fv(super.SHADOW_COLOR.getColorFB());
			GLUtils.enableMultisample();
				this.drawTower(0.0f, this.innerHeight, false);
			GLUtils.disableMultisample();
		}

	}
	
	private void drawTower (float base, float height, boolean drawMaximumBase) throws GLSingletonNotInitializedException {
		/* Dibujamos las caras en sentido contrario a las agujas del reloj
		 * -Counter-ClockWise (CCW)- para especificar la cara frontal.
		 * Con gl.glFrontFace(GL2.GL_CW) podríamos especificarlo al contrario
		 */
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, 0, this.positionY);
			GLSingleton.getGL().glBegin(GL2.GL_QUADS);
				// Frente
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
				GLSingleton.getGL().glVertex3f(-width/2, base,   depth/2);
				GLSingleton.getGL().glVertex3f( width/2, base,   depth/2);
				GLSingleton.getGL().glVertex3f( width/2, height, depth/2);
				GLSingleton.getGL().glVertex3f(-width/2, height, depth/2);
				// Lado derecho
				GLSingleton.getGL().glNormal3f(1.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(width/2, base,    depth/2);
				GLSingleton.getGL().glVertex3f(width/2, base,   -depth/2);
				GLSingleton.getGL().glVertex3f(width/2, height, -depth/2);
				GLSingleton.getGL().glVertex3f(width/2, height,  depth/2);
				// Espalda
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, -1.0f);
				GLSingleton.getGL().glVertex3f(-width/2, base,   -depth/2);
				GLSingleton.getGL().glVertex3f(-width/2, height, -depth/2);
				GLSingleton.getGL().glVertex3f( width/2, height, -depth/2);
				GLSingleton.getGL().glVertex3f( width/2, base,   -depth/2);
				// Lado izquierdo
				GLSingleton.getGL().glNormal3f(-1.0f, 0.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(-width/2, base,   -depth/2);
				GLSingleton.getGL().glVertex3f(-width/2, base,    depth/2);
				GLSingleton.getGL().glVertex3f(-width/2, height,  depth/2);
				GLSingleton.getGL().glVertex3f(-width/2, height, -depth/2);
				// Planta (igual que la base pero con eje Z = height
				GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
				GLSingleton.getGL().glVertex3f(-width/2, height, -depth/2);
				GLSingleton.getGL().glVertex3f(-width/2, height,  depth/2);
				GLSingleton.getGL().glVertex3f( width/2, height,  depth/2);
				GLSingleton.getGL().glVertex3f( width/2, height, -depth/2);
				// Base	(en principio no es necesario dibujarla)
				if (drawMaximumBase) {
					GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
					GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
					GLSingleton.getGL().glVertex3f(-MAX_WIDTH/2, 0.0f, -MAX_DEPTH/2);
					GLSingleton.getGL().glVertex3f(-MAX_WIDTH/2, 0.0f,  MAX_DEPTH/2);
					GLSingleton.getGL().glVertex3f( MAX_WIDTH/2, 0.0f,  MAX_DEPTH/2);
					GLSingleton.getGL().glVertex3f( MAX_WIDTH/2, 0.0f, -MAX_DEPTH/2);
				}
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
	}

	public float getWidth() {
		return width;
	}

	public float getDepth() {
		return depth;
	}

	public float getHeight() {
		return height;
	}

	public float getInnerHeight() {
		return innerHeight;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setInnerHeight(float innerHeight) {
		this.innerHeight = innerHeight;
	}
	
}
