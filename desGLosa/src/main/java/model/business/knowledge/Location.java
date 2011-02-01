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
	
	
}
