package model.business.knowledge;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"mapId", "xcoord", "ycoord"})
public class Mark {
	@XmlAttribute private int mapId;
	@XmlAttribute private float xcoord;
	@XmlAttribute private float ycoord;
	
	public Mark() {
		super();
	}

	public Mark(int mapId, float xcoord, float ycoord) {
		super();
		this.mapId = mapId;
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}

	public int getMapId() {
		return mapId;
	}
	
	public void setMapId(int mapId) {
		this.mapId = mapId;
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
	
}
