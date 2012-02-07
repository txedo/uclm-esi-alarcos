package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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
    private final static Logger log = Logger.getAnonymousLogger();
    static private IGLFacadeImpl _instance = null;

    protected IGLFacadeImpl() {
    }

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
    public void visualizeBuildings(String jsonCity) throws ViewManagerNotInstantiatedException {
        log.info("VisualizeBuildings() - begin");
        int maxHeight = 0;
        
        City city = new City();
        log.info("Serializing json string to json object");
        JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonCity);
        List<GLObject> buildings = new ArrayList<GLObject>();
        JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
        log.info("creating metaphore elements");
        for (int i = 0; i < jsonNeighborhoods.size(); i++) {
            List<GLObject> tmpBuildings = new ArrayList<GLObject>();
            JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
            JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
            for (int j = 0; j < jsonFlats.size(); j++) {
                JSONObject jobj = jsonFlats.getJSONObject(j);
                GLFactory building = new GLFactory();
                readJSONGLObjectProperties(jobj, building);
                int height = jobj.getInt("smokestackHeight");
                if (maxHeight < height) {
                    maxHeight = height;
                }
                building.setSmokestackHeight(height);
                building.setLabel(new Integer(height).toString());
                building.setSmokestackColor(readJSONColor(jobj, "smokestackColor"));
                buildings.add(building);
                tmpBuildings.add(building);
            }
            city.getNeighborhoods().add(new Neighborhood(jsonFlatsObject.getString("name"), tmpBuildings));
        }
        
        log.info("applying normalization process");
        // Read ratios from json city
        Map<String, Object> ratios = readRatios(json.getJSONObject("ratios"));
        float normHeight = (Float) (ratios.get("smokestackHeight") != null? new Float(ratios.get("smokestackHeight").toString()) : maxHeight);
        
        // Normalize smokestack heights
        if (normHeight > 0) {
            for (GLObject glF : buildings) {
                ((GLFactory) glF).setSmokestackHeight(((GLFactory) glF).getSmokestackHeight() * GLFactory.SMOKESTACK_MAX_HEIGHT / normHeight);
            }
        }

        log.info("processing neighborhood and flat positions");
        // Place flats and neighborhoods once normalized
        city.placeNeighborhoods(GLFactory.MAX_HEIGHT + 1.0f);

        log.info("configure caption");
        // Configure caption lines
        Caption caption = configureCaption(json.getJSONObject("captionLines"));
        if (caption != null)
            GLFactoryViewManager.getInstance().setCaption(caption);

        // Change the active view to BuildingLevel
        GLFactoryViewManager.getInstance().setPavements(city.getPavements());
        GLFactoryViewManager.getInstance().setItems(buildings);
        log.info("Setting view level to BuildingLevel");
        GLFactoryViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.BuildingLevel);
        log.info("VisualizeBuildings() - end");
    }

    @Override
    public void visualizeAntennaBalls(String jsonCity) throws ViewManagerNotInstantiatedException {
        log.info("VisualizeAntennaBalls() - begin");
        float maxSize = 0.0f;

        City city = new City();
        log.info("Serializing json string to json object");
        JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonCity);
        List<GLObject> antennaBalls = new ArrayList<GLObject>();
        JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
        log.info("creating metaphore elements");
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
                antennaBall.setLeftChildBallValue(jobj.getString("rightChildBallValue"));
                antennaBall.setRightChildBallValue(jobj.getString("leftChildBallValue"));
                antennaBall.setColor(readJSONColor(jobj, "color"));
                float size = (float) jobj.getDouble("parentBallRadius");
                antennaBall.setParentBallRadius(size);
                if (maxSize < size) {
                    maxSize = size;
                }
                antennaBalls.add(antennaBall);
                tmpAntennaBalls.add(antennaBall);
                // Get some dimension values for later normalization
                if (antennaBall.getParentBallRadius() > maxSize) {
                    maxSize = antennaBall.getParentBallRadius();
                }
            }
            city.getNeighborhoods().add(new Neighborhood(jsonFlatsObject.getString("name"), tmpAntennaBalls));
        }

        log.info("applying normalization process");
        // Read ratios from json city
        Map<String, Object> ratios = readRatios(json.getJSONObject("ratios"));
        float normParentRadius = (Float) (ratios.get("parentBallRadius") != null? new Float(ratios.get("parentBallRadius").toString()) : maxSize);
        // Normalize
        if (normParentRadius > 0.0) {
            for (GLObject glAB : antennaBalls) {
                ((GLAntennaBall) glAB).setParentBallRadius(((GLAntennaBall) glAB).getParentBallRadius() * GLAntennaBall.MAX_SIZE / normParentRadius);
            }
        }

        log.info("processing neighborhood and flat positions");
        // Place flats and neighborhoods once normalized
        city.placeNeighborhoods(GLAntennaBall.MAX_HEIGHT * 2);


        log.info("configure caption");// Configure caption lines
        Caption caption = configureCaption(json.getJSONObject("captionLines"));
        if (caption != null) {
            GLProjectViewManager.getInstance().setCaption(caption);
        }

        // Change the active view to AntennaBallLevel
        GLProjectViewManager.getInstance().setPavements(city.getPavements());
        GLProjectViewManager.getInstance().setItems(antennaBalls);
        log.info("Setting view level to AntennaBallLevel");
        GLProjectViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.AntennaBallLevel);
        log.info("VisualizeAntennaBalls() - end");
    }

    @Override
    public void visualizeTowers(String jsonCity) throws ViewManagerNotInstantiatedException {
        log.info("VisualizeTowers() - begin");
        float maxDepth = 0.0f;
        float maxWidth = 0.0f;
        float maxHeight = 0.0f;
        float maxInnerHeight = 0.0f;

        City city = new City();
        log.info("Serializing json string to json object");
        List<GLObject> towers = new ArrayList<GLObject>();
        JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonCity);
        JSONArray jsonNeighborhoods = json.getJSONArray("neighborhoods");
        log.info("creating metaphore elements");
        for (int i = 0; i < jsonNeighborhoods.size(); i++) {
            List<GLObject> tmpTowers = new ArrayList<GLObject>();
            JSONObject jsonFlatsObject = jsonNeighborhoods.getJSONObject(i);
            JSONArray jsonFlats = jsonFlatsObject.getJSONArray("flats");
            for (int j = 0; j < jsonFlats.size(); j++) {
                JSONObject jobj = jsonFlats.getJSONObject(j);
                GLTower tower = new GLTower();
                readJSONGLObjectProperties(jobj, tower);
                tower.setDepth((float) jobj.getDouble("depth"));
                tower.setHeight((float) jobj.getDouble("height"));
                tower.setWidth((float) jobj.getDouble("width"));
                tower.setInnerHeight((float) jobj.getDouble("innerHeight"));
                tower.setColor(readJSONColor(jobj, "color"));
                towers.add(tower);
                tmpTowers.add(tower);
                // Get some dimension values for later normalization
                if (tower.getDepth() > maxDepth) {
                    maxDepth = tower.getDepth();
                }
                if (tower.getWidth() > maxWidth) {
                    maxWidth = tower.getWidth();
                }
                if (tower.getHeight() > maxHeight) {
                    maxHeight = tower.getHeight();
                }
                if (tower.getInnerHeight() > maxInnerHeight) {
                    maxInnerHeight = tower.getInnerHeight();
                }
            }
            city.getNeighborhoods().add(new Neighborhood(jsonFlatsObject.getString("name"), tmpTowers));
        }

        log.info("applying normalization process");
        // Read ratios from json city
        Map<String, Object> ratios = readRatios(json.getJSONObject("ratios"));
        float normHeight = (Float) (ratios.get("height") != null? new Float(ratios.get("height").toString()) : maxHeight);
        float normInnerHeight = ratios.get("innerHeight") != null? new Float(ratios.get("innerHeight").toString()) : maxHeight;
        float normWidth = (Float) (ratios.get("width") != null? new Float(ratios.get("width").toString()) : maxWidth);
        float normDepth = ratios.get("depth") != null? new Float(ratios.get("depth").toString()) : maxDepth;
        // Normalize
        for (GLObject glTower : towers) {
            if (normHeight > 0.0) {
                ((GLTower) glTower).setHeight(((GLTower) glTower).getHeight() * GLTower.MAX_HEIGHT / normHeight);
            }
            if (normInnerHeight > 0.0) {
                ((GLTower) glTower).setInnerHeight(((GLTower) glTower).getInnerHeight() * GLTower.MAX_HEIGHT / normInnerHeight);
            }
            if (normWidth > 0.0) {
                ((GLTower) glTower).setWidth(((GLTower) glTower).getWidth() * GLTower.MAX_WIDTH / normWidth);
            }
            if (normDepth > 0.0) {
                ((GLTower) glTower).setDepth(((GLTower) glTower).getDepth() * GLTower.MAX_DEPTH / normDepth);
            }
        }

        log.info("processing neighborhood and flat positions");
        // Place flats and neighborhoods once normalized
        city.placeNeighborhoods(GLTower.MAX_HEIGHT + 1.0f);

        log.info("configure caption");
        // Configure caption lines
        Caption caption = configureCaption(json.getJSONObject("captionLines"));
        if (caption != null) {
            GLTowerViewManager.getInstance().setCaption(caption);
        }

        // Change the active view to TowerLevel
        GLTowerViewManager.getInstance().setPavements(city.getPavements());
        GLTowerViewManager.getInstance().setItems(towers);
        log.info("Setting view level to TowerLevel");
        GLTowerViewManager.getInstance().getDrawer().setViewLevel(EViewLevels.TowerLevel);
        log.info("VisualizeTowers() - end");
    }

    private Caption configureCaption(JSONObject jsonCaptionLines) {
        Caption caption = null;
        if (jsonCaptionLines != null) {
            caption = new Caption();
            Iterator<?> it = jsonCaptionLines.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> pairs = (Map.Entry<String, String>) it.next();
                caption.addLine(new Color((String) pairs.getValue()),
                        (String) pairs.getKey());
            }
        }
        return caption;
    }

    private void readJSONGLObjectProperties(JSONObject jsonObj, GLObject glObj) {
        glObj.setId(jsonObj.getInt("id"));
        glObj.setPositionX((float) jsonObj.getDouble("positionX"));
        glObj.setPositionZ((float) jsonObj.getDouble("positionZ"));
        glObj.setScale((float) jsonObj.getDouble("scale"));
    }

    private Color readJSONColor(JSONObject jsonObj, String tag) {
        float r = (float) (jsonObj.getJSONObject(tag)).getDouble("r");
        float g = (float) (jsonObj.getJSONObject(tag)).getDouble("g");
        float b = (float) (jsonObj.getJSONObject(tag)).getDouble("b");
        float alpha = (float) (jsonObj.getJSONObject(tag)).getDouble("alpha");
        Color color = new Color(r, g, b);
        color.setAlpha(alpha);
        return color;
    }
    
    private Map<String, Object> readRatios(JSONObject jsonRatios) {
        Map<String, Object> ratios = new HashMap<String, Object>();
        if (jsonRatios != null) {
            Iterator<?> it = jsonRatios.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> pairs = (Map.Entry<String, Object>) it.next();
                ratios.put(pairs.getKey(), pairs.getValue());
            }
        }
        return ratios;
    }

}
