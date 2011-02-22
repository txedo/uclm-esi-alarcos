package model.gl.knowledge;

import javax.media.opengl.GL;

import model.gl.GLSingleton;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

public class Spotlight {
	private final int lightSource = GL.GL_LIGHT1;
	@SuppressWarnings("unused")
	private final float DIRECTIONAL = 0.0f;
	private final float POSITIONAL = 1.0f;
	
	private float[] color;
	private final float[] position = {0.0f, 0.0f, 0.0f, POSITIONAL};
	private float direction[]  = {1.0f, 0.0f, 1.0f};
	private final float initialDirection[] = direction.clone();
	
	private final float light_ambient[] = {0.1f, 0.1f, 0.1f, 1.0f};
	private final float light_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
	private final float light_diffuse[] = {1.0f, 1.0f, 1.0f, 1.0f};
	private float mat_ambient[] = {0.0f, 0.0f, 0.0f, 1.0f};
	private float mat_specular[] = {0.8f, 0.8f, 0.8f, 1.0f};
	private float mat_diffuse[] = {0.5f, 0.5f, 0.5f, 1.0f};
	private float mat_shininess = 96.0f;

	public Spotlight (float r, float g, float b) throws GLSingletonNotInitializedException {
		color = new float[4];
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = 0.0f;
		
		if (GLSingleton.getGL() != null) {
			GLSingleton.getGL().glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient, 0);
			GLSingleton.getGL().glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
			GLSingleton.getGL().glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
			GLSingleton.getGL().glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess);
			//GLSingleton.getGL().glColorMaterial(GL.GL_FRONT, GL.GL_EMISSION);
			
			// Setup The Light
			GLSingleton.getGL().glLightfv(this.lightSource, GL.GL_AMBIENT, light_ambient, 0);
			GLSingleton.getGL().glLightfv(this.lightSource, GL.GL_SPECULAR, light_specular, 0);
			GLSingleton.getGL().glLightfv(this.lightSource, GL.GL_DIFFUSE, light_diffuse, 0);
			// Position and Render The Spotlight
			Vector3f pos = new Vector3f(position[0], position[1], position[2]);
			Vector3f dir = new Vector3f(direction[0], direction[1], direction[2]);
			this.render(pos, dir);

			// Angle of the cone light emitted by the spot : value between 0 to 180
			GLSingleton.getGL().glLightf(this.lightSource, GL.GL_SPOT_CUTOFF, 105.0f);
			GLSingleton.getGL().glLightf(this.lightSource, GL.GL_SPOT_EXPONENT, 15.0f);
		       
	        // Light attenuation (default values used here : no attenuation with the distance)
			GLSingleton.getGL().glLightf(this.lightSource, GL.GL_CONSTANT_ATTENUATION, 0.8f);
			GLSingleton.getGL().glLightf(this.lightSource, GL.GL_LINEAR_ATTENUATION, 0.005f);
			GLSingleton.getGL().glLightf(this.lightSource, GL.GL_QUADRATIC_ATTENUATION, 0.005f);

			GLSingleton.getGL().glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_TRUE);
			this.switchOn();
		} else throw new GLSingletonNotInitializedException();
						
	}

	public void render(Vector3f position, Vector3f viewDir) throws GLSingletonNotInitializedException {
		if (GLSingleton.getGL() != null) {
			GLSingleton.getGL().glPushMatrix();
				// Position The Light
				GLSingleton.getGL().glTranslatef(position.getX(), position.getY(), position.getZ());
				GLSingleton.getGL().glLightfv(this.lightSource, GL.GL_POSITION, this.position, 0);		// Position The Light
				// Setup the spot direction
				this.direction = viewDir.toArray();
				GLSingleton.getGL().glLightfv(this.lightSource, GL.GL_SPOT_DIRECTION, this.direction, 0);
			GLSingleton.getGL().glPopMatrix();
		} else throw new GLSingletonNotInitializedException();
	}

	public void switchOn () throws GLSingletonNotInitializedException {
		// Enable Lighting
		GLSingleton.getGL().glEnable(this.lightSource);	
		GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
	}
	
	public void switchOff () throws GLSingletonNotInitializedException {
		// Disable Lighting
		GLSingleton.getGL().glDisable(this.lightSource);
		GLSingleton.getGL().glDisable(GL.GL_LIGHTING);
	}

	public void reset() {
		this.direction = this.initialDirection.clone();
	}
}
