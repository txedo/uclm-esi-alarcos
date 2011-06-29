package model.util;

import java.util.List;

import model.gl.knowledge.GLObject;

public class Neighborhood extends City {
	private final float X_GAP = 0.3f;
	private final float Y_GAP = 0.6f;
	
	private float width;
	private float depth;
	
	private List<GLObject> flats;

	public Neighborhood (List<GLObject> flats) {
		super();
		this.flats = flats;
		this.cols = 0;
		this.rows = 0;
		this.width = 0.0f;
		this.depth = 0.0f;
		// If the neighborhood has flats
		if (flats != null) {
			if (flats.size() > 0) {
				// Calculate how many columns it will have
				this.cols = super.calculateCols(flats.size());
				// Calculate how many rows it will have
				if (flats.size()%this.cols == 0)
					this.rows = flats.size()/this.cols;
				else
					this.rows = (flats.size()/this.cols + 1);
			}
		}
	}
	
	protected Vector2f doLayout(Vector2f point) {
		// Once we know how many cols and rows it will have, we can calculate a position for each flatç
		float x = point.getX();
		float y = point.getY();
		GLObject obj = null;
		boolean done = false;
		for (int i = 0; i < this.rows && !done; i++) {
			for (int j = 0; j < this.cols && !done; j++) {
				int index = i*this.cols+j;
				if (flats.size() > index) {
					obj = flats.get(index);
					obj.setPositionX(x);
					obj.setPositionZ(y);
					x += obj.getMaxWidth() + X_GAP;
				}
				else done = true;
			}
			if (!done) {
				x = point.getX();
				if (obj != null) y += obj.getMaxDepth() + Y_GAP;
			}

		}
		this.width = this.cols*obj.getMaxWidth() + (this.cols-1)*this.X_GAP;
		this.depth = this.rows*obj.getMaxDepth() + (this.rows-1)*this.Y_GAP;
		
		return new Vector2f(this.width, this.depth);
	}

	public List<GLObject> getFlats() {
		return flats;
	}

	public void setFlats(List<GLObject> flats) {
		this.flats = flats;
	}

	public float getWidth() {
		return width;
	}

	public float getDepth() {
		return depth;
	}
	
}
