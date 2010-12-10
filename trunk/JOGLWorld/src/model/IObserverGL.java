package model;

import java.io.IOException;

import exceptions.gl.GLSingletonNotInitializedException;

public interface IObserverGL {
	public void updateMapChanged() throws GLSingletonNotInitializedException, IOException;
}
