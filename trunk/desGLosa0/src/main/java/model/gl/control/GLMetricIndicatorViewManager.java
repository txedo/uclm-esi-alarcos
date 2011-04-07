package model.gl.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;

import model.gl.GLDrawer;
import model.gl.GLSingleton;
import model.gl.knowledge.Edge;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.IEdge;
import model.gl.knowledge.MetricIndicator;
import model.gl.knowledge.caption.Caption;
import model.knowledge.Color;

import exceptions.gl.GLSingletonNotInitializedException;

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
		GLSingleton.getGL().glDisable(GL.GL_LIGHTING);
	}

	@Override
	public void deconfigureView() throws GLSingletonNotInitializedException {
		GLSingleton.getGL().glEnable(GL.GL_LIGHTING);
	}
	
	@Override
	public void manageView() throws GLSingletonNotInitializedException, IOException {
		if (this.isSelectionMode()) this.selectItem();
		this.drawItems();
	}

	public void setupItems() {
		this.setupNodes();
		this.setupEdges();
		this.setupCaptions();
	}
	
	private void setupNodes () {
		MetricIndicator n;
		Color c = new Color (0.0f, 0.0f, 0.0f);
		n = new MetricIndicator(0.0f, 0.0f, 1.0f, c);
		this.nodes.add(n);
		n = new MetricIndicator(2.0f, 0.0f, 1.0f, c);
		this.nodes.add(n);
		n = new MetricIndicator(0.0f, 2.0f, 1.0f, c);
		this.nodes.add(n);
		n = new MetricIndicator(2.0f, 2.0f, 1.0f, c);
		this.nodes.add(n);
	}
	
	private void setupEdges () {
		Edge e;
		e = new Edge((MetricIndicator)nodes.get(0), (MetricIndicator)nodes.get(1));
		e.setType(IEdge.DOTTED);
		this.edges.add(e);
		e = new Edge((MetricIndicator)nodes.get(0), (MetricIndicator)nodes.get(2));
		e.setType(IEdge.DASHED);
		this.edges.add(e);
		e = new Edge((MetricIndicator)nodes.get(1), (MetricIndicator)nodes.get(2));
		e.setType(IEdge.DOT_AND_DASH);
		this.edges.add(e);
		e = new Edge((MetricIndicator)nodes.get(2), (MetricIndicator)nodes.get(3));
		e.setType(IEdge.SOLID);
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
		((GLTowerViewManager)super.drawer.getViewManager(EViewLevels.TowerLevel)).setupItems(selectedObject);
		this.drawer.setViewLevel(EViewLevels.TowerLevel);
	}

}
