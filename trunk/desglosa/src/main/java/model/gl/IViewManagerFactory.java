package model.gl;

import model.gl.control.GLViewManager;

public interface IViewManagerFactory {
	public GLViewManager createMetricIndicatorViewManager(GLDrawer drawer);
	public GLViewManager createProjectViewManager(GLDrawer drawer);
	public GLViewManager createFactoryViewManager(GLDrawer drawer);
	public GLViewManager createTowerViewManager(GLDrawer drawer);
}