package persistence.dao.business;

import java.sql.SQLException;
import java.util.List;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;
import exceptions.ImageNotFoundException;
import model.business.knowledge.Image;

public class ImageDAO {
	private static final String TABLE = "Image";
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "filename";
	
	public static Image get(String filename) throws SQLException, ImageNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Image result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + NAME_COLUMN + " = ?", filename);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Image
			result = (Image)((Image)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new ImageNotFoundException();
		
		return result;
	}

	public static Image get(int id) throws SQLException, ImageNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Image result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + ID_COLUMN + " = ?", id);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Image
			result = (Image)((Image)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new ImageNotFoundException();
		
		return result;
	}

	public static void update(Image image) throws SQLException {
		try {
			ConnectionManager.beginTransaction();
			ConnectionManager.update(image.clone());
		} finally {
			ConnectionManager.endTransaction();
		}
	}

}
