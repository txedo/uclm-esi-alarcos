package model.business.knowledge;


public class Location {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4801491534751742727L;
	private int id;
	private Factory factory;
	private Map map;
	private float xcoord;
	private float ycoord;
	
	public Location() {
		super();
	}

	public Location(Factory fact, Map map, float xcoord, float ycoord) {
		super();
		this.factory = fact;
		this.map = map;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
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
	public String toString() {
		return "Location [factory=" + factory + ", map=" + map + ", xcoord="
				+ xcoord + ", ycoord=" + ycoord + "]";
	}

	@Override
	public Object clone() {
		Location result = new Location((Factory)this.getFactory().clone(), (Map)this.getMap().clone(), this.xcoord, this.ycoord);
		result.setId(this.getId());
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result + id;
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
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (id != other.id)
			return false;
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
