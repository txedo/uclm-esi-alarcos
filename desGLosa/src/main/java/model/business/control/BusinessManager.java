package model.business.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import exceptions.CompanyAlreadyExistsException;
import exceptions.CompanyNotFoundException;
import exceptions.MandatoryFieldException;
import exceptions.FactoryAlreadyExistsException;
import exceptions.FactoryNotFoundException;
import exceptions.ImageNotFoundException;
import exceptions.LocationAlreadyExistsException;
import exceptions.LocationNotFoundException;
import exceptions.MapNotFoundException;
import exceptions.gl.GLSingletonNotInitializedException;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;
import model.business.knowledge.Image;
import model.business.knowledge.Location;
import model.business.knowledge.Map;
import model.knowledge.Vector2f;

public class BusinessManager {
	
	public static boolean addCompany(Company c) throws MandatoryFieldException, SQLException, CompanyAlreadyExistsException {
		return CompanyManager.addCompany(c);
	}

	public static Company getCompany(String name) throws MandatoryFieldException, SQLException, CompanyNotFoundException {
		return (Company)CompanyManager.getCompany(name).clone();
	}
	
	public static Company getCompany(int id) throws SQLException, CompanyNotFoundException {
		return (Company)CompanyManager.getCompany(id).clone();
	}
	
	public static List<Company> getAllCompanies () throws SQLException {
		return CompanyManager.getAllCompanies();
	}

	public static boolean addFactory(Factory f) throws MandatoryFieldException, SQLException, FactoryAlreadyExistsException {
		return FactoryManager.addFactory(f);
	}

	public static List<Factory> getFactories(Company c) throws SQLException {
		return FactoryManager.getFactories(c.getName());
	}

	public static List<Map> getAllMaps() throws SQLException {
		return MapManager.getAllMaps();
	}

	public static void setActiveMap(Map m) throws GLSingletonNotInitializedException, IOException {
		MapManager.setActiveMap(m);
	}

	public static Factory getFactory(int id) throws SQLException, FactoryNotFoundException {
		return FactoryManager.getFactory(id);
	}

	public static boolean addLocation(Factory factory, Map map, Vector2f coordinates) throws LocationAlreadyExistsException, MandatoryFieldException, SQLException {
		return LocationManager.addLocation (factory, map, coordinates);
	}

	public static Factory getFactory(String name) throws MandatoryFieldException, SQLException, FactoryNotFoundException {
		return FactoryManager.getFactory(name);
	}

	public static Map getMap(int id) throws SQLException, MapNotFoundException {
		return MapManager.getMap(id);
	}

	public static Map getMap(String label) throws SQLException, MapNotFoundException {
		return MapManager.getMap(label);
	}

	public static Image getImage(int id) throws SQLException, ImageNotFoundException {
		return ImageManager.getImage(id);
	}

	public static boolean updateImage(Image image) throws SQLException, ImageNotFoundException {
		return ImageManager.updateImage(image);
	}

	public static Map getActiveMap() {
		return MapManager.getActiveMap();
	}

	public static List<Location> getLocations(List<Factory> factories, Map map) throws MandatoryFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException {
		return LocationManager.getLocations (factories, map);
	}

	public static void setMapLocations(List<Location> locations) {
		MapManager.setMapLocations(locations);
	}

	public static Location getLocation(int id) throws SQLException, LocationNotFoundException {
		return LocationManager.getLocation(id);
	}

	public static Location getLocation(Factory factory,	Map map) throws MandatoryFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException {
		return LocationManager.getLocation(factory, map);
	}

	public static List<Factory> getAllFactories() throws SQLException {
		return FactoryManager.getAllFactories();
	}

}
