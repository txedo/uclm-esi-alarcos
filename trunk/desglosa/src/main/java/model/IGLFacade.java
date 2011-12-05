package model;

import exceptions.ViewManagerNotInstantiatedException;

public interface IGLFacade {
    void visualizeBuildings(String jsonCity)
            throws ViewManagerNotInstantiatedException;

    void visualizeAntennaBalls(String jsonCity)
            throws ViewManagerNotInstantiatedException;

    void visualizeTowers(String jsonCity)
            throws ViewManagerNotInstantiatedException;
}
