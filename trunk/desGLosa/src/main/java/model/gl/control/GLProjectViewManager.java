package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.TextureLoader;
import model.gl.knowledge.AntennaBall;
import model.gl.knowledge.GLObject;
import model.knowledge.Color;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLProjectViewManager extends GLViewManager {
	private final String TEXTURE_PATH = "src/main/resources/gl/";
	private final String APPLY = "gtk-apply.png";
	private final String CANCEL = "gtk-cancel.png";
	
	private GLUquadric quadric;
	private TextureLoader textureLoader;
	private static List<GLObject> antennaBalls;

	public GLProjectViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		antennaBalls = new ArrayList<GLObject>();
		textureLoader = new TextureLoader(new String[] {TEXTURE_PATH+APPLY, TEXTURE_PATH+CANCEL});
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		try {
			if (!textureLoader.isTexturesLoaded()) textureLoader.loadTexures(true, true, true);
		
			// Create A New Quadratic
			this.quadric = GLSingleton.getGLU().gluNewQuadric();
			// Generate Smooth Normals For The Quad
			GLSingleton.getGLU().gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
			// Enable Texture Coords For The Quad
			GLSingleton.getGLU().gluQuadricTexture(quadric, true);
			GLSingleton.getGLU().gluQuadricDrawStyle( quadric, GLU.GLU_FILL);
			GLSingleton.getGLU().gluQuadricOrientation( quadric, GLU.GLU_OUTSIDE);
			
			for (GLObject ball : antennaBalls) {
				((AntennaBall)ball).setQuadric(quadric);
				((AntennaBall)ball).setTextures(textureLoader.getTextureNames());
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
		GLSingleton.getGL().glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
		this.drawItems();
	}
	
	public static void setupItems() {
		AntennaBall ab = new AntennaBall(1.0f, 1.0f);
		ab.setLabel("projecto 1");
		ab.setParentBallRadius(2.0f);
		antennaBalls.add(ab);
		ab = new AntennaBall(5.0f, 5.0f);
		ab.setProgression(false);
		ab.setColor(new Color(0.8f, 0.8f, 0.8f));
		ab.setLabel("projecto 2");
		antennaBalls.add(ab);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject ball : antennaBalls) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			ball.draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		// TODO Auto-generated method stub
		System.err.println("Selected project: " + selectedObject);
	}

}
