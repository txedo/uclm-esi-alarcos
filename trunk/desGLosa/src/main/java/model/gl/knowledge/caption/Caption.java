package model.gl.knowledge.caption;

import java.util.Vector;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLObject;
import model.knowledge.Color;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

public class Caption extends GLObject {
	private final int pxGAP = 10; // px
	private Vector<Line> lines;
	
	public Caption(float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
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
			int aux = l.getLengthPX();
			if (aux > max_length) max_length = aux; 
		}

		float width = 0.0f;
		if (num_lines > 0) {
			width = this.pxGAP * 2 + max_length;
		}
		
		Frame f = new Frame ((int)Math.ceil(width), (int)Math.ceil((num_lines+1) * this.pxGAP + num_lines * lines.firstElement().getHeightPX()));
		//System.out.println("frame width: " + f.getWidth() + "\nframe height: " + f.getHeight());
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
			f.draw();
		GLSingleton.getGL().glPopMatrix();
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glLoadIdentity();
			Vector3f oglGAP = GLUtils.getScreen2World(this.pxGAP, this.pxGAP, true);
			float vgap = GLUtils.getScreen2World(0, 20, true).getY();
		GLSingleton.getGL().glPopMatrix();
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX + oglGAP.getX(), this.positionY - oglGAP.getY() * 2, 0.0f);
			for (int i = 0 ; i < lines.size(); i++) {
				GLSingleton.getGL().glTranslatef(0.0f, -vgap*i, 0.0f);
				lines.get(i).draw();
			}
		GLSingleton.getGL().glPopMatrix();
	}
	
}
