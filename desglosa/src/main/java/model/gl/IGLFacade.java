package model.gl;

import exceptions.ViewManagerNotInstantiatedException;

public interface IGLFacade {
	public void visualizeFactories (String JSONtext) throws ViewManagerNotInstantiatedException;
	public void visualizeProjects (String JSONtext) throws ViewManagerNotInstantiatedException;
}
