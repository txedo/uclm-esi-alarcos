package model;

import java.util.ArrayList;

import model.business.knowledge.Centre;
import model.knowledge.Vector2f;


public class NotifyUIController {
	private static ArrayList<IObserverUI> observers = new ArrayList<IObserverUI>();

	public static void attach (IObserverUI ob) {
		observers.add(ob);
	}
	
	public static void notifyCompanyListUpdate () {
		for (IObserverUI iob : observers) {
			iob.updateCompanyList();
		}
	}
	
	public static void notifyFactoryListUpdate (int companyId) {
		for (IObserverUI iob : observers) {
			iob.updateFactoryList(companyId);
		}
	}

	public static void notifyMapListUpdate() {
		for (IObserverUI iob : observers) {
			iob.updateMapList();
		}
	}
	
	public static void notifyClickedWorldCoords (Vector2f coordinates) {
		for (IObserverUI iob : observers) {
			iob.updateClickedWorldCoords(coordinates);
		}
	}
	
	public static void notifySelectedCentre (Centre c) {
		for (IObserverUI iob : observers) {
			iob.selectCentre(c);
		}
	}
}
