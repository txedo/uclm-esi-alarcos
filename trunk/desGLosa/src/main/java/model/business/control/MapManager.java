package model.business.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.NotifyUIManager;
import model.business.knowledge.Location;
import model.business.knowledge.Map;
import model.gl.control.GLMapLocationViewManager;

import persistence.dao.business.MapDAO;
import exceptions.MapAlreadyExistsException;
import exceptions.MapNotFoundException;
import exceptions.gl.GLSingletonNotInitializedException;

public class MapManager {
	
	private static Map activeMap;
	
	public static Map getActiveMap() {
		return MapManager.activeMap;
	}

	public static void setActiveMap(Map m) throws GLSingletonNotInitializedException, IOException {
		MapManager.activeMap = m;
		GLMapLocationViewManager.updateMapChanged();
	}
	
	public static void setMapLocations (List<Location> locations) {
		GLMapLocationViewManager.addMapLocations(locations);
	}

	static public boolean addMap (Map m) throws MapAlreadyExistsException, SQLException {
		boolean success = false;
		try {
			MapManager.getMap(m.getLabel());
			throw new MapAlreadyExistsException();
		} catch (MapNotFoundException e) {
			MapDAO.insert(m);
			NotifyUIManager.notifyMapListUpdate();
			success = true;
		}
		return success;
	}
	
	static public Map getMap (String name) throws SQLException, MapNotFoundException {
		Map result = MapDAO.get(name);
		return result;
	}
	
	public static Map getMap(int id) throws SQLException, MapNotFoundException {
		Map result = MapDAO.get(id);
		return result;
	}
	
	static public List<Map> getAllMaps () throws SQLException {
		return MapDAO.getAll();
	}

	public static void highlightMapLocations(List<Location> locations) {
		GLMapLocationViewManager.highlightMapLocations(locations);
	}
	
}
