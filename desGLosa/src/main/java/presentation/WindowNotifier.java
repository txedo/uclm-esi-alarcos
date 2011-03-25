package presentation;

import java.util.ArrayList;

public class WindowNotifier {
	private static ArrayList<WindowNotifierObserverInterface> observers = new ArrayList<WindowNotifierObserverInterface>();

	public static void attach (WindowNotifierObserverInterface ob) {
		observers.add(ob);
	}
	
	public static void clear () {
		observers.clear();
	}
	
	public static void executeOperation(WindowNotifierOperationCodes code, Object... objects) {
		for (WindowNotifierObserverInterface wnoi : observers) {
			wnoi.manageOperation(code, objects);
		}
	}
}
