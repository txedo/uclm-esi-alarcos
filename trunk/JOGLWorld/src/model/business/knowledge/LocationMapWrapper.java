package model.business.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LocationMapWrapper {
	@XmlElement ( name="location" )
	private List<Location> innerList;

	public LocationMapWrapper() {
		this.innerList = new ArrayList<Location>();
	}
	
	public void addLocation (Location m) {
		this.innerList.add(m);
	}

	@SuppressWarnings("rawtypes")
	public List getInnerList() {
		return innerList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setInnerList(List innerList) {
		this.innerList = innerList;
	}
	
}
