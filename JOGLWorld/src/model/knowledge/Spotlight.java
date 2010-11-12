package model.knowledge;

import javax.media.opengl.GL;

import model.control.GLSingleton;

import exceptions.GLSingletonNotInitializedException;

public class Spotlight {
	private final float DIRECTIONAL = 0.0f;
	private final float POSITIONAL = 1.0f;
	
	private float[] color;
	private final float[] position = {0.0f, 0.0f, 0.0f, POSITIONAL};
	private float direction[]  = {1.0f, 0.0f, 1.0f};
	
	private final float light_ambient[] = {0.1f, 0.1f, 0.1f, 1.0f};
	private final float light_full[] = {0.5f, 0.5f, 0.5f, 1.0f};
	private float mat_specular[] = {0.8f, 0.8f, 0.8f, 1.0f};
	private float mat_shininess = 80.0f;

	public Spotlight (float r, float g, float b) throws GLSingletonNotInitializedException {
		color = new float[4];
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = 1.0f;
		
		if (GLSingleton.getGL() != null) {
			GLSingleton.getGL().glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
			GLSingleton.getGL().glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess);
			GLSingleton.getGL().glColorMaterial(GL.GL_FRONT, GL.GL_EMISSION) ;
			
			// Position The Light
			GLSingleton.getGL().glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, light_ambient, 0);
			// Setup The Light
			GLSingleton.getGL().glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, light_full, 0);
			// Render The Spotlight
			Vector3f pos = new Vector3f(position[0], position[1], position[2]);
			Vector3f dir = new Vector3f(direction[0], direction[1], direction[2]);
			this.render(pos, dir);

			// Angle of the cone light emitted by the spot : value between 0 to 180
			GLSingleton.getGL().glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 120.0f);
			GLSingleton.getGL().glLightf(GL.GL_LIGHT1, GL.GL_SPOT_EXPONENT, 15.0f);
		       
	        // Light attenuation (default values used here : no attenuation with the distance)
			GLSingleton.getGL().glLightf(GL.GL_LIGHT1, GL.GL_CONSTANT_ATTENUATION, 1.0f);
			GLSingleton.getGL().glLightf(GL.GL_LIGHT1, GL.GL_LINEAR_ATTENUATION, 0.0f);
			GLSingleton.getGL().glLightf(GL.GL_LIGHT1, GL.GL_QUADRATIC_ATTENUATION, 0.0f);

			GLSingleton.getGL().glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_TRUE);
			// Enable Lighting
			GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
			GLSingleton.getGL().glEnable(GL.GL_LIGHT1);				
		} else throw new GLSingletonNotInitializedException();
						
	}


	public void render(Vector3f position, Vector3f viewDir) throws GLSingletonNotInitializedException {
		if (GLSingleton.getGL() != null) {
			GLSingleton.getGL().glPushMatrix();
				// Position The Light
				GLSingleton.getGL().glTranslatef(position.getX(), position.getY(), position.getZ());
				GLSingleton.getGL().glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, this.position, 0);		// Position The Light
				// Setup the spot direction
				this.direction = viewDir.toArray();
				GLSingleton.getGL().glLightfv(GL.GL_LIGHT1, GL.GL_SPOT_DIRECTION, this.direction, 0);
			GLSingleton.getGL().glPopMatrix();
		} else throw new GLSingletonNotInitializedException();
	}


}
