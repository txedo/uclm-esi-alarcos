package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;
import model.gl.knowledge.AntennaBall;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;
import model.knowledge.Color;
import model.knowledge.Vector3f;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLProjectViewManager extends GLViewManager {
	private final String APPLY = "textures/gtk-apply.png";
	private final String CANCEL = "textures/gtk-cancel.png";
	
	private GLUquadric quadric;
//	private TextureLoader textureLoader;
	private static List<GLObject> antennaBalls;

	public GLProjectViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		antennaBalls = new ArrayList<GLObject>();
//		textureLoader = new TextureLoader(new String[] {APPLY, CANCEL});
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
//		try {
//			if (!textureLoader.isTexturesLoaded()) textureLoader.loadTexures(true, true, true);
		
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
//				((AntennaBall)ball).setTextures(textureLoader.getTextureNames());
			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGLU().gluDeleteQuadric(quadric);
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException,
			IOException {
		if (this.isSelectionMode()) this.selectItem();
//		super.drawFloor();
		GLSingleton.getGL().glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_DECAL);
		this.drawItems();
	}
	
	public static void setupItems() {
		AntennaBall ab = new AntennaBall(1.0f, 1.0f);
		ab.setLabel("Proyecto 1");
		ab.setParentBallRadius(2.0f);
		ab.setLeftChildBallValue(12);
		ab.setRightChildBallValue(21);
		antennaBalls.add(ab);
		ab = new AntennaBall(5.0f, 5.0f);
		ab.setLeftChildBallValue(45);
		ab.setRightChildBallValue(54);
		ab.setProgression(false);
		ab.setColor(new Color(0.8f, 0.8f, 0.8f));
		ab.setLabel("Proyecto 2");
		antennaBalls.add(ab);
		ab = new AntennaBall(7.0f, 8.0f);
		ab.setParentBallRadius(1.25f);
		ab.setLeftChildBallValue(23);
		ab.setRightChildBallValue(52);
		ab.setProgression(true);
		ab.setColor(new Color(0.3f, 0.5f, 0.1f));
		ab.setLabel("Proyecto 3");
		antennaBalls.add(ab);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject glo : antennaBalls) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			if (this.drawingShadows) ((GLObject3D)glo).drawShadow();
			else {
				((GLObject3D)glo).draw();
				// Write the project label
				Vector3f cameraViewDir = this.drawer.getCamera().getViewDir().clone();
				cameraViewDir.setY(0.0f);
				cameraViewDir.normalize();
				Vector3f normalizedOppositeCameraViewDir = cameraViewDir.mult(-1);
				Vector3f objectPosition = new Vector3f(glo.getPositionX(), 0.0f, glo.getPositionZ());
				float offset = ((AntennaBall)glo).getParentBallRadius();
				Vector3f offsetPosition = objectPosition.add(normalizedOppositeCameraViewDir.mult(offset));
				GLUtils.renderBitmapString(offsetPosition.getX(), 0.1f, offsetPosition.getZ(), 5, ((AntennaBall)glo).getLabel());
			}
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		System.err.println("Selected project: " + selectedObject);
		NotifyUIManager.notifySelectedProject(selectedObject);
	}

}
