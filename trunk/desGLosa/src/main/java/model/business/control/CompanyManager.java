package model.business.control;

import java.sql.SQLException;
import java.util.List;

import persistence.dao.business.CompanyDAO;

import exceptions.CompanyAlreadyExistsException;
import exceptions.CompanyNotFoundException;
import exceptions.EmptyFieldException;
import model.NotifyUIManager;
import model.business.knowledge.Company;

public class CompanyManager {
	
	public static boolean addCompany (Company c) throws EmptyFieldException, SQLException, CompanyAlreadyExistsException {
		boolean success = false;
		// Check that required fields are filled in
		if (c.getName().equals("")) throw new EmptyFieldException();
		try {
			// If no exception is thrown, check if the company exists already
			CompanyManager.getCompany(c.getName());
			throw new CompanyAlreadyExistsException();
		} catch (CompanyNotFoundException e) {
			// If the company does not exist, insert it
			CompanyDAO.insert(c);
			// Notify changes to the observer
			NotifyUIManager.notifyCompanyListUpdate();
			success = true;
		}
		return success;
	}
	
	public static Company getCompany (int id) throws SQLException, CompanyNotFoundException {
		Company result = CompanyDAO.get(id);
		return result;
	}
	
	public static Company getCompany (String name) throws EmptyFieldException, SQLException, CompanyNotFoundException {
		if (name.equals("")) throw new EmptyFieldException();
		Company result = CompanyDAO.get(name);
		return result;
	}
	
	public static List<Company> getAllCompanies () throws SQLException {
		return CompanyDAO.getAll();
	}

}
