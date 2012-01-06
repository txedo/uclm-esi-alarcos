package model.gl.control;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import com.jogamp.common.nio.Buffers;

import exceptions.GLSingletonNotInitializedException;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLPavement;
import model.gl.knowledge.caption.Caption;
import model.util.Vector3f;

public abstract class GLViewManager {

    protected final int BUFFSIZE = 512;
    /* http://www.cgtextures.com/ */
    private final String PAVEMENT_TEXTURE = "textures/pavement-texture.jpg";
    private final String FLOOR_TEXTURE = "textures/floor-texture.jpg";
    private final String[] SKYBOX_TEXTURE = { "textures/skybox0.png",
            "textures/skybox1.png", "textures/skybox2.png",
            "textures/skybox3.png", "textures/skybox4.png" };

    protected GLDrawer drawer;
    protected boolean threeDimensional;
    protected boolean selectionMode;
    protected int clickButton;
    protected int clickCount;
    protected static double pickingRegion;
    private List<GLObject> pavements;
    private TextureLoader textureLoader;
    private TextureLoader skyboxTextureLoader;

    private Caption caption = null;

    protected boolean shadowSupport;
    protected boolean drawingShadows = false;

    public GLViewManager(GLDrawer d, boolean is3D) {
        this.drawer = d;
        this.threeDimensional = is3D;
        this.shadowSupport = false;
        this.selectionMode = false;
        this.clickCount = 0;
        pickingRegion = 0.1;
        textureLoader = new TextureLoader(new String[] { FLOOR_TEXTURE,
                PAVEMENT_TEXTURE });
        skyboxTextureLoader = new TextureLoader(SKYBOX_TEXTURE);
    }

    /**
     * Called by the GLDrawer.setViewLevel() function to configure OpenGL
     * properties.
     */
    public abstract void configureView()
            throws GLSingletonNotInitializedException;

    /**
     * Called by the GLDrawer.setViewLevel() function to deconfigure previously
     * configured OpenGL properties.
     */
    public abstract void deconfigureView()
            throws GLSingletonNotInitializedException;

    /**
     * Called by the GLDrawer.display() function to manage view functions such
     * as drawing and selection.
     */
    public abstract void manageView()
            throws GLSingletonNotInitializedException, IOException;

    public abstract void setItems(List<GLObject> objs);

    public abstract void addItems(List<GLObject> objs);

    public abstract void drawItems() throws GLSingletonNotInitializedException;

    public void setPavements(List<GLObject> pavs) {
        pavements = new ArrayList<GLObject>();
        pavements.addAll(pavs);
    }

    public void drawPavements() throws GLSingletonNotInitializedException,
            IOException {
        if (!textureLoader.isTexturesLoaded())
            textureLoader.loadTexures(true, true, true, false);
        if (pavements != null) {
            for (GLObject globj : pavements) {
                if (globj instanceof GLPavement) {
                    if (((GLPavement) globj).getTexture() == -1) {
                        ((GLPavement) globj).setTexture(textureLoader
                                .getTextureNames()[1]);
                    }
                    ((GLPavement) globj).draw();
                }
            }
        }
    }

    public void drawShadows() throws GLSingletonNotInitializedException {
        if (this.shadowSupport) {
            this.drawingShadows = true;
            this.drawItems();
            this.drawingShadows = false;
        }
    }

    public void selectItem() throws GLSingletonNotInitializedException {
        int[] selectBuff = new int[BUFFSIZE];
        IntBuffer selectBuffer = Buffers.newDirectIntBuffer(BUFFSIZE);
        int hits = 0;
        int[] viewport = new int[4];
        // Save somewhere the info about the current viewport
        GLSingleton.getGL().glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        // Initialize a selection buffer, which will contain data about selected
        // objects
        GLSingleton.getGL().glSelectBuffer(BUFFSIZE, selectBuffer);
        // Switch to GL_SELECT mode
        GLSingleton.getGL().glRenderMode(GL2.GL_SELECT);
        // Initialize the name stack
        GLSingleton.getGL().glInitNames();
        // Now fill the stack with one element (or glLoadName will generate an
        // error)
        GLSingleton.getGL().glPushName(-1);
        // Restrict the drawing area around the mouse position (5x5 pixel
        // region)
        GLSingleton.getGL().glMatrixMode(GL2.GL_PROJECTION);
        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glLoadIdentity();
        // Important: gl (0,0) is bottom left but window coordinates (0,0) are
        // top left so we have to change this!
        GLSingleton.getGLU().gluPickMatrix(this.drawer.getPickPoint().getX(),
                viewport[3] - this.drawer.getPickPoint().getY(), pickingRegion,
                pickingRegion, viewport, 0);
        if (threeDimensional) {
            float h = (float) this.drawer.getScreenWidth()
                    / this.drawer.getScreenHeight();
            GLSingleton.getGLU().gluPerspective(60.0f, h, 0.1f, 50.0f);
        } else {
            float h = (float) this.drawer.getScreenHeight()
                    / (float) this.drawer.getScreenWidth();
            GLSingleton.getGL().glOrtho(0.0, this.drawer.getDim(), 0.0,
                    this.drawer.getDim() * h, -1, 1);
        }
        // 6. Draw the objects with their names
        GLSingleton.getGL().glMatrixMode(GL2.GL_MODELVIEW);
        GLSingleton.getGL().glPushMatrix(); // //////////////////////////////////////////////
        this.drawItems();
        GLSingleton.getGL().glMatrixMode(GL2.GL_PROJECTION);
        GLSingleton.getGL().glPopMatrix();
        GLSingleton.getGL().glMatrixMode(GL2.GL_MODELVIEW);
        GLSingleton.getGL().glPopMatrix(); // //////////////////////////////////////////////
        GLSingleton.getGL().glFlush();
        // 7. Get the number of hits
        hits = GLSingleton.getGL().glRenderMode(GL2.GL_RENDER);
        // 8. Handle the hits, and get the picked object
        selectBuffer.get(selectBuff);
        this.handleHits(hits, selectBuff);
        selectionMode = false;
        // clickCount = 0;
    }

