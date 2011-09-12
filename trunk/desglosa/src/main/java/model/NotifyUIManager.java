package model;

import java.util.ArrayList;

public class NotifyUIManager {
	private static ArrayList<IObserverUI> observers = new ArrayList<IObserverUI>();

	public static void attach (IObserverUI ob) {
		observers.add(ob);
	}
	
	public static void notifySelectedProject (int idProject, int clickButton, int clickCount) {
		for (IObserverUI iob : observers) {
			iob.selectAntennaBall(idProject, clickButton, clickCount);
		}
	}
	
	public static void notifySelectedFactory (int idFactory, int clickButton, int clickCount) {
		for (IObserverUI iob : observers) {
			iob.selectBuilding(idFactory, clickButton, clickCount);
		}
	}
	
	public static void notifySelectedTower (int idTower, int clickButton, int clickCount) {
		for (IObserverUI iob : observers) {
			iob.selectTower(idTower, clickButton, clickCount);
		}
	}

}
