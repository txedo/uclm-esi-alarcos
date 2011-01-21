package model.business.knowledge;

import java.io.Serializable;


public class Location implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4801491534751742727L;
	private Map map;
	private float xcoord;
	private float ycoord;
	
	public Location() {
		super();
	}

	public Location(Map map, float xcoord, float ycoord) {
		super();
		this.map = map;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}

	public Map getMap() {
		return this.map;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public float getXcoord() {
		return xcoord;
	}
	
	public void setXcoord(float xcoord) {
		this.xcoord = xcoord;
	}
	
	public float getYcoord() {
		return ycoord;
	}
	
	public void setYcoord(float ycoord) {
		this.ycoord = ycoord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + Float.floatToIntBits(xcoord);
		result = prime * result + Float.floatToIntBits(ycoord);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (Float.floatToIntBits(xcoord) != Float.floatToIntBits(other.xcoord))
			return false;
		if (Float.floatToIntBits(ycoord) != Float.floatToIntBits(other.ycoord))
			return false;
		return true;
	}
	
}
