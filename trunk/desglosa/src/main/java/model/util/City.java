package model.util;

import java.util.ArrayList;
import java.util.List;

import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLPavement;

public class City {
	private final float X_GAP = 1.5f;
	private final float Y_GAP = 2.0f;
	
	private List<Neighborhood> neightborhoods;
	private List<GLObject> pavements;
	protected int cols;
	protected int rows;
	protected Vector2f placePoint;

	public City() {
		this.neightborhoods = new ArrayList<Neighborhood>();
		this.cols = 0;
		this.rows = 0;
		placePoint = new Vector2f(0.0f, 0.0f);
		pavements = new ArrayList<GLObject>();
	}
	
	public City(List<Neighborhood> neightborhoods) {
		this();
		this.neightborhoods = neightborhoods;
		calculateRowsAndCols();
	}
	
	protected void calculateRowsAndCols() {
		if (neightborhoods != null) {
			if (neightborhoods.size() > 0) {
				this.cols = calculateCols(neightborhoods.size());
				if (neightborhoods.size()%this.cols == 0)
					this.rows = neightborhoods.size()/this.cols;
				else
					this.rows = (neightborhoods.size()/this.cols + 1);
			}
		}
	}
	
	protected int calculateCols (double d) {
		return (Math.sqrt(d) > (double)((int)(Math.sqrt(d))) ? (int)(Math.sqrt(d))+1 : (int)(Math.sqrt(d)));
	}
	
	public void placeNeighborhoods() {
		
		// Once we know how many columns and rows it will have, we can calculate a position for each flat
		float x = placePoint.getX();
		Vector2f dimensions = null;
		double maxDepth = 0.0f;
		for (int i = 0; i < this.rows; i++) {
			maxDepth = 0.0f;
			for (int j = 0; j < this.cols; j++) {
				int index = i*this.cols+j;
				if (neightborhoods.size() > index) {
					// Configure Pavement
					GLPavement pavement = new GLPavement();
					pavement.setPositionX(placePoint.getX()-neightborhoods.get(0).getFlats().get(0).getMaxWidth()/2-this.X_GAP/3);
					pavement.setPositionZ(placePoint.getY()-neightborhoods.get(0).getFlats().get(0).getMaxDepth()/2-this.Y_GAP/3);
					// The neighborhood place its flats by itself
					dimensions = neightborhoods.get(index).doLayout(placePoint);
					placePoint.setX(dimensions.getX() + this.X_GAP);
					if (maxDepth < dimensions.getY()) maxDepth = dimensions.getY();
					// Continue configuring pavement
					pavement.setWidth(dimensions.getX()+2*this.X_GAP/3);
					pavement.setDepth((float)maxDepth+2*this.Y_GAP/3);
					pavements.add(pavement);
				}
			}
			placePoint.setX(x);
			if (dimensions != null) placePoint.setY((float)maxDepth + this.Y_GAP);
		}
	}

	public List<Neighborhood> getNeightborhoods() {
		return neightborhoods;
	}

	public List<GLObject> getPavements() {
		return pavements;
	}
	
}
