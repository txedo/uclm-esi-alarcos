package model.business.control;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import model.NotifyUIController;
import model.business.knowledge.Centre;
import model.business.knowledge.Map;
import model.gl.control.GLMapLocationViewController;
import model.knowledge.Vector2f;

import org.apache.commons.configuration.ConfigurationException;

import persistency.dao.MapDAO;
import exceptions.MapAlreadyExistsException;
import exceptions.MapNotFoundException;
import exceptions.gl.GLSingletonNotInitializedException;

public class MapController {
	
	private static Map activeMap;
	
	public static Map getActiveMap() {
		return activeMap;
	}

	public static void setActiveMap(Map activeMap) throws GLSingletonNotInitializedException, IOException {
		MapController.activeMap = activeMap;
		GLMapLocationViewController.updateMapChanged();
	}
	
	public static void setMapLocations (List<Centre> centres, List<Vector2f> locations) {
		GLMapLocationViewController.addMapLocations(centres, locations);
	}

	static public void addMap (Map m) throws MapAlreadyExistsException, ConfigurationException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		try {
			MapController.getMap(m.getLabel());
			throw new MapAlreadyExistsException();
		} catch (MapNotFoundException e) {
			MapDAO mdao = new MapDAO();
			mdao.save(m);
			NotifyUIController.notifyMapListUpdate();
		}
	}
	
	static public Map getMap (String name) throws ConfigurationException, JAXBException, IOException, InstantiationException, IllegalAccessException, MapNotFoundException {
		Map result = null;
		MapDAO mdao = new MapDAO();
		result = mdao.get(name);
		if (result == null) throw new MapNotFoundException ();
		return result;
	}
	
	static public List<Map> getAllMaps () throws ConfigurationException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		MapDAO mdao = new MapDAO();
		return mdao.getAll().getInnerList();
	}
}
