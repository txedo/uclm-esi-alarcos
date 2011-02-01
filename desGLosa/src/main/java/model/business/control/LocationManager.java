package model.business.control;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.dao.business.LocationDAO;

import exceptions.EmptyFieldException;
import exceptions.FactoryNotFoundException;
import exceptions.LocationAlreadyExistsException;
import exceptions.LocationNotFoundException;
import exceptions.MapNotFoundException;
import model.business.knowledge.Factory;
import model.business.knowledge.Location;
import model.business.knowledge.Map;
import model.knowledge.Vector2f;

public class LocationManager {

	public static boolean addLocation(Factory factory, Map map,	Vector2f coordinates) throws LocationAlreadyExistsException, EmptyFieldException, SQLException {
		boolean success = false;
		// Check if the location already exists for this factory and map (1:1 relationship)
		try {
			LocationDAO.get(factory, map);
			throw new LocationAlreadyExistsException();
		} catch (LocationNotFoundException e) {
			// Check if the factory and map location already exists
			try {
				factory = BusinessManager.getFactory(factory.getName());
				map = BusinessManager.getMap(map.getLabel());
			} catch (FactoryNotFoundException e1) {
			} catch (MapNotFoundException e2) {
			}
			// Create the new location
			Location loc = new Location(factory, map, coordinates.getX(), coordinates.getY());
			// Insert the location
			LocationDAO.insert(loc);
			success = true;
		}

		return success;
	}

	public static Location getLocation(Factory factory, Map map) throws EmptyFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException {
		//factory = BusinessManager.getFactory(factory.getName());
		//map = BusinessManager.getMap(map.getLabel());
		Location result = LocationDAO.get(factory, map);
		return result;
	}

	public static List<Location> getLocations(List<Factory> factories, Map map) throws EmptyFieldException, SQLException, FactoryNotFoundException, MapNotFoundException {
		List<Location> result = new ArrayList<Location>();
		for (Factory f : factories) {
			try {
				Location loc = LocationManager.getLocation(f, map);
				result.add(loc);
			} catch (LocationNotFoundException e) {}
		}
		return result;
	}
	
	

}
