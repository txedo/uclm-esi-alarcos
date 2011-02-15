package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactoryViewManager extends GLViewManager {
	private static List<GLObject> glFactories;

	public GLFactoryViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		glFactories = new ArrayList<GLObject>();
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException,
			IOException {
		super.drawFloor();
		this.drawItems();
		GLSingleton.getGL().glColor3f(0.0f, 0.0f, 0.0f);
		GLUtils.renderBitmapString(0, 0, 0, 2, "asdfasdfasdfasdfasdf");
	}
	
	public static void setupItems() {
		glFactories = new ArrayList<GLObject>();
		GLFactory glf = new GLFactory(5.0f, 5.0f);
		glFactories.add(glf);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		for (GLObject glo : glFactories) {
			glo.draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		// TODO Auto-generated method stub
		
	}

}
