package dominio.conocimiento;

import javax.media.opengl.GL;

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
	
	private GL gl;

	public Spotlight (GL gl, float r, float g, float b) {
		color = new float[4];
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = 1.0f;
		this.gl = gl;
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess);
		gl.glColorMaterial(GL.GL_FRONT, GL.GL_EMISSION) ;
		
		// Position The Light
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, light_ambient, 0);
		// Setup The Light
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, light_full, 0);
		// Render The Spotlight
		Vector3f pos = new Vector3f(position[0], position[1], position[2]);
		Vector3f dir = new Vector3f(direction[0], direction[1], direction[2]);
		this.render(pos, dir);

		// Angle of the cone light emitted by the spot : value between 0 to 180
		gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_CUTOFF, 90.0f);
		gl.glLightf(GL.GL_LIGHT1, GL.GL_SPOT_EXPONENT, 15.0f);
	       
        // Light attenuation (default values used here : no attenuation with the distance)
        gl.glLightf(GL.GL_LIGHT1, GL.GL_CONSTANT_ATTENUATION, 1.0f);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_LINEAR_ATTENUATION, 0.0f);
        gl.glLightf(GL.GL_LIGHT1, GL.GL_QUADRATIC_ATTENUATION, 0.0f);

		gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, GL.GL_TRUE);
		// Enable Lighting
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT1);										
	}


	public void render(Vector3f position, Vector3f viewDir) {
		gl.glPushMatrix();
			// Position The Light
			gl.glTranslatef(position.getX(), position.getY(), position.getZ());
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, this.position, 0);		// Position The Light
			// Setup the spot direction
			this.direction = viewDir.toArray();
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPOT_DIRECTION, this.direction, 0);
		gl.glPopMatrix();
	}


}
