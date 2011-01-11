package model.gl.control;

import java.io.IOException;

import javax.media.opengl.GL;

import model.NotifyUIController;
import model.business.control.MapController;
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

	public GLMapLocationViewController(GLDrawer d, boolean is3d) {
		super(d, is3d);
		GLMapLocationViewController.hasTextureMapChanged = false;
		this.isTextureMapReady = false;
	}
	
	@Override
	public void manageView() throws GLSingletonNotInitializedException, IOException {
		// This state machine prevents GL trying to load a texture while it is loading from disk to memory
		if (hasTextureMapChanged) {
			isTextureMapReady = false;
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

	@Override
	public void setupItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleHits(int hits, int[] data) {
		// TODO Auto-generated method stub
		
	}

}
