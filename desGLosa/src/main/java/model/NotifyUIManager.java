package model;

import java.util.ArrayList;

import model.knowledge.Vector2f;


public class NotifyUIManager {
	private static ArrayList<IObserverUI> observers = new ArrayList<IObserverUI>();

	public static void attach (IObserverUI ob) {
		observers.add(ob);
	}
	
	public static void notifyCompanyListUpdate () {
		for (IObserverUI iob : observers) {
			iob.updateCompanyList();
		}
	}
	
	public static void notifyFactoryListUpdate (int idCompany) {
		for (IObserverUI iob : observers) {
			iob.updateFactoryList(idCompany);
		}
	}

	public static void notifyMapListUpdate() {
		for (IObserverUI iob : observers) {
			iob.updateMapList();
		}
	}
	
	public static void notifyProjectListUpdate() {
		for (IObserverUI iob : observers) {
			iob.updateProjectList();
		}
	}
	
	public static void notifyClickedWorldCoords (Vector2f coordinates) {
		for (IObserverUI iob : observers) {
			iob.updateClickedWorldCoords(coordinates);
		}
	}
	
	public static void notifySelectedLocation (int idLocation) {
		for (IObserverUI iob : observers) {
			iob.selectFactoryByLocation(idLocation);
		}
	}
	
	public static void notifySelectedProject (int idProject) {
		for (IObserverUI iob : observers) {
			iob.selectProject(idProject);
		}
	}
	
	public static void notifySelectedFactory (int idFactory) {
		for (IObserverUI iob : observers) {
			iob.selectFactory(idFactory);
		}
	}
	
	public static void notifySelectedTower (int idTower) {
		for (IObserverUI iob : observers) {
			iob.selectTower(idTower);
		}
	}

}
