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
//			IGLFacadeImpl.getInstance().visualizeBuildings("{\"captionLines\":{},\"model\":\"model.gl.knowledge.GLFactory\",\"neighborhoods\":[{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":3,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"smokestackHeight\":3}],\"model\":\"\",\"name\":\"w2p\",\"neighborhoods\":[],\"pavements\":[],\"width\":0},{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":4,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1},{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":2,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.5176471,\"r\":0.16078432},\"smokestackHeight\":1}],\"model\":\"\",\"name\":\"vilma\",\"neighborhoods\":[],\"pavements\":[],\"width\":0},{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":3,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":1,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"smokestackHeight\":2}],\"model\":\"\",\"name\":\"asdf\",\"neighborhoods\":[],\"pavements\":[],\"width\":0},{\"captionLines\":{},\"depth\":0,\"flats\":[{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":3,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"smokestackHeight\":3},{\"color\":{\"alpha\":1,\"b\":1,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0,\"r\":0},\"id\":1,\"maxDepth\":1.25,\"maxHeight\":0,\"maxWidth\":2.5,\"positionX\":0,\"positionZ\":0,\"scale\":1,\"smokestackColor\":{\"alpha\":1,\"b\":0.6039216,\"colorFB\":{\"direct\":false,\"readOnly\":false},\"g\":0.39215687,\"r\":0},\"smokestackHeight\":2}],\"model\":\"\",\"name\":\"desglosa\",\"neighborhoods\":[],\"pavements\":[],\"width\":0}],\"pavements\":[]}");
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
		this.handleEvent(EOperationCodes.ProjectSelection, id, clickButton, clickCount);
	}

	@Override
	public void selectBuilding(int id, int clickButton, int clickCount){
		this.handleEvent(EOperationCodes.FactorySelection, id, clickButton, clickCount);
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
			case FactorySelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectBuilding(" + id + "," + clickButton + "," + clickCount +")"));
				}
				break;
			case ProjectSelection:
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
