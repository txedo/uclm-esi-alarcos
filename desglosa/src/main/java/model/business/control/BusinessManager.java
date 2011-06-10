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
import exceptions.NoActiveMapException;
import exceptions.ProjectNotFoundException;
import exceptions.WorkingFactoryIsNotInvolvedFactoryException;
import exceptions.gl.GLSingletonNotInitializedException;
import model.NotifyUIManager;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;
import model.business.knowledge.Image;
import model.business.knowledge.Location;
import model.business.knowledge.Map;
import model.business.knowledge.Project;
import model.knowledge.Vector2f;

public class BusinessManager {
	
	/* 
	 * Methods related to company management
	 */
	public static boolean addCompany(Company c) throws MandatoryFieldException, SQLException, CompanyAlreadyExistsException {
		boolean result;
		if (result = CompanyManager.addCompany(c)) {
			// Notify changes to the observer
			NotifyUIManager.notifyCompanyListUpdate();
		}
		return result;
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
	
	/*
	 * Methods related to factory management
	 */

	public static boolean addFactory(Factory f) throws MandatoryFieldException, SQLException, FactoryAlreadyExistsException {
		boolean result;
		if (result = FactoryManager.addFactory(f)) {
			// Notify changes to the observer
			NotifyUIManager.notifyFactoryListUpdate(f.getCompany().getId());
		}
		return result;
	}
	
	public static Factory getFactory(int id) throws SQLException, FactoryNotFoundException {
		return FactoryManager.getFactory(id);
	}
	
	public static Factory getFactory(String name) throws MandatoryFieldException, SQLException, FactoryNotFoundException {
		return FactoryManager.getFactory(name);
	}

	public static List<Factory> getFactories(Company c) throws SQLException {
		return FactoryManager.getFactories(c.getName());
	}
	
	public static List<Factory> getAllFactories() throws SQLException {
		return FactoryManager.getAllFactories();
	}
	
	/*
	 * Methods related to map management
	 */

	public static Map getMap(int id) throws SQLException, MapNotFoundException {
		return MapManager.getMap(id);
	}

	public static Map getMap(String label) throws SQLException, MapNotFoundException {
		return MapManager.getMap(label);
	}
	
	public static List<Map> getAllMaps() throws SQLException {
		return MapManager.getAllMaps();
	}

	public static void setActiveMap(Map m) throws GLSingletonNotInitializedException, IOException {
		MapManager.setActiveMap(m);
	}
	
	public static Map getActiveMap() throws NoActiveMapException {
		return MapManager.getActiveMap();
	}
	
	public static void setMapLocations(List<Factory> factories) throws MandatoryFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException, NoActiveMapException {
		List<Location> locations = BusinessManager.getLocations(factories, BusinessManager.getActiveMap());
		MapManager.setMapLocations(locations);
	}

	public static void highlightMapLocations(List<Factory> factories) throws MandatoryFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException, NoActiveMapException {
		List<Location> locations = BusinessManager.getLocations(factories, BusinessManager.getActiveMap());
		MapManager.highlightMapLocations(locations);
	}

	/*
	 * Methods related to location management
	 */

	public static boolean addLocation(Factory factory, Map map, Vector2f coordinates) throws LocationAlreadyExistsException, MandatoryFieldException, SQLException {
		return LocationManager.addLocation (factory, map, coordinates);
	}

	public static Location getLocation(int id) throws SQLException, LocationNotFoundException {
		return LocationManager.getLocation(id);
	}

	public static Location getLocation(Factory factory,	Map map) throws MandatoryFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException {
		return LocationManager.getLocation(factory, map);
	}
	
	public static List<Location> getLocations(List<Factory> factories, Map map) throws MandatoryFieldException, SQLException, FactoryNotFoundException, MapNotFoundException, LocationNotFoundException {
		return LocationManager.getLocations (factories, map);
	}
	
	public static void removeLocation(Factory factory, Map map) throws SQLException, LocationNotFoundException {
		LocationManager.removeLocation (factory, map);
	}

	/*
	 * Methods related to image management
	 */

	public static Image getImage(int id) throws SQLException, ImageNotFoundException {
		return ImageManager.getImage(id);
	}

	public static boolean updateImage(Image image) throws SQLException, ImageNotFoundException {
		return ImageManager.updateImage(image);
	}

	/*
	 * Methods related to project management
	 */
	
	public static boolean addProject(Project project) throws MandatoryFieldException, WorkingFactoryIsNotInvolvedFactoryException, SQLException, FactoryNotFoundException {
		boolean result;
		if (result = ProjectManager.addProject(project)) {
			// Notify changes to the observer
			NotifyUIManager.notifyProjectListUpdate();
		}
		return result;
	}
	
	public static Project getProject(int id) throws SQLException, ProjectNotFoundException {
		return ProjectManager.getProject(id);
	}

	public static List<Project> getAllProjects() throws SQLException {
		return ProjectManager.getAllProjects();
	}

}