package model.gl.control;

import java.io.IOException;
import java.util.List;

import model.gl.knowledge.GLObject;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactoryViewManager extends GLViewManager {
	private List<GLObject> glFactories;

	public GLFactoryViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException,
			IOException {
		// TODO Auto-generated method stub
		
	}
	
	public static void setupItems() {
		
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		// TODO Auto-generated method stub
		
	}

}
