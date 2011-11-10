package presentation;

import java.applet.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

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
	private GLAnimatorControl animator;
	private GLDrawer drawer = null;

	@Override
	public void init() {
		System.err.println("GearsApplet: init() - begin");
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
		
		System.err.println("GearsApplet: init() - end");
	}
	
	@Override
	public void start() {
		System.err.println("GearsApplet: start() - begin");
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			IGLFacadeImpl.getInstance().visualizeTowers("{\"captionLines\":{\"Línea informativa\":\"00649a\",\"Otra línea informativa\":\"2a8400\",\"Y otra más\":\"ff8c00\"},\"model\":\"model.gl.knowledge.GLTower\",\"neighborhoods\":[{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"depth\":1,\"height\":6.9,\"id\":4,\"innerHeight\":0.0,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2},{\"color\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"depth\":1.5,\"height\":5.4,\"id\":1,\"innerHeight\":8.6,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2.1},{\"color\":{\"alpha\":1,\"b\":0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"depth\":1.3,\"height\":6,\"id\":2,\"innerHeight\":4,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":3},{\"color\":{\"alpha\":1,\"b\":0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.54901963,\"r\":1},\"depth\":0.9,\"height\":9.8,\"id\":3,\"innerHeight\":2,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2.6}],\"model\":\"\",\"name\":\"Paquete 1\",\"neighborhoods\":[],\"pavements\":[],\"width\":0},{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":0.73725,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.2745,\"r\":0.89},\"depth\":2.7,\"height\":6.9,\"id\":12,\"innerHeight\":5.8,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2.3},{\"color\":{\"alpha\":1,\"b\":0.6,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.6,\"r\":0.6},\"depth\":1.9,\"height\":9,\"id\":10,\"innerHeight\":8,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":3},{\"color\":{\"alpha\":1,\"b\":0.3,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.3,\"r\":0.3},\"depth\":2.5,\"height\":2.9,\"id\":8,\"innerHeight\":2,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":3}],\"model\":\"\",\"name\":\"Paquete 2\",\"neighborhoods\":[],\"pavements\":[],\"width\":0},{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":0.60,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39,\"r\":0},\"depth\":3,\"height\":0.6,\"id\":6,\"innerHeight\":0.3,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2.5},{\"color\":{\"alpha\":1,\"b\":0.3244,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"depth\":0.3,\"height\":5,\"id\":5,\"innerHeight\":4,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":1},{\"color\":{\"alpha\":1,\"b\":0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.6345,\"r\":0.1234},\"depth\":1.0,\"height\":1.3,\"id\":7,\"innerHeight\":1.3,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":3},{\"color\":{\"alpha\":1,\"b\":0.43243,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5234471,\"r\":0.4323},\"depth\":2,\"height\":6,\"id\":9,\"innerHeight\":5.6,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2},{\"color\":{\"alpha\":1,\"b\":0.4124,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"depth\":2,\"height\":3.1,\"id\":11,\"innerHeight\":3.1,\"maxDepth\":3,\"maxHeight\":12,\"maxWidth\":3,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"width\":2}],\"model\":\"\",\"name\":\"Paquete 3\",\"neighborhoods\":[],\"pavements\":[],\"width\":0}],\"pavements\":[]}");
//		} catch (ViewManagerNotInstantiatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.err.println("GearsApplet: start() - end");
	}

	@Override
	public void stop() {
		System.err.println("GearsApplet: stop() - begin");
		animator.stop();
		System.err.println("GearsApplet: stop() - end");
	}

	public void destroy() {
		System.err.println("GearsApplet: destroy() - X");
	}

	@Override
	public void selectAntennaBall(int id, int clickButton, int clickCount){
		this.handleEvent(EOperationCodes.AntennaBallSelection, id, clickButton, clickCount);
	}

	@Override
	public void selectBuilding(int id, int clickButton, int clickCount){
		this.handleEvent(EOperationCodes.BuildingSelection, id, clickButton, clickCount);
	}

	@Override
	public void selectTower(int id, int clickButton, int clickCount){
		this.handleEvent(EOperationCodes.TowerSelection, id, clickButton, clickCount);
	}
	
	private void handleEvent (EOperationCodes opCode, Object ob, int clickButton, int clickCount) {
		try {
			switch (opCode) {
			case TowerSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectTower(" + id + "," + clickButton + "," + clickCount +")"));
				}
				break;
			case BuildingSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectBuilding(" + id + "," + clickButton + "," + clickCount +")"));
				}
				break;
			case AntennaBallSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectAntennaBall(" + id + "," + clickButton + "," + clickCount +")"));
				}
				break;
			default:
				getAppletContext().showDocument(new URL("javascript:selectionError(\'Undefined operation\')"));
				break;
			}
		} catch (MalformedURLException e) {
			try {
				getAppletContext().showDocument(new URL("javascript:selectionError(\'" + e.toString() + "\')"));
			} catch (MalformedURLException e1) {}
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
			getAppletContext().showDocument(new URL("javascript:showMessage(\'" + message + "\')"));
		} catch (MalformedURLException e) {}
	}
	
}
