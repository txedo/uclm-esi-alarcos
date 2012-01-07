package model.gl.knowledge;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLUquadric;

import util.GLDimension;

import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.util.Color;
import exceptions.GLSingletonNotInitializedException;

public class GLAntennaBall extends GLObject3D {
    private int[] textures;

    private int subdivisions;
    @GLDimension(type = "string")
    private String label;
    @GLDimension
    private float parentBallRadius;
    @GLDimension
    private boolean progressionMark;
    private float childBallRadius;
    private Color leftChildBallColor;
    private Color rightChildBallColor;
    @GLDimension(type = "string")
    private String leftChildBallValue;
    @GLDimension(type = "string")
    private String rightChildBallValue;
    private final float ANTENNA_WIDTH = 3.0f;
    private final float ANTENNA_ANGLE = 45.0f;
    private final float ANTENNA_LENGTH = 0.5f;

    public static final float MAX_SIZE = 1.5f;
    public static final float MAX_HEIGHT = MAX_SIZE * 2;

    private GLUquadric quadric;

    public GLAntennaBall() {
        this(0.0f, 0.0f);
    }

    public GLAntennaBall(float pos_x, float pos_y) {
        super();
        this.positionX = pos_x;
        this.positionZ = pos_y;
        this.color = new Color(0.0f, 0.0f, 1.0f); // This will be used for the
        // parent ball color

        this.subdivisions = 32;
        this.parentBallRadius = 1.0f;
        this.progressionMark = true;
        this.childBallRadius = 0.5f;
        this.leftChildBallColor = new Color(0.0f, 1.0f, 0.0f);
        this.rightChildBallColor = new Color(1.0f, 0.0f, 0.0f);

        this.leftChildBallValue = "";
        this.rightChildBallValue = "";

        this.maxWidth = GLAntennaBall.MAX_SIZE * 2.0f;
        this.maxDepth = GLAntennaBall.MAX_SIZE * 2.0f;
    }

