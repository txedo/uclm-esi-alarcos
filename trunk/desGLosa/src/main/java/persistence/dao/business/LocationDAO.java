package persistence.dao.business;

import java.sql.SQLException;
import java.util.List;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;
import exceptions.LocationNotFoundException;
import model.business.knowledge.Factory;
import model.business.knowledge.Location;
import model.business.knowledge.Map;

public class LocationDAO {
	private static final String TABLE = "Location";
	private static final String FACTORY_ID_COLUMN = "factory.id";
	private static final String MAP_ID_COLUMN = "map.id";

	public static void insert(Location loc) throws SQLException {
		try {
			ConnectionManager.beginTransaction();
			ConnectionManager.insert(loc.clone());
		} finally {
			ConnectionManager.endTransaction();
		}
	}

	public static Location get(Factory factory, Map map) throws LocationNotFoundException, SQLException {
		HibernateQuery hquery;
		List<?> resultset;
		Location result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + FACTORY_ID_COLUMN + " = ? AND " + MAP_ID_COLUMN + " = ?", factory.getId(), map.getId());
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Company
			result = (Location)((Location)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new LocationNotFoundException();
		
		return result;
	}

}
