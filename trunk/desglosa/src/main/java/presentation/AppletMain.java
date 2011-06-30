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

import model.IObserverUI;
import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.IGLFacade;
import model.gl.IGLFacadeImpl;
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
		
		try {
			Synchronizer.getInstance().solicitar();
			IGLFacadeImpl.getInstance().visualizeTowers("{\"neighborhoods\":[{\"flats\":[{\"width\":2.6925,\"height\":11.9676,\"depth\":2.529,\"color\":\"ff0000\",\"fill\":10.492799999999999,\"id\":1},{\"width\":2.2362,\"height\":11.694,\"depth\":2.3505,\"color\":\"00ff00\",\"fill\":4.9452,\"id\":4}]}]}");
		} catch (ViewManagerNotInstantiatedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			IGLFacadeImpl.getInstance().visualizeProjects("{\"neighborhoods\":[" +
//					"{\"flats\":[" +
//						"{\"id\":1,\"name\":\"desglosa\",\"audited\":true,\"size\":7,\"totalIncidences\":500,\"repairedIncidences\":100,\"market\":\"Arquitectura\",\"color\":\"00649a\"}," +
//						"{\"id\":2,\"name\":\"vilma\",\"audited\":false,\"size\":3,\"totalIncidences\":10,\"repairedIncidences\":2,\"market\":\"Defensa\",\"color\":\"298400\"}," +
//						"{\"id\":3,\"name\":\"w2p\",\"audited\":true,\"size\":4,\"totalIncidences\":60,\"repairedIncidences\":21,\"market\":\"Sanitario\",\"color\":\"ff8c00\"}" +
//					"]}" +
//				"]}");
//		} catch (ViewManagerNotInstantiatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			IGLFacadeImpl.getInstance().visualizeFactories("{\"neighborhoods\":[" +
//					"{\"flats\":[" +
//						"{\"id\":1,\"employees\":50,\"projects\":1}," +
//						"{\"id\":2,\"employees\":160,\"projects\":2}," +
//						"{\"id\":3,\"employees\":560,\"projects\":3}," +
//						"{\"id\":4,\"employees\":10,\"projects\":4}," +
//						"{\"id\":5,\"employees\":560,\"projects\":5}," +
//						"{\"id\":6,\"employees\":10,\"projects\":6}" +
//					"]}," +
//					"{\"flats\":[" +
//						"{\"id\":7,\"employees\":50,\"projects\":7}," +
//						"{\"id\":8,\"employees\":160,\"projects\":8}," +
//						"{\"id\":9,\"employees\":560,\"projects\":9}," +
//						"{\"id\":10,\"employees\":10,\"projects\":10}" +
//					"]}," +
//					"{\"flats\":[" +
//					"{\"id\":11,\"employees\":50,\"projects\":11}," +
//					"{\"id\":12,\"employees\":160,\"projects\":12}," +
//					"{\"id\":13,\"employees\":560,\"projects\":13}," +
//					"{\"id\":14,\"employees\":1000,\"projects\":14}" +
//				"]}" +
//				"]}");
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
	public void selectProject(int id, int clickCount){
		this.handleEvent(EOperationCodes.ProjectSelection, id, clickCount);
	}

	@Override
	public void selectFactory(int id, int clickCount){
		this.handleEvent(EOperationCodes.FactorySelection, id, clickCount);
	}

	@Override
	public void selectTower(int id, int clickCount){
		this.handleEvent(EOperationCodes.TowerSelection, id, clickCount);
	}
	
	private void handleEvent (EOperationCodes opCode, Object ob, int clickCount) {
		try {
			switch (opCode) {
			case TowerSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectTower(" + id + "," + clickCount +")"));
				}
				break;
			case FactorySelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectFactory(" + id + "," + clickCount +")"));
				}
				break;
			case ProjectSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectProject(" + id + "," + clickCount +")"));
				}
				break;
			default:
				getAppletContext().showDocument(new URL("javascript:alert(\'Undefined operation\')"));
				break;
			}
		} catch (MalformedURLException e) {
			try {
				getAppletContext().showDocument(new URL("javascript:alert(\'" + e.toString() + "\')"));
			} catch (MalformedURLException e1) {}
		}
	}

	@Override
	public void visualizeFactories(String JSONtext) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeFactories(JSONtext);
	}

	@Override
	public void visualizeProjects(String JSONtext) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeProjects(JSONtext);
	}

	@Override
	public void visualizeTowers(String JSONtext) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeTowers(JSONtext);
	}
	
}
