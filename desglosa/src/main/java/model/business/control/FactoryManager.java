package model.business.control;

import java.sql.SQLException;
import java.util.List;

import persistence.dao.business.FactoryDAO;

import exceptions.MandatoryFieldException;
import exceptions.FactoryAlreadyExistsException;
import exceptions.FactoryNotFoundException;

import model.business.knowledge.Factory;

public class FactoryManager {
	
	public static boolean addFactory (Factory f) throws MandatoryFieldException, SQLException, FactoryAlreadyExistsException {
		boolean success = false;
		// Check that required fields are filled in
		if (f.getName().equals("")) throw new MandatoryFieldException();
		if (f.getDirector() == null) throw new MandatoryFieldException();
		if (f.getCompany() == null) throw new MandatoryFieldException();
		if (f.getAddress() == null) throw new MandatoryFieldException();
		try {
			// If no exception is thrown, check if the factory exists already
			FactoryManager.getFactory(f.getName());
			throw new FactoryAlreadyExistsException();
		} catch (FactoryNotFoundException e) {
			// Insert the factory
			FactoryDAO.insert(f);
			success = true;
		}
		return success;
	}
	
	public static Factory getFactory (int id) throws SQLException, FactoryNotFoundException {
		Factory result = FactoryDAO.get(id);
		return result;
	}
	
	public static Factory getFactory (String name) throws MandatoryFieldException, SQLException, FactoryNotFoundException {
		if (name.equals("")) throw new MandatoryFieldException();
		Factory result = FactoryDAO.get(name);
		return result;
	}
	
	public static List<Factory> getAllFactories () throws SQLException {
		return FactoryDAO.getAll();
	}

	public static List<Factory> getFactories(String companyName) throws SQLException {
		return FactoryDAO.getFactories(companyName);
	}


}
