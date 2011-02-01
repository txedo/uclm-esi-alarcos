package model.business.control;

import java.sql.SQLException;
import java.util.List;

import persistence.dao.business.FactoryDAO;

import exceptions.CompanyNotFoundException;
import exceptions.EmptyFieldException;
import exceptions.FactoryAlreadyExistsException;
import exceptions.FactoryNotFoundException;

import model.NotifyUIManager;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;

public class FactoryManager {
	
	public static boolean addFactory (Factory f) throws EmptyFieldException, SQLException, FactoryAlreadyExistsException {
		boolean success = false;
		// Check that required fields are filled in
		if (f.getName().equals("")) throw new EmptyFieldException();
		if (f.getDirector() == null) throw new EmptyFieldException();
		if (f.getCompany() == null) throw new EmptyFieldException();
		if (f.getAddress() == null) throw new EmptyFieldException();
		try {
			// If no exception is thrown, check if the factory exists already
			FactoryManager.getFactory(f.getName());
			throw new FactoryAlreadyExistsException();
		} catch (FactoryNotFoundException e) {
			// If the factory does not exists yet, set if its company exists
			Company c;
			try {
				c = BusinessManager.getCompany(f.getCompany().getName());
				f.setCompany(c);
			} catch (CompanyNotFoundException e1) {
			}
			// Insert the factory
			FactoryDAO.insert(f);
			// Notify changes to the observer
			//NotifyUIController.notifyFactoryListUpdate(f.getCompany());
			success = true;
		}
		return success;
	}
	
	public static Factory getFactory (int id) throws SQLException, FactoryNotFoundException {
		Factory result = FactoryDAO.get(id);
		return result;
	}
	
	public static Factory getFactory (String name) throws EmptyFieldException, SQLException, FactoryNotFoundException {
		if (name.equals("")) throw new EmptyFieldException();
		Factory result = FactoryDAO.get(name);
		return result;
	}
	
	public static List<Factory> getAllFactories (int companyId) throws SQLException {
		return FactoryDAO.getAll();
	}

	public static List<Factory> getFactories(String companyName) throws SQLException {
		return FactoryDAO.getFactories(companyName);
	}

}
