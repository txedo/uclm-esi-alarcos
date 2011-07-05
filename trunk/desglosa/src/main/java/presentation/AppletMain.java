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
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			IGLFacadeImpl.getInstance().visualizeTowers("{\"neighborhoods\":[{\"flats\":[{\"width\":2.2362,\"height\":11.694,\"depth\":2.3505,\"color\":\"00ff00\",\"fill\":4.9452,\"id\":1},{\"width\":2.2362,\"height\":11.694,\"depth\":2.3505,\"color\":\"00ff00\",\"fill\":4.9452,\"id\":4}]}]}");
//		} catch (ViewManagerNotInstantiatedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			Synchronizer.getInstance().solicitar();
//			IGLFacadeImpl.getInstance().visualizeProjects("{\"neighborhoods\":[{\"flats\":[{\"audited\":true,\"code\":\"DGL\",\"delayed\":false,\"id\":1,\"mainFactory\":{\"address\":{\"address\":\"calle\",\"city\":\"Ciudad Real\",\"country\":\"Espa�a\",\"id\":1,\"postalCode\":\"13000\",\"province\":\"Ciudad Real\"},\"company\":{\"director\":{\"id\":5,\"imagePath\":\"images/anonymous.gif\",\"lastName\":\"global de todo\",\"name\":\"director de indra\"},\"id\":1,\"information\":\"information about Indra\",\"name\":\"Indra\"},\"director\":{\"id\":1,\"imagePath\":\"images/anonymous.gif\",\"lastName\":\"de indra\",\"name\":\"director\"},\"email\":\"indra@ciudadreal.es\",\"employees\":25,\"id\":1,\"information\":\"information about Indra Ciudad Real\",\"location\":{\"id\":1,\"latitude\":38.9932,\"longitude\":-3.92495},\"name\":\"Indra Ciudad Real\"},\"market\":{\"color\":\"00649a\",\"id\":1,\"name\":\"Arquitectura\"},\"name\":\"desglosa\",\"plan\":\"DESGLOSA\",\"profile\":{\"color\":{\"acceptable\":\"ff0000\",\"nonAcceptable\":\"00ff00\",\"peripheral\":\"0000ff\"},\"name\":\"default\",\"views\":[{\"chart\":{\"id\":1,\"maxCols\":5,\"name\":\"towers\",\"type\":\"3D\"},\"chartName\":\"towers\",\"dimensions\":[{\"attr\":\"width\",\"csvCol\":\"1\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de fiabilidad\",\"high\":100,\"id\":1,\"key\":\"fia\",\"low\":0,\"medium\":50,\"name\":\"Fiabilidad\"},\"measureKey\":\"fia\",\"type\":\"numeric\",\"value\":74.54},{\"attr\":\"height\",\"csvCol\":\"2\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de usabilidad\",\"high\":100,\"id\":2,\"key\":\"usa\",\"low\":0,\"medium\":50,\"name\":\"Usabiildad\"},\"measureKey\":\"usa\",\"type\":\"numeric\",\"value\":97.45},{\"attr\":\"depth\",\"csvCol\":\"3\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de eficiencia\",\"high\":100,\"id\":3,\"key\":\"efi\",\"low\":0,\"medium\":50,\"name\":\"Eficiencia\"},\"measureKey\":\"efi\",\"type\":\"numeric\",\"value\":78.35},{\"attr\":\"color\",\"csvCol\":\"4\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de mantenibilidad\",\"high\":100,\"id\":4,\"key\":\"mant\",\"low\":0,\"medium\":50,\"name\":\"Mantenibilidad\"},\"measureKey\":\"mant\",\"type\":\"numeric\",\"value\":43.1},{\"attr\":\"fill\",\"csvCol\":\"5\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de portabilidad\",\"high\":100,\"id\":5,\"key\":\"port\",\"low\":0,\"medium\":50,\"name\":\"Portabilidad\"},\"measureKey\":\"port\",\"type\":\"numeric\",\"value\":41.21}],\"id\":1,\"level\":1,\"name\":\"basic characteristics\"},{\"chart\":{\"id\":1,\"maxCols\":5,\"name\":\"towers\",\"type\":\"3D\"},\"chartName\":\"towers\",\"dimensions\":[{\"attr\":\"width\",\"csvCol\":\"6\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de puntos funci�n\",\"high\":100,\"id\":8,\"key\":\"pfunc\",\"low\":0,\"medium\":50,\"name\":\"Puntos funcion\"},\"measureKey\":\"pfunc\",\"type\":\"numeric\",\"value\":1234},{\"attr\":\"height\",\"csvCol\":\"7\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de lcd\",\"high\":100,\"id\":6,\"key\":\"lcd\",\"low\":0,\"medium\":50,\"name\":\"Lineas de codigo\"},\"measureKey\":\"lcd\",\"type\":\"numeric\",\"value\":124123},{\"attr\":\"depth\",\"csvCol\":\"8\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de comentarios\",\"high\":100,\"id\":7,\"key\":\"comm\",\"low\":0,\"medium\":50,\"name\":\"Comentarios\"},\"measureKey\":\"comm\",\"type\":\"numeric\",\"value\":5123},{\"attr\":\"color\",\"csvCol\":\"9\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de actividad\",\"high\":100,\"id\":11,\"key\":\"act\",\"low\":0,\"medium\":50,\"name\":\"Actividad\"},\"measureKey\":\"act\",\"type\":\"numeric\",\"value\":85},{\"attr\":\"fill\",\"csvCol\":\"10,11\",\"description\":null,\"measure\":{\"description\":\"Descripci�n de fich. total\",\"high\":100,\"id\":10,\"key\":\"ftot\",\"low\":0,\"medium\":50,\"name\":\"Fich. total\"},\"measureKey\":\"ftot\",\"type\":\"percent\",\"value\":8.730720249044856}],\"id\":2,\"level\":2,\"name\":\"actividad\"}]},\"profileName\":\"default-project-profile.xml\",\"repairedIncidences\":100,\"size\":7,\"subprojects\":[{\"csvData\":\"89.75;99.73;84.3;76.17;87.44;305789;4125987;547;89;5997;13425\",\"factory\":{\"address\":{\"address\":\"calle\",\"city\":\"Ciudad Real\",\"country\":\"Espa�a\",\"id\":1,\"postalCode\":\"13000\",\"province\":\"Ciudad Real\"},\"company\":{\"director\":{\"id\":5,\"imagePath\":\"images/anonymous.gif\",\"lastName\":\"global de todo\",\"name\":\"director de indra\"},\"id\":1,\"information\":\"information about Indra\",\"name\":\"Indra\"},\"director\":{\"id\":1,\"imagePath\":\"images/anonymous.gif\",\"lastName\":\"de indra\",\"name\":\"director\"},\"email\":\"indra@ciudadreal.es\",\"employees\":25,\"id\":1,\"information\":\"information about Indra Ciudad Real\",\"location\":{\"id\":1,\"latitude\":38.9932,\"longitude\":-3.92495},\"name\":\"Indra Ciudad Real\"},\"id\":1,\"name\":\"fase 1\",\"project\":null},{\"csvData\":\"74.54;97.45;78.35;43.1;41.21;1234;124123;5123;85;1234;14134\",\"factory\":{\"address\":{\"address\":\"calle\",\"city\":\"Miguelturra\",\"country\":\"Espa�a\",\"id\":3,\"postalCode\":\"13000\",\"province\":\"Ciudad Real\"},\"company\":{\"director\":{\"id\":6,\"imagePath\":\"images/anonymous.gif\",\"lastName\":\"global de todoooo\",\"name\":\"director de iecisa\"},\"id\":2,\"information\":\"informaci�n sobre IECISA\",\"name\":\"IECISA\"},\"director\":{\"id\":3,\"imagePath\":\"images/anonymous.gif\",\"lastName\":\"de compa�ia de ejemplo 1\",\"name\":\"director\"},\"email\":\"factory@miguelturra.es\",\"employees\":50,\"id\":3,\"information\":\"information about a factory in Miguelturra\",\"location\":{\"id\":3,\"latitude\":38.9583,\"longitude\":-3.88307},\"name\":\"Factor�a en Miguelturra\"},\"id\":4,\"name\":\"fase 2\",\"project\":null}],\"totalIncidences\":500}]}]}");
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