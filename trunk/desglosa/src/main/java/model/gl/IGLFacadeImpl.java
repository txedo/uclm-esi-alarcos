package model.gl;

import java.util.ArrayList;
import java.util.List;

import exceptions.ViewManagerNotInstantiatedException;

import model.gl.control.EViewLevels;
import model.gl.control.GLFactoryViewManager;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;
import model.util.City;
import model.util.Neighborhood;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class IGLFacadeImpl implements IGLFacade {

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
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONtext);
		List<GLObject> factories = new ArrayList<GLObject>();
		GLFactory factory;
		List<Neighborhood> nbh = new ArrayList<Neighborhood>();
		City city;

		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			factories = new ArrayList<GLObject>();
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				factory = new GLFactory();
				factory.setId(jobj.getInt("id"));
				factory.setSmokestackHeight(jobj.getInt("projects"));
				int employees = jobj.getInt("employees");
				float scale = GLFactory.SMALL;
				if (employees < 150) scale = GLFactory.MEDIUM;
				else if (employees > 500) scale = GLFactory.BIG;
				factory.setScale(scale);
				factories.add(factory);
			}
			// Build the neighborhood
			nbh.add(new Neighborhood(factories));
		}
		// Build the city
		city = new City(nbh);
		city.placeNeighborhoods();
		
		factories = new ArrayList<GLObject>();
		for (Neighborhood n : nbh) {
			factories.addAll(n.getFlats());
		}
		GLFactoryViewManager.getInstance().setItems(factories);
		GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.FactoryLevel);
	}

}
