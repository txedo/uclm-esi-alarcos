package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.knowledge.GLEdge;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.IGLEdge;
import model.gl.knowledge.GLMetricIndicator;
import model.gl.knowledge.caption.Caption;
import model.util.Color;

import exceptions.GLSingletonNotInitializedException;

public class GLMetricIndicatorViewManager extends GLViewManager {
	private List<GLObject> nodes;
	private List<GLObject> edges;
	private List<GLObject> captions;

	public GLMetricIndicatorViewManager(GLDrawer d, boolean is3d) {
		super(d, is3d);
		this.nodes = new ArrayList<GLObject>();
		this.edges = new ArrayList<GLObject>();
		this.captions = new ArrayList<GLObject>();
	}
	
	@Override
	public void configureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glDisable(GL2.GL_LIGHTING);
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL2.GL_LIGHTING);
	}
	
	@Override
	public void manageView() throws GLSingletonNotInitializedException, IOException {
		if (this.isSelectionMode()) this.selectItem();
		this.drawItems();
	}
	
	@Override
	public void setItems(List objs) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void addItems(List objs) {
		// TODO Auto-generated method stub
	}

	public void setupItems() {
		this.setupNodes();
		this.setupEdges();
		this.setupCaptions();
	}
	
	private void setupNodes () {
		GLMetricIndicator n;
		Color c = new Color (0.0f, 0.0f, 0.0f);
		n = new GLMetricIndicator(0.0f, 0.0f, 1.0f, c);
		this.nodes.add(n);
		n = new GLMetricIndicator(2.0f, 0.0f, 1.0f, c);
		this.nodes.add(n);
		n = new GLMetricIndicator(0.0f, 2.0f, 1.0f, c);
		this.nodes.add(n);
		n = new GLMetricIndicator(2.0f, 2.0f, 1.0f, c);
		this.nodes.add(n);
	}
	
	private void setupEdges () {
		GLEdge e;
		e = new GLEdge((GLMetricIndicator)nodes.get(0), (GLMetricIndicator)nodes.get(1));
		e.setType(IGLEdge.DOTTED);
		this.edges.add(e);
		e = new GLEdge((GLMetricIndicator)nodes.get(0), (GLMetricIndicator)nodes.get(2));
		e.setType(IGLEdge.DASHED);
		this.edges.add(e);
		e = new GLEdge((GLMetricIndicator)nodes.get(1), (GLMetricIndicator)nodes.get(2));
		e.setType(IGLEdge.DOT_AND_DASH);
		this.edges.add(e);
		e = new GLEdge((GLMetricIndicator)nodes.get(2), (GLMetricIndicator)nodes.get(3));
		e.setType(IGLEdge.SOLID);
		this.edges.add(e);
	}
	
	private void setupCaptions () {
		Caption c = new Caption(3.2f, 2.8f);
		c.addLine(new Color (1.0f, 0.0f, 0.0f), "Hello World!");
		c.addLine(new Color (0.0f, 1.0f, 0.0f), "Good evening World!");
		c.addLine(new Color (0.0f, 0.0f, 1.0f), "Goodbye World!");
		c.addLine(new Color (0.3f, 0.3f, 0.3f), "Yeah!");
		c.addLine(new Color (0.5f, 0.3f, 0.7f), "Rolling of floor laughing!");
		this.captions.add(c);
	}

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		this.drawNodes();
		this.drawEdges();
		this.drawCaptions();
	}
	
	private void drawNodes () throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject glo : nodes) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			glo.draw();
		}
	}
	
	private void drawEdges () throws GLSingletonNotInitializedException {
		for (GLObject glo : edges) {
			glo.draw();
		}
	}
	
	private void drawCaptions () throws GLSingletonNotInitializedException {
		for (GLObject glo : captions) {
			glo.draw();
		}
	}

	@Override
	protected void selectedObjectHandler(int selectedObject) {
		//((GLTowerViewManager)super.drawer.getViewManager(EViewLevels.TowerLevel)).setupItems(selectedObject);
		this.drawer.setViewLevel(EViewLevels.TowerLevel);
	}


}
