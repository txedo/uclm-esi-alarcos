package presentation;

import java.io.IOException;
import java.sql.SQLException;

import model.business.control.BusinessManager;
import model.business.knowledge.Image;
import model.business.knowledge.Map;

import persistence.ResourceRetriever;
import persistence.database.ConnectionManager;
import persistence.database.DBConnection;
import persistence.database.IDBConnection;
import exceptions.ImageNotFoundException;
import exceptions.MapNotFoundException;

public class FirstRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a new database connection
		IDBConnection idb = new DBConnection("mysql server", "127.0.0.1", 3306, "desglosadb");
		if (!((DBConnection)idb).test()) {
			System.err.println("Cannot connect to the database server.");
		}
		// Add it to the ConnectionManager
		ConnectionManager.addConnection(idb);
		try {
			// Add data for the default contact image
			Image defaultContactImage = BusinessManager.getImage(1);
			defaultContactImage.setData(ResourceRetriever.getResourceAsByteArray("src/main/resources/anonymous.jpg"));
			BusinessManager.updateImage(defaultContactImage);
			// Add map data
			// world map
			Map worldMap = BusinessManager.getMap(1);
			worldMap.getImage().setData(ResourceRetriever.getResourceAsByteArray("src/main/resources/maps/dark-world-map.png"));
			BusinessManager.updateImage(worldMap.getImage());
			// spain
			Map spain = BusinessManager.getMap(2);
			spain.getImage().setData(ResourceRetriever.getResourceAsByteArray("src/main/resources/maps/dark-spain-map.png"));
			BusinessManager.updateImage(spain.getImage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MapNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
