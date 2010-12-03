package model.business.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import model.knowledge.Vector2f;


public class MarkMapAdapter extends XmlAdapter<MarkMapWrapper, Map<Integer,Vector2f>> {

	@Override
	public MarkMapWrapper marshal(Map<Integer, Vector2f> v) throws Exception {
		MarkMapWrapper result = new MarkMapWrapper();
		int i = 0;
		Iterator it = v.keySet().iterator();
		while (it.hasNext()) {
			int mapId = (Integer)it.next();
			Vector2f coords = v.get(mapId);
			result.addMark(new Mark (mapId, coords.getX(), coords.getY()));
			i++;
		}
		return result;
	}

	@Override
	public Map<Integer, Vector2f> unmarshal(MarkMapWrapper v) throws Exception {
	    Map<Integer,Vector2f> r = new HashMap<Integer,Vector2f>();
	    ArrayList<Mark> markList = (ArrayList)v.getInnerList();
	    for( Mark c : markList )
	    	r.put(c.getMapId(), new Vector2f(c.getXcoord(), c.getYcoord()));
	    return r;
	}

}

//public class MarkMapAdapter extends XmlAdapter<Mark[], Map<Integer,Vector2f>> {
//
//	@Override
//	public Mark[] marshal(Map<Integer, Vector2f> v) throws Exception {
//		Mark [] result = new Mark[v.size()];
//		int i = 0;
//		Iterator it = v.keySet().iterator();
//		while (it.hasNext()) {
//			int mapId = (Integer)it.next();
//			Vector2f coords = v.get(mapId);
//			result[i] = new Mark (mapId, coords.getX(), coords.getY());
//			i++;
//		}
//		return result;
//	}
//
//	@Override
//	public Map<Integer, Vector2f> unmarshal(Mark[] v) throws Exception {
//	    Map<Integer,Vector2f> r = new HashMap<Integer,Vector2f>();
//	    for( Mark c : v )
//	    	r.put(c.getMapId(), new Vector2f(c.getXcoord(), c.getYcoord()));
//	    return r;
//	}
//
//}
