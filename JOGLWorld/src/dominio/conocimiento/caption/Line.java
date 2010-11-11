package dominio.conocimiento.caption;

import dominio.conocimiento.Text;
import dominio.conocimiento.Color;
import dominio.control.GLSingleton;
import exceptions.GLSingletonNotInitializedException;

public class Line {
	private final float GAP = 0.20f;
	private Icon icon;
	private Text text;
	
	public Line() {
		this.icon = new Icon(10, 10, new Color(1.0f, 1.0f, 1.0f));
		this.text = new Text("");
	}
	
	public void draw () throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glPushMatrix();
			this.icon.draw();
			GLSingleton.getGL().glTranslatef(this.GAP, 0.0f, 0.0f);
			this.text.draw();
		GLSingleton.getGL().glPopMatrix();
	}

	public float getGAP() {
		return GAP;
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
