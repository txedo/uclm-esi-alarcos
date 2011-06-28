package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;
import model.util.Vector3f;
import exceptions.GLSingletonNotInitializedException;
import exceptions.ViewManagerNotInstantiatedException;

public class GLTowerViewManager extends GLViewManager {
	
	static private GLTowerViewManager _instance = null;
	
	/**
	 * @return The unique instance of this class.
	 * @throws ViewManagerNotInstantiatedException 
	 */
	static public GLTowerViewManager getInstance() throws ViewManagerNotInstantiatedException {
		if (null == _instance) {
			throw new ViewManagerNotInstantiatedException();
		}
		return _instance;
	}
	
	private List<GLObject> towers;
	
	public GLTowerViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		towers = new ArrayList<GLObject>();
		_instance = this;
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException, IOException {
		if (this.isSelectionMode()) this.selectItem();
		super.drawFloor();
		this.drawItems();
	}

	@Override
	public void setItems(List objs) {
		towers = new ArrayList<GLObject>();
		towers.addAll(objs);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		int cont = 1;
		Vector3f cameraPosition = this.drawer.getCamera().getPosition().clone();
		Map<GLObject,Float> sortedTowers = new HashMap<GLObject, Float>();
		for (GLObject glo : towers) {
			float distance = (new Vector3f(cameraPosition.getX()-glo.getPositionX(), 0.0f, cameraPosition.getZ()-glo.getPositionZ())).getLength();
			sortedTowers.put(glo, distance);
		}
		sortedTowers = GLUtils.sortHashMap((HashMap<GLObject, Float>)sortedTowers, true);
		for (GLObject glo : sortedTowers.keySet()) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			if (this.drawingShadows) ((GLObject3D)glo).drawShadow();
			else ((GLObject3D)glo).draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		System.err.println("Selected tower: " + selectedObject);
		NotifyUIManager.notifySelectedTower(selectedObject);
	}

}
