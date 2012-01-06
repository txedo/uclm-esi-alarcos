package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;
import model.gl.knowledge.GLAntennaBall;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;
import model.util.Vector3f;

import exceptions.GLSingletonNotInitializedException;
import exceptions.ViewManagerNotInstantiatedException;

public class GLProjectViewManager extends GLViewManager {
    static private GLProjectViewManager _instance = null;

    private final String APPLY = "textures/gtk-apply.png";
    private final String CANCEL = "textures/gtk-cancel.png";

    private GLUquadric quadric;
    private TextureLoader textureLoader;
    private static List<GLObject> antennaBalls;

    /**
     * @return The unique instance of this class.
     * @throws ViewManagerNotInstantiatedException
     */
    static public GLProjectViewManager getInstance()
            throws ViewManagerNotInstantiatedException {
        if (null == _instance) {
            throw new ViewManagerNotInstantiatedException();
        }
        return _instance;
    }

    public GLProjectViewManager(GLDrawer d, boolean is3d) {
        super(d, is3d);
        antennaBalls = new ArrayList<GLObject>();
        textureLoader = new TextureLoader(new String[] { APPLY, CANCEL });
        _instance = this;
    }

    @Override
    public void configureView() throws GLSingletonNotInitializedException {
        try {
            if (!textureLoader.isTexturesLoaded())
                textureLoader.loadTexures(true, true, true, false);

            // Create A New Quadratic
            this.quadric = GLSingleton.getGLU().gluNewQuadric();
            // Generate Smooth Normals For The Quad
            GLSingleton.getGLU().gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
            // Enable Texture Coords For The Quad
            GLSingleton.getGLU().gluQuadricTexture(quadric, true);
            GLSingleton.getGLU().gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
            GLSingleton.getGLU()
                    .gluQuadricOrientation(quadric, GLU.GLU_OUTSIDE);

            for (GLObject ball : antennaBalls) {
                ((GLAntennaBall) ball).setQuadric(quadric);
                ((GLAntennaBall) ball).setTextures(textureLoader
                        .getTextureNames());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void deconfigureView() throws GLSingletonNotInitializedException {
        GLSingleton.getGLU().gluDeleteQuadric(quadric);
    }

    @Override
    public void manageView() throws GLSingletonNotInitializedException,
            IOException {
        if (this.isSelectionMode())
            this.selectItem();
        super.drawSkybox();
        super.drawFloor();
        super.drawPavements();
        GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV,
                GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
        this.drawItems();
        super.drawCaption();
    }

    @Override
    public void setItems(List<GLObject> objs) {
        antennaBalls = new ArrayList<GLObject>();
        antennaBalls.addAll(objs);
    }

    @Override
    public void addItems(List<GLObject> objs) {
        if (antennaBalls == null){
            antennaBalls = new ArrayList<GLObject>();
        }
        antennaBalls.addAll(objs);
    }

    @Override
    public void drawItems() throws GLSingletonNotInitializedException {
        int cont = 1;
        for (GLObject glo : antennaBalls) {
            if (selectionMode)
                GLSingleton.getGL().glLoadName(cont++);
            if (this.drawingShadows)
                ((GLObject3D) glo).drawShadow();
            else {
                ((GLObject3D) glo).draw();
                // Write the project label
                Vector3f cameraViewDir = this.drawer.getCamera().getViewDir().clone();
                cameraViewDir.setY(0.0f);
                cameraViewDir.normalize();
                Vector3f normalizedOppositeCameraViewDir = cameraViewDir .mult(-1);
                Vector3f objectPosition = new Vector3f(glo.getPositionX(), 0.0f, glo.getPositionZ());
                float offset = ((GLAntennaBall) glo).getParentBallRadius();
                if (offset == 0.0f) {
                    offset = 1.0f;
                }
                Vector3f offsetPosition = objectPosition.add(normalizedOppositeCameraViewDir.mult(offset));
                GLUtils.renderBitmapString(offsetPosition.getX(), 0.1f, offsetPosition.getZ(), 4, ((GLAntennaBall) glo).getLabel());
            }
        }
    }

    @Override
    protected void selectedObjectHandler(int selectedObject) {
        int GLObjectId = antennaBalls.get(selectedObject - 1).getId();
        System.err.println("Selected project: " + GLObjectId + "\tButton: "
                + clickButton + "\tNumber of clicks: " + clickCount);
        NotifyUIManager.notifySelectedAntennaBall(GLObjectId, clickButton,
                clickCount);
    }

}
