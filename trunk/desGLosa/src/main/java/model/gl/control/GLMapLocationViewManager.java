package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import model.NotifyUIManager;
import model.business.control.MapManager;
import model.business.knowledge.Location;
import model.gl.GLAbstractFactory;
import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.JOGLFactory;
import model.gl.TextureLoader;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.MapLocation;
import model.knowledge.Vector2f;
import model.knowledge.Vector3f;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLMapLocationViewManager extends GLViewManager {
	private static TextureLoader textureMapLoader;
	private static TextureLoader textureLoader;
	private static boolean hasTextureMapChanged;
	private boolean isTextureMapReady;

	private static List<Location> locations;
	private static List<GLObject> mapLocations;

	public GLMapLocationViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		GLMapLocationViewManager.hasTextureMapChanged = false;
		this.isTextureMapReady = false;
		locations = new ArrayList<Location>();
		mapLocations = new ArrayList<GLObject>();
		textureLoader = new TextureLoader(new String[]{"src/main/resources/gl/round-highlight.png"});
	}

	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glDisable(GL.GL_LIGHTING);
		try {
			if (!textureLoader.isTexturesLoaded()) textureLoader.loadTexures(true, true, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException,
			IOException {
		// This state machine prevents GL trying to load a texture while it is
		// loading from disk to memory
		if (hasTextureMapChanged) {
			isTextureMapReady = false;
			setupItems();
			textureMapLoader.loadTexures(true, true, true);
			hasTextureMapChanged = false;
			isTextureMapReady = true;
		}
		if (isTextureMapReady) {
			float h = 1, w = 1;
			float textureH = (float) textureMapLoader.getTextures()[0].getHeight();
			float textureW = (float) textureMapLoader.getTextures()[0].getWidth();
			if (textureW > textureH) h = textureH / textureW;
			else if (textureW <= textureH) w = textureW / textureH;
			final float dim = this.drawer.getDim();

			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D); // Enable 2D Texture Mapping
			GLSingleton.getGL().glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
			GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D,	textureMapLoader.getTextureNames()[0]);
			GLSingleton.getGL().glColor3f(1.0f, 1.0f, 1.0f);
			GLSingleton.getGL().glBegin(GL.GL_QUADS);
				GLSingleton.getGL().glNormal3f(0.0f, 0.0f, 1.0f);
				GLSingleton.getGL().glTexCoord2f(0.0f, 0.0f); GLSingleton.getGL().glVertex2f(0.0f,  0.0f);
				GLSingleton.getGL().glTexCoord2f(1.0f, 0.0f); GLSingleton.getGL().glVertex2f(dim*w, 0.0f);
				GLSingleton.getGL().glTexCoord2f(1.0f, 1.0f); GLSingleton.getGL().glVertex2f(dim*w, dim*h);
				GLSingleton.getGL().glTexCoord2f(0.0f, 1.0f); GLSingleton.getGL().glVertex2f(0.0f,  dim*h);
			GLSingleton.getGL().glEnd();
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D); // Disable 2D Texture Mapping

			// Bind the texture to null to avoid color issues
			// GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, 0);

			if (this.selectionMode) {
				GLUtils.debugPrintCoords((int) this.drawer.getPickPoint()
						.getX(), (int) drawer.getPickPoint().getY());
				Vector3f v = GLUtils.getScreen2World((int) drawer
						.getPickPoint().getX(), (int) drawer.getPickPoint()
						.getY(), false);
				NotifyUIManager.notifyClickedWorldCoords(new Vector2f(v.getX(),
						v.getY()));
				this.selectItem();
				selectionMode = false;
			}

			// Place the map locations
			this.drawItems();
		}
	}

	public static void updateMapChanged()
			throws GLSingletonNotInitializedException, IOException {
		GLMapLocationViewManager.textureMapLoader = new TextureLoader(
				new String[] { MapManager.getActiveMap().getImage()
						.getFilename() });
		GLMapLocationViewManager.hasTextureMapChanged = true;
	}

	public static void setupItems() {
		mapLocations = new ArrayList<GLObject>();
		GLAbstractFactory glFactory = new JOGLFactory();
		for (Location loc : locations) {
			if (loc != null) {
				GLObject temp = glFactory.createMapLocation(loc.getId(),
						loc.getXcoord(), loc.getYcoord());
				mapLocations.add(temp);
			}
		}
		// Change the picking region to manage selection work properly
		if (mapLocations.size() != 0)
			setPickingRegion(((MapLocation) mapLocations.get(0)).getSize());
		// System.out.println("param: " + locations.size() + "\tstatic: " +
		// GLMapLocationViewController.locations.size());
		// TODO revisar el acceso a campos estaticos
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		/*
		 * We draw the map locations in two passes: first pass draws
		 * no-highlighted nodes and second pass draws highlighted ones. By this
		 * way, highlighted nodes are drawed over no-highlighted nodes.
		 */
		for (GLObject glo : mapLocations) {
			if (!((MapLocation) glo).isHightlighted()) {
				if (selectionMode)
					GLSingleton.getGL().glLoadName(((MapLocation) glo).getId());
				glo.draw();
			}
		}
		for (GLObject glo : mapLocations) {
			if (((MapLocation) glo).isHightlighted()) {
				if (selectionMode)
					GLSingleton.getGL().glLoadName(((MapLocation) glo).getId());
				glo.draw();
			}
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		NotifyUIManager.notifySelectedLocation(selectedObject);
	}

	public static void addMapLocations(List<Location> locs) {
		locations = new ArrayList<Location>();
		locations.addAll(locs);
		GLMapLocationViewManager.setupItems();
	}

	public static void highlightMapLocations(List<Location> locs) {
		// Set all location highlighting to false
		for (GLObject mapLoc : mapLocations) {
			((MapLocation) mapLoc).setHightlighted(false);
			((MapLocation) mapLoc).setFaded(true);
		}
		// Highlight selected locations
		boolean found = false;
		for (Location loc : locs) {
			for (int i = 0; i < mapLocations.size() && !found; i++) {
				if (loc.getId() == ((MapLocation) mapLocations.get(i)).getId()) {
					((MapLocation) mapLocations.get(i)).setHightlighted(true);
					found = true;
				}
			}
			found = false;
		}
	}

}
