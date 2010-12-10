package model;

import java.io.IOException;
import java.util.ArrayList;

import exceptions.gl.GLSingletonNotInitializedException;

public class NotifyGLController {
	private static ArrayList<IObserverGL> observers = new ArrayList<IObserverGL>();
	
	public static void attach (IObserverGL ob) {
		observers.add(ob);
	}
	
	public static void notifyMapChanged () throws GLSingletonNotInitializedException, IOException {
		for (IObserverGL iob : observers) {
			iob.updateMapChanged();
		}
	}
}
