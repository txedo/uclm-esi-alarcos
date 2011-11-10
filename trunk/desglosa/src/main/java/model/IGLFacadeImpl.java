package model;

import java.util.ArrayList;
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
	public void visualizeBuildings(String JSONcity) throws ViewManagerNotInstantiatedException {
		City city = new City();
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONcity);
		List<GLObject> buildings = new ArrayList<GLObject>();
		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			List<GLObject> tmpBuildings = new ArrayList<GLObject>();
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				GLFactory building = new GLFactory();
				readJSONGLObjectProperties(jobj, building);
				building.setSmokestackHeight(jobj.getInt("smokestackHeight"));
				building.setSmokestackColor(readJSONColor(jobj, "smokestackColor"));
				buildings.add(building);
				tmpBuildings.add(building);
			}
			city.getNeighborhoods().add(new Neighborhood(jsonFlatsObject.getString("name"), tmpBuildings));
		}
		
		// Place flats and neighborhoods once normalized
		city.placeNeighborhoods(GLFactory.MAX_HEIGHT);
		
		// Configure caption lines
		Caption caption = configureCaption(json.getJSONObject("captionLines"));
		if (caption != null) GLFactoryViewManager.getInstance().setCaption(caption);
		
		// Change the active view to BuildingLevel
		GLFactoryViewManager.getInstance().setPavements(city.getPavements());
		GLFactoryViewManager.getInstance().setItems(buildings);
		GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.BuildingLevel);
	}

	@Override
	public void visualizeAntennaBalls(String JSONtext) throws ViewManagerNotInstantiatedException {
		float maxSize = 0.0f;
		
		City city = new City();
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONtext);
		List<GLObject> antennaBalls = new ArrayList<GLObject>();

		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			List<GLObject> tmpAntennaBalls = new ArrayList<GLObject>();
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				GLAntennaBall antennaBall = new GLAntennaBall();
				readJSONGLObjectProperties(jobj, antennaBall);
				antennaBall.setLabel(jobj.getString("label"));
				antennaBall.setProgressionMark(jobj.getBoolean("progressionMark"));
				antennaBall.setLeftChildBallValue(jobj.getInt("rightChildBallValue"));
				antennaBall.setRightChildBallValue(jobj.getInt("leftChildBallValue"));
				antennaBall.setColor(readJSONColor(jobj, "color"));
				float size = (float)jobj.getDouble("parentBallRadius");
				antennaBall.setParentBallRadius(size);
				if (maxSize < size) maxSize = size;
				antennaBalls.add(antennaBall);
				tmpAntennaBalls.add(antennaBall);
				// Get some dimension values for later normalization
				if (antennaBall.getParentBallRadius() > maxSize) maxSize = antennaBall.getParentBallRadius();
			}
			city.getNeighborhoods().add(new Neighborhood(jsonFlatsObject.getString("name"), tmpAntennaBalls));
		}
		
		// Normalize
		for (GLObject glAB :antennaBalls) {
			((GLAntennaBall)glAB).setParentBallRadius(((GLAntennaBall)glAB).getParentBallRadius()*GLAntennaBall.MAX_SIZE/maxSize);
		}
		
		// Place flats and neighborhoods once normalized
		city.placeNeighborhoods(GLAntennaBall.MAX_SIZE*2);
		
		// Configure caption lines
		Caption caption = configureCaption(json.getJSONObject("captionLines"));
		if (caption != null) GLProjectViewManager.getInstance().setCaption(caption);

		// Change the active view to AntennaBallLevel
		GLProjectViewManager.getInstance().setPavements(city.getPavements());
		GLProjectViewManager.getInstance().setItems(antennaBalls);
		GLProjectViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.AntennaBallLevel);
	}

	@Override
	public void visualizeTowers(String JSONtext) throws ViewManagerNotInstantiatedException {
		float maxDepth = 0.0f;
		float maxWidth = 0.0f;
		float maxHeight = 0.0f;
		float maxInnerHeight = 0.0f;
		
		City city = new City();
		List<GLObject> towers = new ArrayList<GLObject>();
		JSONObject json = (JSONObject)JSONSerializer.toJSON(JSONtext);
		JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
		for (int i = 0; i < jsonNeighborhoods.size(); i++) {
			List<GLObject> tmpTowers = new ArrayList<GLObject>();
			JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
			JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				GLTower tower = new GLTower();
				readJSONGLObjectProperties(jobj, tower);
				tower.setDepth((float)jobj.getDouble("depth"));
				tower.setHeight((float)jobj.getDouble("height"));
				tower.setWidth((float)jobj.getDouble("width"));
				tower.setInnerHeight((float)jobj.getDouble("innerHeight"));
				tower.setColor(readJSONColor(jobj, "color"));
				towers.add(tower);
				tmpTowers.add(tower);
				// Get some dimension values for later normalization
				if (tower.getDepth() > maxDepth) maxDepth = tower.getDepth();
				if (tower.getWidth() > maxWidth) maxWidth = tower.getWidth();
				if (tower.getHeight() > maxHeight) maxHeight = tower.getHeight();
				if (tower.getInnerHeight() > maxInnerHeight) maxInnerHeight = tower.getInnerHeight();
			}
			city.getNeighborhoods().add(new Neighborhood(jsonFlatsObject.getString("name"), tmpTowers));
		}
		
		// Normalize
		for (GLObject glTower : towers) {
			((GLTower)glTower).setHeight(((GLTower)glTower).getHeight()*GLTower.MAX_HEIGHT/maxHeight);
			((GLTower)glTower).setInnerHeight(((GLTower)glTower).getInnerHeight()*GLTower.MAX_HEIGHT/maxHeight);
			((GLTower)glTower).setWidth(((GLTower)glTower).getWidth()*GLTower.MAX_WIDTH/maxWidth);
			((GLTower)glTower).setDepth(((GLTower)glTower).getDepth()*GLTower.MAX_DEPTH/maxDepth);
		}
		
		// Place flats and neighborhoods once normalized
		city.placeNeighborhoods(GLTower.MAX_HEIGHT);
		
		// Configure caption lines
		Caption caption = configureCaption(json.getJSONObject("captionLines"));
		if (caption != null) GLTowerViewManager.getInstance().setCaption(caption);
		
		// Change the active view to TowerLevel	
		GLTowerViewManager.getInstance().setPavements(city.getPavements());
		GLTowerViewManager.getInstance().setItems(towers);
		GLTowerViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.TowerLevel);
	}
	
	private Caption configureCaption (JSONObject jsonCaptionLines) {
		Caption caption = null;
		if (jsonCaptionLines != null) {
			caption = new Caption();
			Iterator it = jsonCaptionLines.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
				caption.addLine(new Color((String)pairs.getValue()), (String)pairs.getKey());
			}
		}
		return caption;
	}
	
	private void readJSONGLObjectProperties (JSONObject jsonObj, GLObject glObj) {
		glObj.setId(jsonObj.getInt("id"));
		glObj.setPositionX((float)jsonObj.getDouble("positionX"));
		glObj.setPositionZ((float)jsonObj.getDouble("positionZ"));
		glObj.setScale((float)jsonObj.getDouble("scale"));
	}
	
	private Color readJSONColor (JSONObject jsonObj, String tag) {
		float r = (float)(jsonObj.getJSONObject(tag)).getDouble("r");
		float g = (float)(jsonObj.getJSONObject(tag)).getDouble("g");
		float b = (float)(jsonObj.getJSONObject(tag)).getDouble("b");
		float alpha = (float)(jsonObj.getJSONObject(tag)).getDouble("alpha");
		Color color = new Color(r,g,b);
		color.setAlpha(alpha);
		return color;
	}

}
