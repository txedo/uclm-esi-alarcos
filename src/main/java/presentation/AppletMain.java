package presentation;

import java.applet.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import javax.media.opengl.GLAnimatorControl;

import model.IObserverUI;
import model.NotifyUIManager;
import model.business.knowledge.Factory;
import model.gl.GLDrawer;
import model.knowledge.Vector2f;

public class AppletMain extends Applet implements IObserverUI {
	private GLAnimatorControl animator;

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
		canvas.addGLEventListener(new GLDrawer());
		canvas.setSize(getSize());
		add(canvas, BorderLayout.CENTER);
		animator = new FPSAnimator(canvas, 60);
		
		NotifyUIManager.attach(this);
		System.err.println("GearsApplet: init() - end");

	}

	@Override
	public void start() {
		System.err.println("GearsApplet: start() - begin");
		animator.start();
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
	public void updateCompanyList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFactoryList(int idCompany) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMapList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProjectList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClickedWorldCoords(Vector2f coordinates) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectFactoryByLocation(int idLocation) {
		this.handleEvent(EOperationCodes.FactorySelectionByLocation, idLocation);
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
	public void selectFactory(Factory factory) {
		this.handleEvent(EOperationCodes.FactorySelection, factory);
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
				} else if (ob instanceof Factory) {
					Factory f = (Factory)ob;
					getAppletContext().showDocument(new URL("javascript:selectFactory(" + f.getId() + ")"));
				}
				break;
			case ProjectSelection:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectProject(" + id + ")"));
				}
				break;
			case FactorySelectionByLocation:
				if (ob instanceof Integer) {
					int id = (Integer)ob;
					getAppletContext().showDocument(new URL("javascript:selectFactoryByLocation(" + id + ")"));
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
	
	public String js2java (String message) {
		return message + " from js2java";
	}

}
