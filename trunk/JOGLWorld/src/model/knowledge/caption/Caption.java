package model.knowledge.caption;

import java.util.Vector;
import javax.media.opengl.GL;

import model.GLSingleton;
import model.GLUtils;
import model.knowledge.Color;
import model.knowledge.GLObject;
import model.knowledge.Vector2f;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;

import exceptions.GLSingletonNotInitializedException;

public class Caption extends GLObject {
	private final int pxGAP = 10; // px
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
		if (num_lines > 0) {
			width = this.pxGAP * 2 + lines.firstElement().getIcon().getWidth() + lines.firstElement().getPxGAP() + max_length;
		}
		
		Vector2f v = GLUtils.GetOGLPos2D((int)Math.ceil(width), (int)Math.ceil((num_lines+1) * this.pxGAP + num_lines * lines.firstElement().getText().getHeightInPx()));
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.pos_x, this.pos_y, 0.0f);
			GLSingleton.getGL().glBegin(GL.GL_LINE_LOOP);
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);
				GLSingleton.getGL().glVertex2f(v.getX(), 0.0f);
				GLSingleton.getGL().glVertex2f(v.getX(), -v.getY());
				GLSingleton.getGL().glVertex2f(0.0f, -v.getY());
			GLSingleton.getGL().glEnd();
		GLSingleton.getGL().glPopMatrix();
		
		Vector2f oglGAP = GLUtils.GetOGLPos2D(this.pxGAP, this.pxGAP);
		GLSingleton.getGL().glPushMatrix();
			float offset = 0.0f;
			GLSingleton.getGL().glTranslatef(this.pos_x + oglGAP.getX(), this.pos_y - oglGAP.getY() * 2, 0.0f);
			for (Line l : lines) {
				GLSingleton.getGL().glTranslatef(0.0f, 0.0f - offset, 0.0f);
				l.draw();
				offset += GLUtils.GetOGLPos2D(0, 20).getY();
			}
		GLSingleton.getGL().glPopMatrix();
	}
	
}
