package presentation;

import java.applet.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import exceptions.ViewManagerNotInstantiatedException;

import javax.media.opengl.GLAnimatorControl;

import model.IGLFacade;
import model.IGLFacadeImpl;
import model.IObserverUI;
import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.util.Synchronizer;

public class AppletMain extends Applet implements IObserverUI, IGLFacade {
    private static final long serialVersionUID = -8016293827472817335L;
    private GLAnimatorControl animator;
    private GLDrawer drawer = null;
    
    private final static Logger log = Logger.getAnonymousLogger();

    @Override
    public void init() {
        log.info("DesglosaApplet: init() - begin");
        // Creating an object to manipulate OpenGL parameters.
        GLProfile.initSingleton(false);
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);
        // Set number of stencil bits in order to have stencil buffer working
        capabilities.setStencilBits(8);
        // Sample buffers are used to get polygon anti-aliasing working
        capabilities.setSampleBuffers(true);
        capabilities.setNumSamples(2);
        setLayout(new BorderLayout());
        GLCanvas canvas = new GLCanvas(capabilities);
        drawer = new GLDrawer();
        canvas.addGLEventListener(drawer);
        canvas.setSize(getSize());
        add(canvas, BorderLayout.CENTER);
        animator = new FPSAnimator(canvas, 60);

        NotifyUIManager.attach(this);

        animator.start();

        log.info("DesglosaApplet: init() - end");
    }

    @Override
    public void start() {
        log.info("DesglosaApplet: start() - begin");

//         try {
//             Synchronizer.getInstance().solicitar();
//             IGLFacadeImpl.getInstance().visualizeTowers("{\"captionLines\":{},\"model\":\"model.gl.knowledge.GLTower\",\"neighborhoods\":[{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1,\"r\":1},\"depth\":1,\"height\":0,\"id\":1,\"innerHeight\":1,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionY\":0,\"positionZ\":0,\"scale\":1,\"width\":1},{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":1,\"r\":1},\"depth\":1,\"height\":0,\"id\":2,\"innerHeight\":1,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionY\":0,\"positionZ\":0,\"scale\":1,\"width\":1}],\"model\":\"\",\"name\":\"\",\"neighborhoods\":[],\"pavements\":[],\"ratios\":{},\"width\":0}],\"pavements\":[],\"ratios\":{\"height\":100}}");
//         } catch (ViewManagerNotInstantiatedException e) {
//             e.printStackTrace();
//         }

        log.info("DesglosaApplet: start() - end");
    }

    @Override
    public void stop() {
        log.info("DesglosaApplet: stop() - begin");
        animator.stop();
        log.info("DesglosaApplet: stop() - end");
    }

    public void destroy() {
        log.info("DesglosaApplet: destroy() - X");
    }

    @Override
    public void selectAntennaBall(int id, int clickButton, int clickCount) {
        this.handleEvent(EOperationCodes.AntennaBallSelection, id, clickButton,
                clickCount);
    }

    @Override
    public void selectBuilding(int id, int clickButton, int clickCount) {
        this.handleEvent(EOperationCodes.BuildingSelection, id, clickButton,
                clickCount);
    }

    @Override
    public void selectTower(int id, int clickButton, int clickCount) {
        this.handleEvent(EOperationCodes.TowerSelection, id, clickButton,
                clickCount);
    }

    private void handleEvent(EOperationCodes opCode, Object ob,
            int clickButton, int clickCount) {
        try {
            switch (opCode) {
            case TowerSelection:
                if (ob instanceof Integer) {
                    int id = (Integer) ob;
                    getAppletContext().showDocument(
                            new URL("javascript:selectTower(" + id + ","
                                    + clickButton + "," + clickCount + ")"));
                }
                break;
            case BuildingSelection:
                if (ob instanceof Integer) {
                    int id = (Integer) ob;
                    getAppletContext().showDocument(
                            new URL("javascript:selectBuilding(" + id + ","
                                    + clickButton + "," + clickCount + ")"));
                }
                break;
            case AntennaBallSelection:
                if (ob instanceof Integer) {
                    int id = (Integer) ob;
                    getAppletContext().showDocument(
                            new URL("javascript:selectAntennaBall(" + id + ","
                                    + clickButton + "," + clickCount + ")"));
                }
                break;
            default:
                getAppletContext()
                        .showDocument(
                                new URL(
                                        "javascript:selectionError(\'Undefined operation\')"));
                break;
            }
        } catch (MalformedURLException e) {
            try {
                getAppletContext().showDocument(
                        new URL("javascript:selectionError(\'" + e.toString()
                                + "\')"));
            } catch (MalformedURLException e1) {
            }
        }
    }

    @Override
    public void visualizeBuildings(String JSONtext) {
        Synchronizer.getInstance().solicitar();
        try {
            IGLFacadeImpl.getInstance().visualizeBuildings(JSONtext);
        } catch (ViewManagerNotInstantiatedException e) {
            showStatus(e.getLocalizedMessage());
        }
    }

    @Override
    public void visualizeAntennaBalls(String JSONtext) {
        Synchronizer.getInstance().solicitar();
        try {
            IGLFacadeImpl.getInstance().visualizeAntennaBalls(JSONtext);
        } catch (ViewManagerNotInstantiatedException e) {
            showStatus(e.getLocalizedMessage());
        }
    }

    @Override
    public void visualizeTowers(String JSONtext) {
        Synchronizer.getInstance().solicitar();
        try {
            IGLFacadeImpl.getInstance().visualizeTowers(JSONtext);
        } catch (ViewManagerNotInstantiatedException e) {
            showStatus(e.getLocalizedMessage());
        }
    }

    @Override
    public void showMessage(String message) {
        try {
            getAppletContext().showDocument(
                    new URL("javascript:showMessage(\'" + message + "\')"));
        } catch (MalformedURLException e) {
        }
    }

}
