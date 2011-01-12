package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import model.NotifyUIController;
import model.business.control.MapController;
import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.TextureLoader;
import model.knowledge.Vector2f;
import model.knowledge.Vector3f;
import exceptions.gl.GLSingletonNotInitializedException;


public class GLMapLocationViewController extends GLViewController {
	private static TextureLoader textureMapLoader;
	private static boolean hasTextureMapChanged;
	private boolean isTextureMapReady;
	
	private static List<Vector2f> locations;
	private List<GLObject> mapLocations;

	public GLMapLocationViewController(GLDrawer d, boolean is3d) {
		super(d, is3d);
		GLMapLocationViewController.hasTextureMapChanged = false;
		this.isTextureMapReady = false;
		locations = new ArrayList<Vector2f>(); 
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
			float h = (float)this.drawer.getScreenHeight()/(float)this.drawer.getScreenWidth();
			GLSingleton.getGL().glEnable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			GLSingleton.getGL().glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
			GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, textureMapLoader.getTextureNames()[0]);
			GLSingleton.getGL().glBegin(GL.GL_QUADS);            // Draw Our First Texture Mapped Quad
				GLSingleton.getGL().glTexCoord2d(0.0f, 0.0f);        // First Texture Coord
				GLSingleton.getGL().glVertex2f(0.0f, 0.0f);          	// First Vertex
				GLSingleton.getGL().glTexCoord2d(1.0f, 0.0f);        // Second Texture Coord
				GLSingleton.getGL().glVertex2f(this.drawer.getDim(), 0.0f);         // Second Vertex
				GLSingleton.getGL().glTexCoord2d(1.0f, 1.0f);        // Third Texture Coord
				GLSingleton.getGL().glVertex2f(this.drawer.getDim(), this.drawer.getDim()*h);   // Third Vertex
				GLSingleton.getGL().glTexCoord2d(0.0f, 1.0f);        // Fourth Texture Coord
				GLSingleton.getGL().glVertex2f(0.0f, this.drawer.getDim()*h);       // Fourth Vertex
			GLSingleton.getGL().glEnd();   // Done Drawing The First Quad
			GLSingleton.getGL().glDisable(GL.GL_TEXTURE_2D);     				// Enable 2D Texture Mapping
			
			// Bind the texture to null to avoid color issues
			//GLSingleton.getGL().glBindTexture(GL.GL_TEXTURE_2D, 0);
			
			if (selectionMode) {
				GLUtils.debugPrintCoords((int)this.drawer.getPickPoint().getX(), (int)drawer.getPickPoint().getY());
				Vector3f v = GLUtils.getScreen2World((int)drawer.getPickPoint().getX(), (int)drawer.getPickPoint().getY(), false);
				NotifyUIController.notifyClickedWorldCoords(new Vector2f(v.getX(), v.getY()));
				selectionMode = false;
			}
			
			// Place the map locations
			this.drawItems();
		}
	}
	
	public static void updateMapChanged() throws GLSingletonNotInitializedException, IOException {
		GLMapLocationViewController.textureMapLoader = new TextureLoader (new String[]{MapController.getActiveMap().getFilename()});
		GLMapLocationViewController.hasTextureMapChanged = true;
	}

	public void setupItems() {
		mapLocations = new ArrayList<GLObject>();
		if (locations.size() > 0) {
			GLAbstractFactory glFactory = new JOGLFactory();
			for (Vector2f loc : locations) {
				if (loc != null) {
					GLObject foo = glFactory.createMapLocation(loc.getX(), loc.getY());
					mapLocations.add(foo);
				}
			}
			//System.out.println("param: " + locations.size() + "\tstatic: " + GLMapLocationViewController.locations.size());
			// TODO revisar el acceso a campos estaticos
		}
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		int cont = 0;
		for (GLObject glo : mapLocations) {
			if (this.selectionMode) GLSingleton.getGL().glLoadName(cont++);
			glo.draw();
		}
	}

	@Override
	protected void handleHits(int hits, int[] data) {
		int offset = 0;
		if (hits > 0) {
			System.out.println("Number of hits = " + hits);
			// TODO quedarse con la que está más cerca del viewpoint en el eje Z
			for (int i = 0; i < hits; i++) {
				System.out.println("number " + data[offset++]);
				System.out.println("minZ " + data[offset++]);
				System.out.println("maxZ " + data[offset++]);
				System.out.println("stackName " + data[offset]);
				int pickedNode = data[offset];
				this.drawer.setupTowers(pickedNode);
				this.drawer.setViewLevel(EViewLevels.TowerLevel);
				offset++;
			}
		}
	}
	
	public static void addMapLocations (List<Vector2f> locs) {
		locations = new ArrayList<Vector2f>();
		if (locs.size() > 0)
			locations.addAll(locs);
	}

}
