package model;


import org.jdesktop.application.Application;

import persistence.database.ConnectionManager;
import persistence.database.DBConnection;
import persistence.database.IDBConnection;
import presentation.JFMain;


/**
 * http://radio-weblogs.com/0122027/stories/2003/10/20/
 * implementingTheSingletonPatternInJava.html
 * 
 * A utility class of which at most one instance can exist per VM.
 * 
 * Use Singleton.instance() to access this instance.
 */
public class MainManager {
	/**
	 * A handle to the unique Singleton instance.
	 */
	static private MainManager _instance = null;

	/**
	 * The constructor could be made private to prevent others from
	 * instantiating this class. But this would also make it impossible to
	 * create instances of Singleton subclasses.
	 */
	protected MainManager() {}

	/**
	 * @return The unique instance of this class.
	 */
	static public MainManager getInstance() {
		if (null == _instance) {
			_instance = new MainManager();
		}
		return _instance;
	}
	
	public void configure () {
		// Create a new database connection
		IDBConnection idb = new DBConnection("mysql server", "127.0.0.1", 3306, "desglosadb");
		if (!((DBConnection)idb).test()) {
			System.err.println("Cannot connect to the database server.");
		}
		// Add it to the ConnectionManager
		ConnectionManager.addConnection(idb);
	}

	public void run(String[] args) {
		// Launch the GUI
		Application.launch(JFMain.class, args);
	}
	
}
