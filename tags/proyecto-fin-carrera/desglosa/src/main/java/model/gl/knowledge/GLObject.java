package model.gl.knowledge;

import javax.media.opengl.GL2;

import util.GLDimension;

import model.gl.GLSingleton;
import model.util.Color;

import exceptions.GLSingletonNotInitializedException;

public abstract class GLObject {
    @GLDimension(name = "id", type = "int")
    protected int id;
    protected float maxWidth = 0.0f;
    protected float maxDepth = 0.0f;
    protected float positionX;
    protected float positionZ;
    @GLDimension(name = "color", type = "color")
    protected Color color;
    @GLDimension(name = "scale", type = "float_range")
    protected float scale;

    public GLObject() {
        this.id = -1;
        this.positionX = 0.0f;
        this.positionZ = 0.0f;
        this.color = new Color(0.0f, 0.0f, 0.0f);
        this.scale = 1.0f;
    }

    public GLObject(float posx, float posy) {
        this.id = -1;
        this.positionX = posx;
        this.positionZ = posy;
        this.color = new Color(0.0f, 0.0f, 0.0f);
        this.scale = 1.0f;
    }

    public abstract void draw() throws GLSingletonNotInitializedException;

    protected void enableLight() throws GLSingletonNotInitializedException {
        GLSingleton.getGL().glEnable(GL2.GL_LIGHTING);
        GLSingleton.getGL().glEnable(GL2.GL_LIGHT1);
    }

    protected void disableLight() throws GLSingletonNotInitializedException {
        GLSingleton.getGL().glDisable(GL2.GL_LIGHTING);
        GLSingleton.getGL().glDisable(GL2.GL_LIGHT1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float origin_x) {
        this.positionX = origin_x;
    }

    public float getPositionZ() {
        return positionZ;
    }

    public void setPositionZ(float origin_z) {
        this.positionZ = origin_z;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(String hexCode) {
        this.color = new Color(hexCode);
    }

    public Color getColor() {
        return color;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getMaxWidth() {
        return maxWidth;
    }

    public float getMaxDepth() {
        return maxDepth;
    }

}
