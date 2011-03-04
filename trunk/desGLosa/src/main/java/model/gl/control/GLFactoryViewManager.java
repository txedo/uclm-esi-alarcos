package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.TextureLoader;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLFactoryViewManager extends GLViewManager {
	private static List<GLObject> glFactories;
	private TextureLoader textureLoader;
	private GLUquadric quadric;
	
	private final String FACTORY_TEXTURE = "src/main/resources/gl/factory-texture.png";

	public GLFactoryViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		glFactories = new ArrayList<GLObject>();
		textureLoader = new TextureLoader(new String[] {FACTORY_TEXTURE});
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		try {
			if (!textureLoader.isTexturesLoaded()) textureLoader.loadTexures(true, true, true);

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
		super.drawFloor();
		this.drawItems();
	}
	
	public static void setupItems() {
		glFactories = new ArrayList<GLObject>();
		GLFactory glf = new GLFactory(5.0f, 5.0f);
		glf.setSmokestackHeight(10);
		glFactories.add(glf);
		glf = new GLFactory(8.0f, 2.0f);
		glf.setSmokestackHeight(8);
		glFactories.add(glf);
		glf = new GLFactory(1.0f, 3.0f);
		glFactories.add(glf);
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
		// TODO Auto-generated method stub
		System.err.println("Selected factory: " + selectedObject);
	}

}
