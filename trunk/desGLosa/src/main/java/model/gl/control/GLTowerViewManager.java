package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.gl.GLDrawer;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLObject3D;
import model.gl.knowledge.Tower;
import model.knowledge.Color;
import exceptions.gl.GLSingletonNotInitializedException;

public class GLTowerViewManager extends GLViewManager {
	private List<GLObject> towers;
	
	public GLTowerViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		towers = new ArrayList<GLObject>();
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		
	}

	@Override
	public void manageView() throws GLSingletonNotInitializedException, IOException {
		super.drawFloor();
		this.drawItems();
	}

	public void setupItems(int id) {
		towers = new ArrayList<GLObject>();
		Random r = new Random();
		Color c;
		Tower t;
		for (int i = 0; i < id; i++) {
			c = new Color (r.nextFloat(), r.nextFloat(), r.nextFloat());
			t = new Tower (r.nextFloat()*9,r.nextFloat()*9,r.nextFloat(),r.nextFloat(),r.nextFloat()*5,c);
			float aux = t.getRealMeasure();
			t.setEstimatedMeasure(aux+1);
			towers.add(t);
		}
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		for (GLObject glo : towers) {
			if (this.drawingShadows) ((GLObject3D)glo).drawShadow();
			else ((GLObject3D)glo).draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		// TODO Auto-generated method stub
		
	}

}
