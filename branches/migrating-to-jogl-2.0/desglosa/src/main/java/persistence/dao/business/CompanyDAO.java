package persistence.dao.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.CompanyNotFoundException;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;

import model.business.knowledge.Company;


public class CompanyDAO {
	private static final String TABLE = "Company";
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	
	public static void insert (Company company) throws SQLException {
		try {
			ConnectionManager.beginTransaction();
			ConnectionManager.insert(company.clone());
		} finally {
			ConnectionManager.endTransaction();
		}
	}
	
	public static void insertAll (List<Company> companies) throws SQLException  {
		for (Company c : companies) {
			CompanyDAO.insert (c);
		}
	}
	
	public static Company get (int id) throws SQLException, CompanyNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Company result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + ID_COLUMN + " = ?", id);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Company
			result = (Company)((Company)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new CompanyNotFoundException();
		
		return result;
	}
	
	public static Company get (String name) throws SQLException, CompanyNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Company result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + NAME_COLUMN + " = ?", name);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Company
			result = (Company)((Company)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new CompanyNotFoundException();
		
		return result;
	}
	
	public static List<Company> getAll() throws SQLException {
		HibernateQuery hquery;
		List<?> resultset;
		List<Company> result = new ArrayList<Company>();
		
		hquery = new HibernateQuery("FROM " + TABLE);
		resultset = ConnectionManager.query(hquery);
		
		// Clone read companies
		for (Object obj : resultset) {
			result.add((Company) ((Company)obj).clone());
		}
		ConnectionManager.freeResultset(resultset);
	
		return result;
	}
	
	public static void delete (Company c) {
		System.err.println("CompanyDAO.delete(Company c) is not implemented yet.");
	}
	
}
