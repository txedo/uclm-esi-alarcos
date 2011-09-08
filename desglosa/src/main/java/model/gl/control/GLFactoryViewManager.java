package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.TextureLoader;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;

import exceptions.GLSingletonNotInitializedException;
import exceptions.ViewManagerNotInstantiatedException;

public class GLFactoryViewManager extends GLViewManager {
	
	static private GLFactoryViewManager _instance = null;
	
	/**
	 * @return The unique instance of this class.
	 * @throws ViewManagerNotInstantiatedException 
	 */
	static public GLFactoryViewManager getInstance() throws ViewManagerNotInstantiatedException {
		if (null == _instance) {
			throw new ViewManagerNotInstantiatedException();
		}
		return _instance;
	}
	
	private List<GLObject> glFactories;
	private TextureLoader textureLoader;
	private GLUquadric quadric;
	
	private final String FACTORY_TEXTURE = "textures/factory-texture.png";

	public GLFactoryViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		glFactories = new ArrayList<GLObject>();
		textureLoader = new TextureLoader(new String[] {FACTORY_TEXTURE});
		_instance = this;
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		try {
			if (!textureLoader.isTexturesLoaded()) textureLoader.loadTexures(true, true, true, false);

			// Create A New Quadratic
			this.quadric = GLSingleton.getGLU().gluNewQuadric();
			// Generate Smooth Normals For The Quadric
			GLSingleton.getGLU().gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
			// Enable Texture Coords For The Quadric
			GLSingleton.getGLU().gluQuadricDrawStyle( quadric, GLU.GLU_FILL);
			GLSingleton.getGLU().gluQuadricOrientation( quadric, GLU.GLU_OUTSIDE);
			
			for (GLObject factory : glFactories) {
				((GLFactory)factory).setGLUQuadric(quadric);
				((GLFactory)factory).setTexture(textureLoader.getTextureNames()[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		super.drawSkybox();
		super.drawFloor();
		super.drawPavements();
		this.drawItems();
		super.drawCaption();
	}
	
	@Override
	public void setItems(List objs) {
		glFactories = new ArrayList<GLObject>();
		glFactories.addAll(objs);
	}
	
	@Override
	public void addItems(List objs) {
		if (glFactories == null) glFactories = new ArrayList<GLObject>();
		glFactories.addAll(objs);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject glo : glFactories) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			if (this.drawingShadows) ((GLObject3D)glo).drawShadow();
			else ((GLObject3D)glo).draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		int GLObjectId = glFactories.get(selectedObject-1).getId();
		System.err.println("Selected factory: " + GLObjectId + "\tNumber of clicks: " + clickCount);
		NotifyUIManager.notifySelectedFactory(GLObjectId, clickCount);
	}


}
