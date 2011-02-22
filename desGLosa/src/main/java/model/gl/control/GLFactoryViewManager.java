package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.AntennaBall;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactoryViewManager extends GLViewManager {
	private static List<GLObject> glFactories;
	private GLUquadric quadric;

	public GLFactoryViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		glFactories = new ArrayList<GLObject>();
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		// Create A New Quadratic
		this.quadric = GLSingleton.getGLU().gluNewQuadric();
		// Generate Smooth Normals For The Quadric
		GLSingleton.getGLU().gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
		// Enable Texture Coords For The Quadric
		GLSingleton.getGLU().gluQuadricDrawStyle( quadric, GLU.GLU_FILL);
		GLSingleton.getGLU().gluQuadricOrientation( quadric, GLU.GLU_OUTSIDE);
		
		for (GLObject factory : glFactories) {
			((GLFactory)factory).setGLUQuadric(quadric);
		}
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGLU().gluDeleteQuadric(quadric);
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException,
			IOException {
		if (this.isSelectionMode()) this.selectItem();
		super.drawFloor();
		this.drawItems();
	}
	
	public static void setupItems() {
		glFactories = new ArrayList<GLObject>();
		GLFactory glf = new GLFactory(5.0f, 5.0f);
		glFactories.add(glf);
		glf = new GLFactory(1.0f, 1.0f);
		glf.setSmokestackHeight(10);
		glFactories.add(glf);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject glo : glFactories) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			glo.draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		// TODO Auto-generated method stub
		System.err.println("Selected factory: " + selectedObject);
	}

}
