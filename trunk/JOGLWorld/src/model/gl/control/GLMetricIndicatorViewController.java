package model.gl.control;

import java.util.ArrayList;
import java.util.List;

import model.gl.GLObject;
import model.gl.GLSingleton;
import model.gl.knowledge.Edge;
import model.gl.knowledge.IEdge;
import model.gl.knowledge.MetricIndicator;
import model.knowledge.Color;

import exceptions.gl.GLSingletonNotInitializedException;

public class GLMetricIndicatorViewController extends GLViewController {
	private List<GLObject> nodes;
	private List<GLObject> edges;

	public GLMetricIndicatorViewController(GLDrawer d, boolean is3d) {
		super(d, is3d);
		this.nodes = new ArrayList<GLObject>();
		this.edges = new ArrayList<GLObject>();
	}
	
	@Override
	public void manageView() throws GLSingletonNotInitializedException {
		if (this.isSelectionMode()) this.selectItem();
		this.drawItems();
	}

	@Override
	public void setupItems() {
		this.setupNodes();
		this.setupEdges();
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

	@Override
	public void drawItems() throws GLSingletonNotInitializedException {
		this.drawNodes();
		this.drawEdges();
	}
	
	private void drawNodes () throws GLSingletonNotInitializedException {
		int cont = 1;
		for (GLObject f : nodes) {
			if (selectionMode)
				GLSingleton.getGL().glLoadName(cont++);
			f.draw();
		}
	}
	
	private void drawEdges () throws GLSingletonNotInitializedException {
		for (GLObject f : edges) {
			f.draw();
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

}
