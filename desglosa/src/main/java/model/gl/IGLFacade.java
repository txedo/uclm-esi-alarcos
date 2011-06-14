package model.gl;

import exceptions.ViewManagerNotInstantiatedException;

public interface IGLFacade {
	public void visualizeFactories (String JSONtext) throws ViewManagerNotInstantiatedException;
}
