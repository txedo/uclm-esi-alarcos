package model.gl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import exceptions.ViewManagerNotInstantiatedException;

import model.gl.control.EViewLevels;
import model.gl.control.GLFactoryViewManager;
import model.gl.control.GLProjectViewManager;
import model.gl.control.GLTowerViewManager;
import model.gl.control.GLViewManager;
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
	
	private void configureCaption (GLViewManager glvm, JSONObject jsonCaptionLines) {
		Caption caption;
		if (jsonCaptionLines != null) {
			caption = new Caption();
			Iterator it = jsonCaptionLines.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
				caption.addLine(new Color((String)pairs.getValue()), (String)pairs.getKey());
			}
		    if (caption.getLines().size() > 0) glvm.setCaption(caption);
		}
	}
	
	@Override
	public void visualizeBuildings(String JSONcity) throws ViewManagerNotInstantiatedException {
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONcity);
		List<GLObject> buildings = new ArrayList<GLObject>();
		GLFactory building;
		List<Neighborhood> nbh = new ArrayList<Neighborhood>();
		City city;

		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			buildings = new ArrayList<GLObject>();
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				building = new GLFactory();
				building.setId(jobj.getInt("id"));
				building.setSmokestackHeight(jobj.getInt("smokestackHeight"));
				float r = (float)(jobj.getJSONObject("smokestackColor")).getDouble("r");
				float g = (float)(jobj.getJSONObject("smokestackColor")).getDouble("g");
				float b = (float)(jobj.getJSONObject("smokestackColor")).getDouble("b");
				float alpha = (float)(jobj.getJSONObject("smokestackColor")).getDouble("alpha");
				Color color = new Color(r,g,b);
				color.setAlpha(alpha);
				building.setSmokestackColor(color);
				building.setScale((float)jobj.getDouble("scale"));
				buildings.add(building);
			}
			// Build the neighborhood
			nbh.add(new Neighborhood(jsonFlatsObject.getString("name"), buildings));
		}
		// Configure caption lines
		configureCaption(GLProjectViewManager.getInstance(), json.getJSONObject("captionLines"));
		
		// Build the city
		city = new City(nbh);
		city.placeNeighborhoods();
		
		buildings = new ArrayList<GLObject>();
		for (Neighborhood n : nbh) {
			buildings.addAll(n.getFlats());
		}
		// Change the active view to FactoryLevel
		GLFactoryViewManager.getInstance().setPavements(city.getPavements());
		GLFactoryViewManager.getInstance().setItems(buildings);
		GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.FactoryLevel);
	}

	@Override
	public void visualizeAntennaBalls(String JSONtext) throws ViewManagerNotInstantiatedException {
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONtext);
		List<GLObject> antennaBalls = new ArrayList<GLObject>();
		GLAntennaBall antennaBall;
		List<Neighborhood> nbh = new ArrayList<Neighborhood>();
		City city;
		float maxSize = 0.0f;

		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			antennaBalls = new ArrayList<GLObject>();
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				antennaBall = new GLAntennaBall();
				antennaBall.setId(jobj.getInt("id"));
				antennaBall.setLabel(jobj.getString("label"));
				antennaBall.setProgressionMark(jobj.getBoolean("progressionMark"));
				antennaBall.setLeftChildBallValue(jobj.getInt("rightChildBallValue"));
				antennaBall.setRightChildBallValue(jobj.getInt("leftChildBallValue"));
				float r = (float)(jobj.getJSONObject("color")).getDouble("r");
				float g = (float)(jobj.getJSONObject("color")).getDouble("g");
				float b = (float)(jobj.getJSONObject("color")).getDouble("b");
				float alpha = (float)(jobj.getJSONObject("color")).getDouble("alpha");
				Color color = new Color(r,g,b);
				color.setAlpha(alpha);
				antennaBall.setColor(color);
				float size = (float)jobj.getDouble("parentBallRadius");
				antennaBall.setParentBallRadius(size);
				if (maxSize < size) maxSize = size;
				antennaBalls.add(antennaBall);
			}
			// Build the neighborhood
			nbh.add(new Neighborhood(jsonFlatsObject.getString("name"), antennaBalls));
		}
		// Normalize project size
		for (Neighborhood n : nbh) {
			for (GLObject o : n.getFlats()) {
				((GLAntennaBall)o).setParentBallRadius(((GLAntennaBall)o).getParentBallRadius()*GLAntennaBall.MAX_SIZE/maxSize);
			}
		}
		// Configure caption lines
		configureCaption(GLProjectViewManager.getInstance(), json.getJSONObject("captionLines"));
	 
		// Build the city
		city = new City(nbh);
		city.placeNeighborhoods();
		
		antennaBalls = new ArrayList<GLObject>();
		for (Neighborhood n : nbh) {
			antennaBalls.addAll(n.getFlats());
		}
		// Change the active view to ProjectLevel
		GLProjectViewManager.getInstance().setPavements(city.getPavements());
		GLProjectViewManager.getInstance().setItems(antennaBalls);
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
				tower.setInnerHeight((float)jobj.getDouble("fill"));
				tower.setColor(new Color(jobj.getString("color")));
				towers.add(tower);
			}
			// Build the neighborhood
			nbh.add(new Neighborhood(jsonFlatsObject.getString("name"), towers));
		}
		// Configure caption lines
		configureCaption(GLProjectViewManager.getInstance(), json.getJSONObject("captionLines"));
		
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
