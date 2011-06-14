package model.gl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import exceptions.ViewManagerNotInstantiatedException;

import model.gl.control.EViewLevels;
import model.gl.control.GLFactoryViewManager;
import model.gl.knowledge.GLFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class IGLFacadeImpl implements IGLFacade {
	protected final Log log = LogFactory.getLog(getClass());
	
	static private IGLFacadeImpl _instance = null;
	
	protected IGLFacadeImpl() {}
	
	/**
	 * @return The unique instance of this class.
	 */
	static public IGLFacadeImpl getInstance() {
		if (null == _instance) {
			_instance = new IGLFacadeImpl();
		}
		return _instance;
	}
	
	@Override
	public void visualizeFactories(String JSONtext) throws ViewManagerNotInstantiatedException {
		log.debug("entering in IGLFacadeImpl.visualizeFactories");
		log.debug("will parse this text: " + JSONtext);
		
		JSONArray json = (JSONArray)JSONSerializer.toJSON(JSONtext);
		List<GLFactory> factories = new ArrayList<GLFactory>();

		for (int i = 0; i < json.size(); i++) {
			GLFactory factory = new GLFactory();
			JSONObject jobj = json.getJSONObject(i);
			factory.setId(jobj.getInt("id"));
			factory.setNeightborhood(jobj.getInt("neighborhood"));
			factory.setSmokestackHeight(jobj.getInt("smokestack"));
			int employees = jobj.getInt("employees");
			double scale = 1.0;
			if (employees < 150) scale = 0.75;
			else if (employees > 500) scale = 1.25;
			factory.setScale(scale);
			factories.add(factory);
		}
		
		log.debug("all factories created");
		log.debug("will set factories in GLFactoryViewManager");
		
		GLFactoryViewManager.getInstance().setItems(factories);
		
		log.debug("factories are set up");
		log.debug("will set factory level view");
		
		GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.FactoryLevel);
		
		log.debug("factory level set up");
	}

}
