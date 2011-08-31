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
import model.gl.knowledge.GLPavement;
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
			buildings = new ArrayList<GLObject>();
			for (int j = 0; j < jsonFlats.size(); j++) {
				JSONObject jobj = jsonFlats.getJSONObject(j);
				GLFactory building = new GLFactory();
				readJSONGLObjectProperties(jobj, building);
				building.setSmokestackHeight(jobj.getInt("smokestackHeight"));
				building.setSmokestackColor(readJSONColor(jobj));
				buildings.add(building);
				tmpBuildings.add(building);
			}
			city.getNeighborhoods().add(new Neighborhood(tmpBuildings));
		}
		
		// Place flats and neighborhoods once normalized
		city.placeNeighborhoods();
		
		// Configure caption lines
		configureCaption(GLProjectViewManager.getInstance(), json.getJSONObject("captionLines"));
		
		// Change the active view to FactoryLevel
		GLFactoryViewManager.getInstance().setPavements(configurePavements(json.getJSONArray("pavements")));
		GLFactoryViewManager.getInstance().setItems(buildings);
		GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.FactoryLevel);
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
				antennaBall.setColor(readJSONColor(jobj));
				float size = (float)jobj.getDouble("parentBallRadius");
				antennaBall.setParentBallRadius(size);
				if (maxSize < size) maxSize = size;
				antennaBalls.add(antennaBall);
				tmpAntennaBalls.add(antennaBall);
				// Get some dimension values for later normalization
				if (antennaBall.getParentBallRadius() > maxSize) maxSize = antennaBall.getParentBallRadius();
			}
			city.getNeighborhoods().add(new Neighborhood(tmpAntennaBalls));
		}
		
		// Normalize
		for (GLObject glAB :antennaBalls) {
			((GLAntennaBall)glAB).setParentBallRadius(((GLAntennaBall)glAB).getParentBallRadius()*GLAntennaBall.MAX_SIZE/maxSize);
		}
		
		// Place flats and neighborhoods once normalized
		city.placeNeighborhoods();
		
		// Configure caption lines
		configureCaption(GLProjectViewManager.getInstance(), json.getJSONObject("captionLines"));

		// Change the active view to ProjectLevel
		GLProjectViewManager.getInstance().setPavements(configurePavements(json.getJSONArray("pavements")));
		GLProjectViewManager.getInstance().setItems(antennaBalls);
		GLProjectViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.ProjectLevel);
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
				tower.setColor(readJSONColor(jobj));
				towers.add(tower);
				tmpTowers.add(tower);
				// Get some dimension values for later normalization
				if (tower.getDepth() > maxDepth) maxDepth = tower.getDepth();
				if (tower.getWidth() > maxWidth) maxWidth = tower.getWidth();
				if (tower.getHeight() > maxHeight) maxHeight = tower.getHeight();
				if (tower.getInnerHeight() > maxInnerHeight) maxInnerHeight = tower.getInnerHeight();
			}
			city.getNeighborhoods().add(new Neighborhood(tmpTowers));
		}
		
		// Normalize
		for (GLObject glTower : towers) {
			((GLTower)glTower).setHeight(((GLTower)glTower).getHeight()*GLTower.MAX_HEIGHT/maxHeight);
			((GLTower)glTower).setInnerHeight(((GLTower)glTower).getInnerHeight()*GLTower.MAX_HEIGHT/maxInnerHeight);
			((GLTower)glTower).setWidth(((GLTower)glTower).getWidth()*GLTower.MAX_WIDTH/maxWidth);
			((GLTower)glTower).setDepth(((GLTower)glTower).getDepth()*GLTower.MAX_DEPTH/maxDepth);
		}
		
		// Place flats and neighborhoods once normalized
		city.placeNeighborhoods();
		
		// Configure caption lines
		configureCaption(GLProjectViewManager.getInstance(), json.getJSONObject("captionLines"));
		
		// Change the active view to TowerLevel		
		GLTowerViewManager.getInstance().setPavements(configurePavements(json.getJSONArray("pavements")));
		GLTowerViewManager.getInstance().setItems(towers);
		GLTowerViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.TowerLevel);
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
	
	private List<GLObject> configurePavements(JSONArray jsonPavements) {
		List<GLObject> pavements = new ArrayList<GLObject>();
		for (int i = 0; i< jsonPavements.size(); i++) {
			JSONObject jsonPavementObject = jsonPavements.getJSONObject(i);
			GLPavement glPav = new GLPavement();
			readJSONGLObjectProperties(jsonPavementObject, glPav);
			glPav.setDepth((float)jsonPavementObject.getDouble("depth"));
			glPav.setWidth((float)jsonPavementObject.getDouble("width"));
			glPav.setTitle(jsonPavementObject.getString("title"));
			// Add the new pavement to the list
			pavements.add(glPav);
		}
		return pavements;
	}
	
	private void readJSONGLObjectProperties (JSONObject jsonObj, GLObject glObj) {
		glObj.setId(jsonObj.getInt("id"));
		glObj.setPositionX((float)jsonObj.getDouble("positionX"));
		glObj.setPositionZ((float)jsonObj.getDouble("positionZ"));
		glObj.setScale((float)jsonObj.getDouble("scale"));
	}
	
	private Color readJSONColor (JSONObject jsonObj) {
		float r = (float)(jsonObj.getJSONObject("color")).getDouble("r");
		float g = (float)(jsonObj.getJSONObject("color")).getDouble("g");
		float b = (float)(jsonObj.getJSONObject("color")).getDouble("b");
		float alpha = (float)(jsonObj.getJSONObject("color")).getDouble("alpha");
		Color color = new Color(r,g,b);
		color.setAlpha(alpha);
		return color;
	}

}
