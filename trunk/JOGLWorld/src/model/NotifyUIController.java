package model;

import java.util.ArrayList;


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

	public static void notifyMapListUpdate() {
		for (IObserverUI iob : observers) {
			iob.updateMapList();
		}
	}
}
