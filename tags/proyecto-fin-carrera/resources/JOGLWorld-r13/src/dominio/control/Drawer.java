package dominio.control;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import dominio.conocimiento.Color;
import dominio.conocimiento.Tower;

public class Drawer implements GLEventListener, Constantes {
	
	private Color c1, c2;
	private Tower t1, t2;
	
	private float xrot, scenerotx;
	private float yrot, sceneroty;
	private float xpos, ypos, zpos; // Posición de la cámara
	private float xsight, ysight, zsight; // Dónde apunta la cámara
	private float xzoom_min, yzoom_min, zzoom_min;
	
	@Override
	public void display(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		
		drawScene(glDrawable);
	}

	@Override
	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged,
			boolean deviceChanged) {
	}

	@Override
	public void init(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		gl.glShadeModel(GL.GL_FLAT);
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // fondo blanco
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		// Añadir el listener de teclado
		glDrawable.addKeyListener(new MyKeyListener(this));
		// Añadir los listener de ratón
		glDrawable.addMouseListener(new MyMouseListener());
		glDrawable.addMouseMotionListener(new MyMouseMotionListener());
		
		//TODO SetupWorld();
		c1 = new Color(1.0f, 0.0f, 0.0f);
		t1 = new Tower(0.0f, 0.0f, 1.0f, 2.0f, c1);
		c2 = new Color(0.0f, 1.0f, 0.0f);
		t2 = new Tower(-1.0f, -1.0f, 1.0f, 2.0f, c2);
		
		xrot = 0.0f;
		scenerotx = 360.0f - xrot;
		yrot = 0.0f;
		sceneroty = 360.0f - yrot;
		// TODO Inicializar xpos, ypos y zpos en función de lo que haya hecho SetupWorld
		xpos = 0.0f;
		ypos = 4.0f;
		zpos = 5.0f;
		// Inicialmente la camara apunta al origen de coordenadas (0,0,0)
		xsight = 0.0f;
		ysight = 1.0f;
		zsight = 0.0f;
	}

	@Override
	public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
			int height) {
		// Qué acción realizar cuando se redimensiona la ventana
		final GL gl = glDrawable.getGL();
		final GLU glu = new GLU();
		
		gl.setSwapInterval(1);

		// lower left corner (0,0); upper right corner (width,height)
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		float h = (float) width / (float) height;
		glu.gluPerspective(60.0f, h, 0.1f, 1000.0f);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	public void drawScene(GLAutoDrawable glDrawable) {
		final GL gl = glDrawable.getGL();
		final GLU glu = new GLU();
		scenerotx = 360.0f - xrot;
		sceneroty = 360.0f - yrot;
		
		gl.glLoadIdentity();
		// La cámara se sitúa en (xpos, ypos, zpos)
		// apuntando al origen de coordenadas (0, 0, 0)
		// El vector (0, 1, 0) indica la posición de la cámara
		glu.gluLookAt(xpos, ypos, zpos, xsight, ysight, zsight, 0.0f, 1.0f, 0.0f);
		//glu.gluLookAt(0.0f, 4.0f, zpos, 0, 0, 0, 0.0f, 1.0f, 0.0f);
		//gl.glTranslatef(-xpos, -ypos, -zpos);

		gl.glRotatef(scenerotx, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(sceneroty, 0.0f, 1.0f, 0.0f);
		t1.draw(gl);
		t2.draw(gl);
		
		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.3f);
		gl.glBegin(GL.GL_QUADS);	
			gl.glVertex3f(-2, 0, -2);
			gl.glVertex3f(-2, 0, 2);
			gl.glVertex3f(2, 0, 2);
			gl.glVertex3f(2, 0, -2);
		gl.glEnd();
		
		gl.glColor4f(0.5f, 0.5f, 0.5f, 0.3f);
		gl.glLineWidth(5.0f);
		gl.glBegin(GL.GL_LINE);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(13.0f, 0.0f, 0.0f);
		gl.glEnd();
		gl.glBegin(GL.GL_LINE);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 13.0f, 0.0f);
		gl.glEnd();
		gl.glBegin(GL.GL_LINE);
			gl.glVertex3f(0.0f, 0.0f, 0.0f);
			gl.glVertex3f(0.0f, 0.0f, 13.0f);
		gl.glEnd();
		
		gl.glFlush();
	}

	public void moveLeft() {
		yrot -= Y_ROTATION;
	}
	
	public void moveRight() {
		yrot += Y_ROTATION;
	}

	public float getYrot() {
		return yrot;
	}

	public void moveUp() {
		// TODO Controlar límite para que no pueda dar vueltas
		float newXRot = xrot - X_ROTATION;
		//if (newXRot <= 180) {
			xrot -= X_ROTATION;
		//}
	}

	public void moveDown() {
		// TODO Controlar límite para que no pueda dar vueltas
		float newXRot = xrot + X_ROTATION;
		//if (newXRot >= 0) {
			xrot += X_ROTATION;
		//}
	}

	public float getXrot() {
		return xrot;
	}

	// TODO para los zooms, mantener una máquina de estados que indica si cada una
	// de las coordenadas anteriores son distintas de 0.
	public void zoomIn() {
		// TODO Auto-generated method stub
		//if (xpos > xzoom_min) xpos -= ZOOM;
		//if (ypos > yzoom_min) ypos -= ZOOM;
		if (zpos > zzoom_min) {
			zpos -= ZOOM;
			//xsight -= ZOOM;
			//zsight -= ZOOM;
		}
	}

	public void zoomOut() {
		// TODO Auto-generated method stub
		//if (xpos < ZOOM_MAX) xpos += ZOOM;
		//if (ypos < ZOOM_MAX) ypos += ZOOM;
		if (zpos < ZOOM_MAX) {
			zpos += ZOOM;
			//ysight -= ZOOM;
		}
	}

	public float getXpos() {
		return xpos;
	}

	public float getYpos() {
		return ypos;
	}

	public float getZpos() {
		return zpos;
	}

	public void lookUp() {
		// TODO Auto-generated method stub
		zsight += 0.5f;
	}

	public void lookDown() {
		// TODO Auto-generated method stub
		zsight -= 0.5f;
	}

	public float getXsight() {
		return xsight;
	}

	public float getYsight() {
		return ysight;
	}

	public float getZsight() {
		return zsight;
	}

}
