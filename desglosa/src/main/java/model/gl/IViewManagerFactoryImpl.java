package model.gl;

import model.gl.control.GLFactoryViewManager;
import model.gl.control.GLProjectViewManager;
import model.gl.control.GLTowerViewManager;
import model.gl.control.GLViewManager;

public class IViewManagerFactoryImpl implements IViewManagerFactory {
	static private IViewManagerFactoryImpl _instance = null;
	
	protected IViewManagerFactoryImpl() {}
	
	/**
	 * @return The unique instance of this class.
	 */
	static public IViewManagerFactoryImpl getInstance() {
		if (null == _instance) {
			_instance = new IViewManagerFactoryImpl();
		}
		return _instance;
	}

	public GLViewManager createProjectViewManager(GLDrawer drawer) {
		GLViewManager res = new GLProjectViewManager(drawer, true);	// 3D View
		res.setShadowSupport(true);
		return res;
	}

	public GLViewManager createFactoryViewManager(GLDrawer drawer) {
		GLViewManager res = new GLFactoryViewManager(drawer, true);	// 3D View
		res.setShadowSupport(true);
		return res;
	}

	public GLViewManager createTowerViewManager(GLDrawer drawer) {
		GLViewManager res = new GLTowerViewManager(drawer, true);	// 3D View
		res.setShadowSupport(true);
		return res;
	}
	
}
