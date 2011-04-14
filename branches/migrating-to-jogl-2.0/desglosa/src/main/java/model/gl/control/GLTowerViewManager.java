package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.NotifyUIManager;
import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.GLUtils;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;
import model.gl.knowledge.Tower;
import model.knowledge.Color;
import model.knowledge.Vector3f;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLTowerViewManager extends GLViewManager {
	private List<GLObject> towers;
	
	public GLTowerViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		towers = new ArrayList<GLObject>();
		setupItems(10);
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
//		super.drawFloor();
		this.drawItems();
	}

	public void setupItems(int id) {
		towers = new ArrayList<GLObject>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < id; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (r.nextFloat()*9,r.nextFloat()*9,r.nextFloat()*2.f,r.nextFloat()*2.f,r.nextFloat()*5,c);
			float aux = t.getRealMeasure();
			t.setEstimatedMeasure(aux+1);
			towers.add(t);
		}
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
