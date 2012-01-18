package model;

import java.util.ArrayList;
import java.util.logging.Logger;

public class NotifyUIManager {
    private final static Logger log = Logger.getAnonymousLogger();
    private static ArrayList<IObserverUI> observers = new ArrayList<IObserverUI>();

    public static void attach(IObserverUI ob) {
        observers.add(ob);
    }

    public static void notifyMessage(String message) {
        log.info("sending messages to observers...");
        for (IObserverUI iob : observers) {
            iob.showMessage(message);
        }
        log.info("messages to observers are sent.");
    }

    public static void notifySelectedAntennaBall(int idProject, int clickButton, int clickCount) {
        log.info("notifying selected antenna ball to observers...");
        for (IObserverUI iob : observers) {
            iob.selectAntennaBall(idProject, clickButton, clickCount);
        }
        log.info("selected antenna ball notified.");
    }

    public static void notifySelectedBuilding(int idFactory, int clickButton, int clickCount) {
        log.info("notifying selected building to observers...");
        for (IObserverUI iob : observers) {
            iob.selectBuilding(idFactory, clickButton, clickCount);
        }
        log.info("selected building notified.");
    }

    public static void notifySelectedTower(int idTower, int clickButton, int clickCount) {
        log.info("notifying selected tower to observers...");
        for (IObserverUI iob : observers) {
            iob.selectTower(idTower, clickButton, clickCount);
        }
        log.info("selected tower notified.");
    }

}
