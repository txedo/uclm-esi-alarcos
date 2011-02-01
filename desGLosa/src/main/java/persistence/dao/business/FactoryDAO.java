package persistence.dao.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.FactoryNotFoundException;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;

import model.business.knowledge.Factory;

public class FactoryDAO {
	private static final String TABLE = "Factory";
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";

	public static void insert(Factory factory) throws SQLException {
		try {
			ConnectionManager.beginTransaction();
			ConnectionManager.insert(factory.clone());
		} finally {
			ConnectionManager.endTransaction();
		}
	}

	public static Factory get(int id) throws SQLException, FactoryNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Factory result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + ID_COLUMN + " = ?", id);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Factory
			result = (Factory)((Factory)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new FactoryNotFoundException();
		
		return result;
	}

	public static Factory get(String name) throws SQLException, FactoryNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Factory result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + NAME_COLUMN+ " = ?", name);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Factory
			result = (Factory)((Factory)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new FactoryNotFoundException();
		
		return result;
	}

	public static List<Factory> getAll() throws SQLException {
		HibernateQuery hquery;
		List<?> resultset;
		List<Factory> result = new ArrayList<Factory>();
		
		hquery = new HibernateQuery("FROM " + TABLE);
		resultset = ConnectionManager.query(hquery);
		
		// Clone read factories
		for (Object obj : resultset) {
			result.add((Factory) ((Factory)obj).clone());
		}
		ConnectionManager.freeResultset(resultset);
	
		return result;
	}
	
	public static void delete (Factory f) {
		System.err.println("FactoryDAO.delete(Factory f) is not implemented yet.");
	}

	public static List<Factory> getFactories(String companyName) throws SQLException {
		HibernateQuery hquery;
		List<?> resultset;
		List<Factory> result = new ArrayList<Factory>();
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE company.name = ?", companyName);
		resultset = ConnectionManager.query(hquery);
		
		// Clone read factories
		for (Object obj : resultset) {
			result.add((Factory) ((Factory)obj).clone());
		}
		ConnectionManager.freeResultset(resultset);
	
		return result;
	}

}