    @Override
    protected void draw(boolean shadow)
            throws GLSingletonNotInitializedException {
        GLUtils.enableMultisample();
        GLSingleton.getGL().glPushMatrix();
        
        // Move to the parent ball center by doing the scale in two steps, so the ball keeps at floor and moves in correctly plane XZ
        GLSingleton.getGL().glScalef(1.0f, this.scale, 1.0f);
        GLSingleton.getGL().glTranslatef(this.positionX, this.parentBallRadius, this.positionZ);
        GLSingleton.getGL().glScalef(this.scale, 1.0f, this.scale);
        // if parentBallRadius == 0.0f, make a copy and use 1.0f in order to draw child balls and shadows
        float parentBallRadiusBackup = this.parentBallRadius;
        if (this.parentBallRadius == 0.0f) {
            this.parentBallRadius = 0.1f;
        }
        
        // Draw the child balls
        if (shadow) {
            this.drawChildBall(true, super.SHADOW_COLOR, "");
            this.drawChildBall(false, super.SHADOW_COLOR, "");
        } else {
            this.drawChildBall(true, this.leftChildBallColor,
                    this.leftChildBallValue);
            this.drawChildBall(false, this.rightChildBallColor,
                    this.rightChildBallValue);
        }

        // Drawing the parent ball
        if (!shadow) {
            // Enable texture mapping
            GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
            GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_GEN_S);
            GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_GEN_T);
            // Set Up Sphere Mapping
            GLSingleton.getGL().glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE,
                    GL2.GL_SPHERE_MAP);
            GLSingleton.getGL().glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE,
                    GL2.GL_SPHERE_MAP);
            // Bind the APPLY(0) or CANCEL(1) texture
            int texture = this.textures[0];
            if (!this.progressionMark)
                texture = this.textures[1];
            GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D, texture);
            GLSingleton.getGL().glColor4fv(this.color.getColorFB());
        } else {
            GLSingleton.getGL().glColor4fv(super.SHADOW_COLOR.getColorFB());
        }
        
        // Set parentBallRadius back to its real value
        this.parentBallRadius = parentBallRadiusBackup;
        // Now we draw the sphere
        GLSingleton.getGLU().gluSphere(this.quadric, this.parentBallRadius,
                this.subdivisions, this.subdivisions);

        if (!shadow) {
            // Disable everything we enabled before
            GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_GEN_S);
            GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_GEN_T);
            GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_2D);
        }
        GLSingleton.getGL().glPopMatrix();
        GLUtils.disableMultisample();
    }

    private void drawChildBall(boolean left, Color color, String value)
            throws GLSingletonNotInitializedException {
        int x = 1;
        if (left)
            x = -1;
        GLSingleton.getGL().glLineWidth(ANTENNA_WIDTH);
        // Draw the antenna
        GLSingleton.getGL().glPushMatrix();
        if (color.equals(this.rightChildBallColor)
                || color.equals(this.leftChildBallColor))
            GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
        else
            GLSingleton.getGL().glColor4fv(super.SHADOW_COLOR.getColorFB());
        GLSingleton.getGL().glBegin(GL2.GL_LINES);
        // GLSingleton.getGL().glVertex3f(x*parentBallRadius*(float)Math.cos(ANTENNA_ANGLE),
        // parentBallRadius*(float)Math.sin(ANTENNA_ANGLE), 0.0f);
        // GLSingleton.getGL().glVertex3f(x*parentBallRadius-x*childBallRadius*(float)Math.cos(90-ANTENNA_ANGLE),
        // parentBallRadius*(float)Math.tan(ANTENNA_ANGLE)-childBallRadius*(float)Math.sin(90-ANTENNA_ANGLE),
        // 0.0f);
        GLSingleton.getGL().glVertex3f(
                x * parentBallRadius * (float) Math.cos(ANTENNA_ANGLE),
                parentBallRadius * (float) Math.sin(ANTENNA_ANGLE), 0.0f);
        GLSingleton.getGL().glVertex3f(
                x * (parentBallRadius + ANTENNA_LENGTH)
                        * (float) Math.cos(ANTENNA_ANGLE),
                (parentBallRadius + ANTENNA_LENGTH)
                        * (float) Math.sin(ANTENNA_ANGLE), 0.0f);
        GLSingleton.getGL().glEnd();
        // Draw the child ball
        GLSingleton.getGL().glTranslatef(
                x * (parentBallRadius + ANTENNA_LENGTH + childBallRadius)
                        * (float) Math.cos(ANTENNA_ANGLE),
                (parentBallRadius + ANTENNA_LENGTH + childBallRadius)
                        * (float) Math.sin(ANTENNA_ANGLE), 0.0f);
        GLSingleton.getGL().glColor4fv(color.getColorFB());
        GLSingleton.getGLU().gluSphere(this.quadric, this.childBallRadius,
                this.subdivisions, this.subdivisions);
        // Draw its value
        GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
        GLUtils.renderBitmapString(0, this.childBallRadius * 1.10f, 0, 4, "" + value);
        GLSingleton.getGL().glPopMatrix();
    }

    public void setTextures(int[] textureNames) {
        this.textures = textureNames;
    }

    public void setQuadric(GLUquadric quadric) {
        this.quadric = quadric;
    }

    public void setParentBallRadius(float parentBallRadius) {
        this.parentBallRadius = parentBallRadius;
    }

    public float getParentBallRadius() {
        return this.parentBallRadius;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLeftChildBallValue() {
        return leftChildBallValue;
    }

    public void setLeftChildBallValue(String leftChildBallValue) {
        this.leftChildBallValue = leftChildBallValue;
    }

    public String getRightChildBallValue() {
        return rightChildBallValue;
    }

    public void setRightChildBallValue(String rightChildBallValue) {
        this.rightChildBallValue = rightChildBallValue;
    }

    public boolean isProgressionMark() {
        return progressionMark;
    }

    public void setProgressionMark(boolean progression) {
        this.progressionMark = progression;
    }

}
