package model.gl.knowledge.caption;

import java.util.List;
import java.util.Vector;

import javax.media.opengl.GL2;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLObject;
import model.util.Color;
import model.util.Vector3f;

import exceptions.GLSingletonNotInitializedException;

public class Caption extends GLObject {
	
	private final int pxGAP = 10; // px
	private Vector<Line> lines;
	private Frame frame;
	private float width;
	private float height;
	
	public Caption () {
		this(0.0f, 0.0f);
	}
	
	public Caption(float pos_x, float pos_y) {
		this.positionX = pos_x;
		this.positionY = pos_y;
		this.lines = new Vector<Line>();
		this.width = 0.0f;
		this.height = 0.0f;
	}
	
	public void addLine (Color c, String t) {
		Line l = new Line ();
		l.getIcon().setColor(c);
		l.getText().setText(t);
		lines.add(l);
	}
	
	public void draw () throws GLSingletonNotInitializedException {
		// Calculamos el tama�o de la leyenda en funci�n del n�mero de l�neas y la longitud de la m�s larga
		int num_lines = lines.size();
		int max_length = 0;
		for (Line l : lines) {
			int aux = l.getLengthPX();
			if (aux > max_length) max_length = aux; 
		}

		if (num_lines > 0) {
			width = this.pxGAP * 2 + max_length;
		}
		
		frame = new Frame ((int)Math.ceil(width), (int)Math.ceil((num_lines+1) * this.pxGAP + num_lines * lines.firstElement().getHeightPX()));
		//System.out.println("frame width: " + f.getWidth() + "\nframe height: " + f.getHeight());
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX, this.positionY, 0.0f);
			frame.draw();
		GLSingleton.getGL().glPopMatrix();
		
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glLoadIdentity();
			Vector3f oglGAP = GLUtils.getScreen2World(this.pxGAP, this.pxGAP, true);
			float vgap = GLUtils.getScreen2World(0, 20, true).getY();
		GLSingleton.getGL().glPopMatrix();
		GLSingleton.getGL().glPushMatrix();
			GLSingleton.getGL().glTranslatef(this.positionX + oglGAP.getX(), this.positionY - oglGAP.getY() * 2, 0.0f);
			for (int i = 0 ; i < lines.size(); i++) {
				if (i == 0) GLSingleton.getGL().glTranslatef(0.0f, 0.0f, 0.0f);
				else GLSingleton.getGL().glTranslatef(0.0f, -vgap, 0.0f);
				lines.get(i).draw();
			}
		GLSingleton.getGL().glPopMatrix();
	}

	public List getLines() {
		return lines;
	}
	
	public float getHeight() {
		float height;
		try {
			int aux = (int)Math.ceil((lines.size()+1) * this.pxGAP + lines.size() * lines.firstElement().getHeightPX());
			Vector3f v = GLUtils.getScreen2World(0, aux, true);
			height = v.getY();
		} catch (GLSingletonNotInitializedException e) {
			height = 0.0f;
		}
		return height;
	}
	
	public float getWidth() {
		return this.width;
	}
	
}