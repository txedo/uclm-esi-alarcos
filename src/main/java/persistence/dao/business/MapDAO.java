package persistence.dao.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.MapNotFoundException;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;

import model.business.knowledge.Map;


public class MapDAO {
	private static final String TABLE = "Map";
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "label";

	public static void insert(Map map) throws SQLException {
		try {
			ConnectionManager.beginTransaction();
			ConnectionManager.insert(map.clone());
		} finally {
			ConnectionManager.endTransaction();
		}
	}
	
	public static Map get(int id) throws SQLException, MapNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Map result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + ID_COLUMN + " = ?", id);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Factory
			result = (Map)((Map)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new MapNotFoundException();
		
		return result;
	}

	public static Map get(String name) throws SQLException, MapNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Map result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + NAME_COLUMN+ " = ?", name);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Factory
			result = (Map)((Map)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new MapNotFoundException();
		
		return result;
	}

	public static List<Map> getAll() throws SQLException {
		HibernateQuery hquery;
		List<?> resultset;
		List<Map> result = new ArrayList<Map>();
		
		hquery = new HibernateQuery("FROM " + TABLE);
		resultset = ConnectionManager.query(hquery);
		
		// Clone read factories
		for (Object obj : resultset) {
			result.add((Map) ((Map)obj).clone());
		}
		ConnectionManager.freeResultset(resultset);
	
		return result;
	}
	
	public static void delete (Map m) {
		System.err.println("MapDAO.delete(Map m) is not implemented yet.");
	}
	
}
