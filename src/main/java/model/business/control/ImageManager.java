package model.business.control;

import java.sql.SQLException;

import exceptions.ImageNotFoundException;
import persistence.dao.business.ImageDAO;
import model.business.knowledge.Image;

public class ImageManager {

	public static Image getImage(int id) throws SQLException, ImageNotFoundException {
		Image result = ImageDAO.get(id);
		return result;
	}

	public static boolean updateImage(Image image) throws SQLException, ImageNotFoundException {
		boolean success = false;
		// Check that the image already exists
		ImageDAO.get(image.getId());
		ImageDAO.update(image);
		success = true;
		
		return success;
	}

}
