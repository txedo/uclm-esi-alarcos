package dominio.conocimiento;
import javax.media.opengl.GL;

public abstract class Figure {
	protected float origin_x;
	protected float origin_z;
	protected Color color;
	
	public abstract void draw (GL gl, boolean wired);	
}
