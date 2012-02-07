package model.gl.knowledge.caption;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLText;
import model.util.Color;
import model.util.Vector3f;
import exceptions.GLSingletonNotInitializedException;

public class Line extends GLObject {
    private final int pxGAP = 10; // px
    private Icon icon;
    private GLText text;

    public Line() {
        this.icon = new Icon(10, 10, new Color(1.0f, 1.0f, 1.0f));
        this.text = new GLText("");
    }

    public void draw() throws GLSingletonNotInitializedException {
        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glLoadIdentity();
        Vector3f v = GLUtils.getScreen2World(this.icon.getWidth() + this.pxGAP,
                0, true);
        GLSingleton.getGL().glPopMatrix();
        GLSingleton.getGL().glPushMatrix();
        this.icon.draw();
        GLSingleton.getGL().glTranslatef(v.getX(), 0.0f, 0.0f);
        this.text.draw();
        GLSingleton.getGL().glPopMatrix();
    }

    public int getLengthPX() throws GLSingletonNotInitializedException {
        return this.icon.getWidth() + this.pxGAP + this.text.getLengthPX();
    }

    public int getHeightPX() throws GLSingletonNotInitializedException {
        int max = this.icon.getHeight();
        if (max < this.text.getHeightInPx())
            max = this.text.getHeightInPx();
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

    public GLText getText() {
        return text;
    }

    public void setText(GLText text) {
        this.text = text;
    }

}
