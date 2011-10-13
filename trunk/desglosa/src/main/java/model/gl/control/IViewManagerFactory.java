package model.gl.control;

import model.gl.GLDrawer;

public interface IViewManagerFactory {
	public GLViewManager createProjectViewManager(GLDrawer drawer);
	public GLViewManager createFactoryViewManager(GLDrawer drawer);
	public GLViewManager createTowerViewManager(GLDrawer drawer);
}
