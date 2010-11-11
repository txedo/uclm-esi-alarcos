package dominio.conocimiento.caption;

import java.util.Vector;
import javax.media.opengl.GL;

import dominio.conocimiento.Color;
import dominio.conocimiento.GLUtils;
import dominio.conocimiento.Vector2f;
import dominio.control.GLSingleton;
import exceptions.GLSingletonNotInitializedException;

public class Caption {
	private float pos_x;
	private float pos_y;
	private Vector<Line> lines;
	
	public Caption(float pos_x, float pos_y) {
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		lines = new Vector<Line>();
	}
	
	public void addLine (Color c, String t) {
		Line l = new Line ();
		l.getIcon().setColor(c);
		l.getText().setText(t);
		lines.add(l);
	}
	
	public void draw () throws GLSingletonNotInitializedException {
		// Calculamos el tamaño de la leyenda en función del número de líneas y la longitud de la más larga
		int num_lines = lines.size();
		int max_length = 0;
		for (Line l : lines) {
			int aux = l.getText().getLength();
			if (aux > max_length) max_length = aux; 
		}
		
		float width = 0.0f;
		float weight = 0.0f;
		if (num_lines > 0) {
			width = lines.firstElement().getIcon().getWidth();
			width += lines.firstElement().getGAP() + max_length;
			// TODO SEGUIR POR AQUI
			// TODO HACER UN SINGLETON QUE TENGA EL CONTEXTO GL
		}
		Vector2f v = GLUtils.GetOGLPos2D((int)Math.ceil(width), (int)(this.pos_y - num_lines));
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.pos_x, this.pos_y, 0.0f);
			GLSingleton.getGL().glBegin(GL.GL_LINE_LOOP);
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
				GLSingleton.getGL().glVertex2f(v.getX(), 0.0f);
				GLSingleton.getGL().glVertex2f(v.getX(), -v.getY());
				GLSingleton.getGL().glVertex2f(0.0f, -v.getY());
			GLSingleton.getGL().glEnd();
			
			float offset = 0.0f;
			for (Line l : lines) {
				GLSingleton.getGL().glTranslatef(0.0f, 0.0f - offset, 0.0f);
				l.draw();
				offset += 0.20f;
			}
		GLSingleton.getGL().glPopMatrix();
	}
	
}
