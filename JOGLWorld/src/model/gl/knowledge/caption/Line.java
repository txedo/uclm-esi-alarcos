package model.gl.knowledge.caption;

import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.Text;
import model.knowledge.Color;
import exceptions.gl.GLSingletonNotInitializedException;

public class Line extends GLObject {
	private final int pxGAP = 10; // px
	private Icon icon;
	private Text text;
	
	public Line() {
		this.icon = new Icon(10, 10, new Color(1.0f, 1.0f, 1.0f));
		this.text = new Text("");
	}
	
	public void draw () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glPushMatrix();
			this.icon.draw();
			GLSingleton.getGL().glTranslatef(GLUtils.getScreen2World(this.icon.getWidth() + this.pxGAP, 0, true).getX(), 0.0f, 0.0f);
			this.text.draw();
		GLSingleton.getGL().glPopMatrix();
	}
	
	public int getLengthPX () throws GLSingletonNotInitializedException {
		return this.icon.getWidth() + this.pxGAP + this.text.getLengthPX();
	}
	
	public int getHeightPX () throws GLSingletonNotInitializedException {
		int max = this.icon.getHeight();
		if (max < this.text.getHeightInPx()) max = this.text.getHeightInPx();
		return max;
	}

	public float getPxGAP() {
		return pxGAP;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
}
