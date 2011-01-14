package model.business.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import model.knowledge.Vector2f;


public class LocationMapAdapter extends XmlAdapter<LocationMapWrapper, Map<Integer,Vector2f>> {

	@SuppressWarnings("rawtypes")
	@Override
	public LocationMapWrapper marshal(Map<Integer, Vector2f> v) throws Exception {
		LocationMapWrapper result = new LocationMapWrapper();
		int i = 0;
		Iterator it = v.keySet().iterator();
		while (it.hasNext()) {
			int mapId = (Integer)it.next();
			Vector2f coords = v.get(mapId);
			result.addLocation(new Location (mapId, coords.getX(), coords.getY()));
			i++;
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<Integer, Vector2f> unmarshal(LocationMapWrapper v) throws Exception {
	    Map<Integer,Vector2f> r = new HashMap<Integer,Vector2f>();
	    ArrayList<Location> locationList = (ArrayList)v.getInnerList();
	    for( Location c : locationList )
	    	r.put(c.getMapId(), new Vector2f(c.getXcoord(), c.getYcoord()));
	    return r;
	}

}

