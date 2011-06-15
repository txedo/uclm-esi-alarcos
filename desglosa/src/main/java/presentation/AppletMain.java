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
import model.Synchronizer;
import model.gl.GLDrawer;
import model.gl.IGLFacade;
import model.gl.IGLFacadeImpl;
import model.gl.control.EViewLevels;

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
	public void selectProject(int id) {
		this.handleEvent(EOperationCodes.ProjectSelection, id);
	}

	@Override
	public void selectFactory(int id) {
		this.handleEvent(EOperationCodes.FactorySelection, id);
	}

	@Override
	public void selectTower(int id) {
		this.handleEvent(EOperationCodes.TowerSelection, id);
	}
	
	private void handleEvent (EOperationCodes opCode, Object ob) {
		try {
			switch (opCode) {
			case TowerSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectTower(" + id + ")"));
				}
				break;
			case FactorySelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectFactory(" + id + ")"));
				}
				break;
			case ProjectSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectProject(" + id + ")"));
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
	public void visualizeFactories(String JSON) throws ViewManagerNotInstantiatedException {
		Synchronizer.getInstance().solicitar();
		IGLFacadeImpl.getInstance().visualizeFactories("[{\"neighborhood\":0,\"id\":3,\"employees\":50,\"projects\":1},{\"neighborhood\":0,\"id\":4,\"employees\":60,\"projects\":1},{\"neighborhood\":0,\"id\":16,\"employees\":111111,\"projects\":1},{\"neighborhood\":0,\"id\":17,\"employees\":111111,\"projects\":1}]");
	}
	
	public void foo () {
		drawer.setViewLevel(EViewLevels.FactoryLevel);
	}
	
}
