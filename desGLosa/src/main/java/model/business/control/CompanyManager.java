package model.business.control;

import java.sql.SQLException;
import java.util.List;

import persistence.dao.business.CompanyDAO;

import exceptions.CompanyAlreadyExistsException;
import exceptions.CompanyNotFoundException;
import exceptions.MandatoryFieldException;
import model.business.knowledge.Company;

public class CompanyManager {
	
	public static boolean addCompany (Company c) throws MandatoryFieldException, SQLException, CompanyAlreadyExistsException {
		boolean success = false;
		// Check that required fields are filled in
		if (c.getName().equals("")) throw new MandatoryFieldException();
		try {
			// If no exception is thrown, check if the company exists already
			CompanyManager.getCompany(c.getName());
			throw new CompanyAlreadyExistsException();
		} catch (CompanyNotFoundException e) {
			// If the company does not exist, insert it
			CompanyDAO.insert(c);
			success = true;
		}
		return success;
	}
	
	public static Company getCompany (int id) throws SQLException, CompanyNotFoundException {
		Company result = CompanyDAO.get(id);
		return result;
	}
	
	public static Company getCompany (String name) throws MandatoryFieldException, SQLException, CompanyNotFoundException {
		if (name.equals("")) throw new MandatoryFieldException();
		Company result = CompanyDAO.get(name);
		return result;
	}
	
	public static List<Company> getAllCompanies () throws SQLException {
		return CompanyDAO.getAll();
	}

}
