package model.util;

import java.util.ArrayList;
import java.util.List;

public class City {
	private final float X_GAP = 1.5f;
	private final float Y_GAP = 2.0f;
	
	private List<Neighborhood> neightborhoods;
	protected int cols;
	protected int rows;
	protected Vector2f placePoint;

	public City() {
		this.neightborhoods = new ArrayList<Neighborhood>();
		this.cols = 0;
		this.rows = 0;
		placePoint = new Vector2f(0.0f, 0.0f);
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
		// Once we know how many cols and rows it will have, we can calculate a position for each flatç
		float x = placePoint.getX();
		Vector2f dimensions = null;
		double maxDepth = 0.0f;
		for (int i = 0; i < this.rows; i++) {
			maxDepth = 0.0f;
			for (int j = 0; j < this.cols; j++) {
				int index = i*this.cols+j;
				if (neightborhoods.size() > index) {
					dimensions = neightborhoods.get(index).doLayout(placePoint);
					placePoint.setX(dimensions.getX() + this.X_GAP);
					if (maxDepth < dimensions.getY()) maxDepth = dimensions.getY();
				}
			}
			placePoint.setX(x);
			if (dimensions != null) placePoint.setY((float)maxDepth + this.Y_GAP);
		}
	}
	
}
