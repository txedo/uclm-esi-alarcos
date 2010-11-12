package model.knowledge.caption;

import model.GLSingleton;
import model.GLUtils;
import model.knowledge.Color;
import model.knowledge.GLObject;
import model.knowledge.Text;
import exceptions.GLSingletonNotInitializedException;

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
			GLSingleton.getGL().glTranslatef(GLUtils.GetOGLPos2D(this.icon.getWidth() + this.pxGAP, 0).getX(), 0.0f, 0.0f);
			this.text.draw();
		GLSingleton.getGL().glPopMatrix();
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