    /**
     * Called by the selectItem function.
     * 
     * @param hits
     *            The number of items clicked or hitted.
     * @param data
     *            A 4-multiple array containing blocks of data. Each block of
     *            data has four units of information. The first one indicates
     *            the number of the element which goes from 1 to <i>hits</i>. If
     *            the value is 0, then no items has been clicked. The second one
     *            indicates the minimum depth in axis Z. The third one indicates
     *            the maxinum depth in axis Z. The depthless object should be
     *            usually picked up. The fourth and last one indicates the name
     *            of the object, previously setted with the glLoadName(int)
     *            primitive.
     */
    protected void handleHits(int hits, int[] data) {
        int offset = 0;
        System.out.println("Number of hits = " + hits);
        if (hits > 0) { // If There Were More Than 0 Hits
            int choose = data[3]; // Make Our Selection The First Object
            int depth = data[1]; // Store How Far Away It Is
            for (int loop = 1; loop < hits; loop++) { // Loop Through All The
                                                      // Detected Hits
                // If This Object Is Closer To Us Than The One We Have Selected
                if (data[loop * 4 + 1] <= depth) {
                    choose = data[loop * 4 + 3]; // Select The Closer Object
                    depth = data[loop * 4 + 1]; // Store How Far Away It Is
                }
            }
            selectedObjectHandler(choose);
            offset++;
        }
    }

    protected void drawCaption() throws GLSingletonNotInitializedException {
        if (caption != null && caption.getLines().size() > 0) {
            GLSingleton.getGL().glPushMatrix();
            GLUtils.beginOrtho(drawer.getScreenHeight(),
                    drawer.getScreenWidth(), drawer.DIM);
            GLSingleton.getGL().glPushMatrix();
            GLSingleton.getGL().glTranslatef(0.1f, caption.getHeight() + 0.1f,
                    0.0f);
            caption.draw();
            GLSingleton.getGL().glPopMatrix();
            GLUtils.endOrtho();
            GLSingleton.getGL().glPopMatrix();
        }
    }

