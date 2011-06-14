package model;

import java.util.ArrayList;

import model.util.Vector2f;


public class NotifyUIManager {
	private static ArrayList<IObserverUI> observers = new ArrayList<IObserverUI>();

	public static void attach (IObserverUI ob) {
		observers.add(ob);
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
