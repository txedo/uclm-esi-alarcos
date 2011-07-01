package model.gl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.ViewManagerNotInstantiatedException;

import model.gl.control.EViewLevels;
import model.gl.control.GLFactoryViewManager;
import model.gl.control.GLProjectViewManager;
import model.gl.control.GLTowerViewManager;
import model.gl.knowledge.GLAntennaBall;
import model.gl.knowledge.GLFactory;
import model.gl.knowledge.GLObject;
import model.gl.knowledge.GLTower;
import model.gl.knowledge.caption.Caption;
import model.util.City;
import model.util.Color;
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
		// Change the active view to FactoryLevel
		GLFactoryViewManager.getInstance().setPavements(city.getPavements());
		GLFactoryViewManager.getInstance().setItems(factories);
		GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.FactoryLevel);
	}

	@Override
	public void visualizeProjects(String JSONtext) throws ViewManagerNotInstantiatedException {
		Caption caption = new Caption();
		Map<String, String> captionLines = new HashMap<String, String>();
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONtext);
		List<GLObject> projects = new ArrayList<GLObject>();
		GLAntennaBall project;
		List<Neighborhood> nbh = new ArrayList<Neighborhood>();
		City city;
		float maxSize = 0.0f;

		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			projects = new ArrayList<GLObject>();
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				project = new GLAntennaBall();
				project.setId(jobj.getInt("id"));
				project.setLabel(jobj.getString("name"));
				project.setProgression(jobj.getBoolean("audited"));
				int incidences = jobj.getInt("totalIncidences");
				int repairedIncidences = jobj.getInt("repairedIncidences");
				project.setLeftChildBallValue(repairedIncidences);
				project.setRightChildBallValue(incidences - repairedIncidences);
				project.setColor(new Color(jobj.getJSONObject("market").getString("color")));
				float size = (float)jobj.getDouble("size");
				project.setParentBallRadius(size);
				if (maxSize < size) maxSize = size;
				captionLines.put(jobj.getJSONObject("market").getString("name"), jobj.getJSONObject("market").getString("color"));
				projects.add(project);
			}
			// Build the neighborhood
			nbh.add(new Neighborhood(projects));
		}
		// Normalize project size
		for (Neighborhood n : nbh) {
			for (GLObject o : n.getFlats()) {
				((GLAntennaBall)o).setParentBallRadius(((GLAntennaBall)o).getParentBallRadius()*GLAntennaBall.MAX_SIZE/maxSize);
			}
		}
		// Configure caption lines
	    Iterator it = captionLines.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        caption.addLine(new Color((String)pairs.getValue()), (String)pairs.getKey());
	    }
	    if (caption.getLines().size() > 0) GLProjectViewManager.getInstance().setCaption(caption);

		// Build the city
		city = new City(nbh);
		city.placeNeighborhoods();
		
		projects = new ArrayList<GLObject>();
		for (Neighborhood n : nbh) {
			projects.addAll(n.getFlats());
		}
		// Change the active view to ProjectLevel
		GLProjectViewManager.getInstance().setPavements(city.getPavements());
		GLProjectViewManager.getInstance().setItems(projects);
		GLProjectViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.ProjectLevel);
	}

	@Override
	public void visualizeTowers(String JSONtext) throws ViewManagerNotInstantiatedException {
		List<GLObject> towers = new ArrayList<GLObject>();
		GLTower tower;
		List<Neighborhood> nbh = new ArrayList<Neighborhood>();
		City city;
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONtext);
		
		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			towers = new ArrayList<GLObject>();
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				tower = new GLTower();
				tower.setId(jobj.getInt("id"));
				tower.setDepth((float)jobj.getDouble("depth"));
				tower.setHeight((float)jobj.getDouble("height"));
				tower.setWidth((float)jobj.getDouble("width"));
				tower.setFill((float)jobj.getDouble("fill"));
				tower.setColor(new Color(jobj.getString("color")));
				towers.add(tower);
			}
			// Build the neighborhood
			nbh.add(new Neighborhood(towers));
		}
		
		// Build the city
		city = new City(nbh);
		city.placeNeighborhoods();
		
		towers = new ArrayList<GLObject>();
		for (Neighborhood n : nbh) {
			towers.addAll(n.getFlats());
		}
		
		// Change the active view to TowerLevel		
		GLTowerViewManager.getInstance().setPavements(city.getPavements());
		GLTowerViewManager.getInstance().setItems(towers);
		GLTowerViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.TowerLevel);
	}

}