    protected void drawFloor() throws GLSingletonNotInitializedException,
            IOException {
        final float FLOOR_DIMENSION = 100.0f;
        if (!textureLoader.isTexturesLoaded())
            textureLoader.loadTexures(true, true, true, false);
        GLUtils.enableMultisample();
        GLSingleton.getGL().glDisable(GL2.GL_LIGHTING);
        GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
        GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV,
                GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                textureLoader.getTextureNames()[0]);
        GLSingleton.getGL().glNormal3f(0.0f, 1.0f, 0.0f);
        GLSingleton.getGL().glColor3f(0.9f, 0.9f, 0.9f);
        GLSingleton.getGL().glBegin(GL2.GL_QUADS);
        GLSingleton.getGL().glTexCoord2f(0.0f, FLOOR_DIMENSION);
        GLSingleton.getGL()
                .glVertex3f(-FLOOR_DIMENSION, 0.0f, -FLOOR_DIMENSION);
        GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f);
        GLSingleton.getGL().glVertex3f(-FLOOR_DIMENSION, 0.0f, FLOOR_DIMENSION);
        GLSingleton.getGL().glTexCoord2f(FLOOR_DIMENSION, 0.0f);
        GLSingleton.getGL().glVertex3f(FLOOR_DIMENSION, 0.0f, FLOOR_DIMENSION);
        GLSingleton.getGL().glTexCoord2f(FLOOR_DIMENSION, FLOOR_DIMENSION);
        GLSingleton.getGL().glVertex3f(FLOOR_DIMENSION, 0.0f, -FLOOR_DIMENSION);
        GLSingleton.getGL().glEnd();
        GLSingleton.getGL().glDisable(GL2.GL_TEXTURE_2D);
        GLSingleton.getGL().glEnable(GL2.GL_LIGHTING);
        GLUtils.disableMultisample();
    }

    protected void drawSkybox() throws GLSingletonNotInitializedException,
            IOException {
        // This skybox technique was extracted from
        // http://sidvind.com/wiki/Skybox_tutorial
        if (!skyboxTextureLoader.isTexturesLoaded())
            skyboxTextureLoader.loadTexures(true, true, true, true);
        GLUtils.enableMultisample();
        GLSingleton.getGL().glPushMatrix();
        GLSingleton.getGL().glLoadIdentity();
        Vector3f viewDir = this.drawer.getCamera().getViewDir();
        GLSingleton.getGLU().gluLookAt(0, 0, 0, viewDir.getX(), viewDir.getY(),
                viewDir.getZ(), 0, 1, 0);
        GLSingleton.getGL().glPushAttrib(GL2.GL_ENABLE_BIT);
        GLSingleton.getGL().glEnable(GL2.GL_TEXTURE_2D);
        GLSingleton.getGL().glDisable(GL2.GL_DEPTH_TEST);
        GLSingleton.getGL().glDisable(GL2.GL_LIGHTING);
        GLSingleton.getGL().glDisable(GL2.GL_BLEND);
        GLSingleton.getGL().glColor4f(1, 1, 1, 1);

        // Render the front quad
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                skyboxTextureLoader.getTextureNames()[0]);
        GLSingleton.getGL().glBegin(GL2.GL_QUADS);
        GLSingleton.getGL().glTexCoord2f(0, 0);
        GLSingleton.getGL().glVertex3f(-0.5f, -0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 0);
        GLSingleton.getGL().glVertex3f(0.5f, -0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 1);
        GLSingleton.getGL().glVertex3f(0.5f, 0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(0, 1);
        GLSingleton.getGL().glVertex3f(-0.5f, 0.5f, -0.5f);
        GLSingleton.getGL().glEnd();

        // Render the left quad
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                skyboxTextureLoader.getTextureNames()[1]);
        GLSingleton.getGL().glBegin(GL2.GL_QUADS);
        GLSingleton.getGL().glTexCoord2f(0, 0);
        GLSingleton.getGL().glVertex3f(-0.5f, -0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 0);
        GLSingleton.getGL().glVertex3f(-0.5f, -0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 1);
        GLSingleton.getGL().glVertex3f(-0.5f, 0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(0, 1);
        GLSingleton.getGL().glVertex3f(-0.5f, 0.5f, 0.5f);
        GLSingleton.getGL().glEnd();

        // Render the back quad
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                skyboxTextureLoader.getTextureNames()[2]);
        GLSingleton.getGL().glBegin(GL2.GL_QUADS);
        GLSingleton.getGL().glTexCoord2f(0, 0);
        GLSingleton.getGL().glVertex3f(0.5f, -0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 0);
        GLSingleton.getGL().glVertex3f(-0.5f, -0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 1);
        GLSingleton.getGL().glVertex3f(-0.5f, 0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(0, 1);
        GLSingleton.getGL().glVertex3f(0.5f, 0.5f, 0.5f);
        GLSingleton.getGL().glEnd();

        // Render the right quad
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                skyboxTextureLoader.getTextureNames()[3]);
        GLSingleton.getGL().glBegin(GL2.GL_QUADS);
        GLSingleton.getGL().glTexCoord2f(0, 0);
        GLSingleton.getGL().glVertex3f(0.5f, -0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 0);
        GLSingleton.getGL().glVertex3f(0.5f, -0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 1);
        GLSingleton.getGL().glVertex3f(0.5f, 0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(0, 1);
        GLSingleton.getGL().glVertex3f(0.5f, 0.5f, -0.5f);
        GLSingleton.getGL().glEnd();

        // Render the top quad
        GLSingleton.getGL().glBindTexture(GL2.GL_TEXTURE_2D,
                skyboxTextureLoader.getTextureNames()[4]);
        GLSingleton.getGL().glBegin(GL2.GL_QUADS);
        GLSingleton.getGL().glTexCoord2f(0, 0);
        GLSingleton.getGL().glVertex3f(-0.5f, 0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 0);
        GLSingleton.getGL().glVertex3f(0.5f, 0.5f, -0.5f);
        GLSingleton.getGL().glTexCoord2f(1, 1);
        GLSingleton.getGL().glVertex3f(0.5f, 0.5f, 0.5f);
        GLSingleton.getGL().glTexCoord2f(0, 1);
        GLSingleton.getGL().glVertex3f(-0.5f, 0.5f, 0.5f);
        GLSingleton.getGL().glEnd();

        // Restore enable bits and matrix
        GLSingleton.getGL().glPopAttrib();
        GLSingleton.getGL().glPopMatrix();
        GLUtils.disableMultisample();
    }

    protected abstract void selectedObjectHandler(int selectedObject);

    public boolean isSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(boolean selectionMode, int clickButton,
            int clickCount) {
        this.selectionMode = selectionMode;
        this.clickCount = clickCount;
        this.clickButton = clickButton;
    }

    public static void setPickingRegion(double pickRegion) {
        pickingRegion = pickRegion;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public boolean isShadowSupport() {
        return shadowSupport;
    }

    public void setShadowSupport(boolean shadowSupport) {
        this.shadowSupport = shadowSupport;
    }

    public GLDrawer getDrawer() {
        return drawer;
    }

    public void setCaption(Caption c) {
        this.caption = c;
    }

}