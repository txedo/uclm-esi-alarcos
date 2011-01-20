package model.business.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement ( name="maps" )
@XmlAccessorType(XmlAccessType.FIELD)
public class MapWrapper {
	@XmlElement ( name="map" , required=false ) private List<Map> innerList = new ArrayList<Map>();
	
	public void addMap (Map m) {
		innerList.add(m);
	}
	
	public void addAllMaps (List<Map> m) {
		innerList.addAll(m);
	}
	
	public void setInnerList(List<Map> innerList) {
		this.innerList = innerList;
	}

	public List<Map> getInnerList () {
		return innerList;
	}
	
	public int getLastId () {
		int lastId = 0;
		if (innerList.size() != 0)
			lastId = ((Map)innerList.get(innerList.size()-1)).getId();
		return ++lastId;
	}
	
	public String toString () {
		StringBuffer sb = new StringBuffer();
        sb.append( " mapList\n");
        for (Map m : innerList) {
        	sb.append( "       " + m + "\n");
        }
		return sb.toString();
	}
}
