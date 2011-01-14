package persistence.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import model.business.knowledge.Map;
import model.business.knowledge.MapWrapper;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.MapNotFoundException;

import persistence.XMLAgent;

public class MapDAO {
	private String xmlfile;
	
	public MapDAO () throws ConfigurationException {
		this.xmlfile = XMLAgent.getXMLFilename("maps");
	}
	
	public void save (Map m) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		MapWrapper mapList = this.getAll();
		m.setId(mapList.getLastId());
		mapList.addMap(m);
		XMLAgent.marshal(this.xmlfile, MapWrapper.class, (MapWrapper)mapList);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map get (String mapName) throws JAXBException, IOException, InstantiationException, IllegalAccessException, MapNotFoundException {
		boolean found = false;
		Map result = null;
		ArrayList<Map> maps = (ArrayList)XMLAgent.unmarshal(this.xmlfile, MapWrapper.class).getInnerList();
		Iterator it = maps.iterator();
		while (!found && it.hasNext()) {
			Map aux = (Map)it.next();
			if (aux.getLabel().equals(mapName)) {
				result = aux;
				found = true;
			}
		}
		if (result == null) throw new MapNotFoundException ();
		return result;
	}
	
	public MapWrapper getAll () throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		return (MapWrapper)XMLAgent.unmarshal(this.xmlfile, MapWrapper.class);
	}
}
