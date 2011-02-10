package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import model.NotifyUIManager;
import model.business.control.MapManager;
import model.business.knowledge.Location;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.MapLocation;
import model.knowledge.Vector2f;
import model.knowledge.Vector3f;
import exceptions.gl.GLSingletonNotInitializedException;


public class GLMapLocationViewManager extends GLViewManager {
	private static TextureLoader textureMapLoader;
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
	}
	
	@Override
	public void manageView() throws GLSingletonNotInitializedException, IOException {
		// This state machine prevents GL trying to load a texture while it is loading from disk to memory
		if (hasTextureMapChanged) {
			isTextureMapReady = false;
			setupItems();
			textureMapLoader.loadTexures();
			hasTextureMapChanged = false;
			isTextureMapReady = true;
		}
		if (isTextureMapReady) {
			float h = 1, w = 1;
			float textureH = (float)textureMapLoader.getTextures()[0].getHeight();
			float textureW = (float)textureMapLoader.getTextures()[0].getWidth();
			if (textureW > textureH) h = textureH / textureW;
			else if (textureW < textureH) w = textureW / textureH;
			
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			GLSingleton.getGL().glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
			GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, textureMapLoader.getTextureNames()[0]);
			GLSingleton.getGL().glBegin(GL.GL_QUADS);            // Draw Our First Texture Mapped Quad
				GLSingleton.getGL().glTexCoord2d(0.0f, 0.0f);        // First Texture Coord
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);          	// First Vertex
				GLSingleton.getGL().glTexCoord2d(1.0f, 0.0f);        // Second Texture Coord
				GLSingleton.getGL().glVertex2f(this.drawer.getDim()*w, 0.0f);         // Second Vertex
				GLSingleton.getGL().glTexCoord2d(1.0f, 1.0f);        // Third Texture Coord
				GLSingleton.getGL().glVertex2f(this.drawer.getDim()*w, this.drawer.getDim()*h);   // Third Vertex
				GLSingleton.getGL().glTexCoord2d(0.0f, 1.0f);        // Fourth Texture Coord
				GLSingleton.getGL().glVertex2f(0.0f, this.drawer.getDim()*h);       // Fourth Vertex
			GLSingleton.getGL().glEnd();   // Done Drawing The First Quad
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			
			// Bind the texture to null to avoid color issues
			//GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, 0);
			
			if (this.selectionMode) {
				GLUtils.debugPrintCoords((int)this.drawer.getPickPoint().getX(), (int)drawer.getPickPoint().getY());
				Vector3f v = GLUtils.getScreen2World((int)drawer.getPickPoint().getX(), (int)drawer.getPickPoint().getY(), false);
				NotifyUIManager.notifyClickedWorldCoords(new Vector2f(v.getX(), v.getY()));
				this.selectItem();
				selectionMode = false;
			}
			
			// Place the map locations
			this.drawItems();
		}
	}
	
	public static void updateMapChanged() throws GLSingletonNotInitializedException, IOException {
		GLMapLocationViewManager.textureMapLoader = new TextureLoader (new String[]{MapManager.getActiveMap().getImage().getFilename()});
		GLMapLocationViewManager.hasTextureMapChanged = true;
	}

	public static void setupItems() {
		mapLocations = new ArrayList<GLObject>();
		GLAbstractFactory glFactory = new JOGLFactory();
		for (Location loc : locations) {
			if (loc != null) {
				GLObject temp = glFactory.createMapLocation(loc.getId(), loc.getXcoord(), loc.getYcoord());
				mapLocations.add(temp);	
			}
		}
		// Change the picking region to manage selection work properly
		if (mapLocations.size() != 0) setPickingRegion(((MapLocation)mapLocations.get(0)).getSize());
		//System.out.println("param: " + locations.size() + "\tstatic: " + GLMapLocationViewController.locations.size());
		// TODO revisar el acceso a campos estaticos
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		for (GLObject glo : mapLocations) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(((MapLocation)glo).getId());
			glo.draw();
		}
	}
	
	@Override
	protected void selectedObjectHandler(int selectedObject) {
		NotifyUIManager.notifySelectedLocation(selectedObject);
	}
	
	public static void addMapLocations (List<Location> locs) {
		locations = new ArrayList<Location>();
		locations.addAll(locs);
		GLMapLocationViewManager.setupItems();
	}

	public static void highlightMapLocations(List<Location> locs) {
		// Set all locaiton highlighting to false
		for (GLObject mapLoc : mapLocations) {
			((MapLocation)mapLoc).setHightlighted(false);
		}
		// Highlight selected locations
		boolean found = false;
		for (Location loc : locs) {
			for (int i = 0; i < mapLocations.size() && !found; i++) {
				if (loc.getId() == ((MapLocation)mapLocations.get(i)).getId()) {
					((MapLocation)mapLocations.get(i)).setHightlighted(true);
					found = true;
				}
			}
			found = false;
		}
	}

}
