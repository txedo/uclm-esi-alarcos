package model;

import exceptions.ViewManagerNotInstantiatedException;

public interface IGLFacade {
	public void visualizeBuildings (String JSONtext) throws ViewManagerNotInstantiatedException;
	public void visualizeAntennaBalls (String JSONtext) throws ViewManagerNotInstantiatedException;
	public void visualizeTowers (String JSONtext) throws ViewManagerNotInstantiatedException;
}
