package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.WordUtils;

import util.AnnotationParser;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProfileDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.DeleteProfileException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Field;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Mapping;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Profile;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Rule;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.MyHashMapType;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyAnnotationParser;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.XMLAgent;
import es.uclm.inf_cr.alarcos.desglosa_web.util.Utilities;

public class ProfileManager {
    private static ProfileDAO profileDao;
    public static final String PROFILE_FOLDER = "profiles";
    
    private ProfileManager() {
    }
    
    public static List<PropertyWrapper> readEntityAttributes(String entity) throws ClassNotFoundException, Exception {
        Class<?> c = Class.forName(entity);
        return PropertyAnnotationParser.parse(c);
    }
    
    public static Map<String, String> readModelDimensions(String model) throws ClassNotFoundException, Exception {
        Class<?> c = Class.forName(model);
        return AnnotationParser.parse(c);
    }
    
    public static Map<String, String> getProfilesForEntity(String entity) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        return profileDao.getProfiles(entity);
    }
    
    public static Metaclass getProfileByName(String profileName) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        return profileDao.getProfile(profileName);
    }

    public static Metaclass buildProfile(String profileName, String profileDescription, String entity, String model) {
        Metaclass metaclass = new Metaclass();
        // Add profile name and description
        profileName = profileName.replace("-", " ");
        profileName = WordUtils.capitalize(profileName);
        profileName = profileName.replace(" ", "");
        metaclass.setName(profileName);
        metaclass.setDescription(profileDescription);
        // Add table and class name
        metaclass.setEntityName(entity);
        metaclass.setModelName(model);
        return metaclass;
    }

    public static void addMappings(Metaclass metaclass, String jsonMappings) {
     // Add mapping data to metaclass
        List<Mapping> mappings = new ArrayList<Mapping>();
        JSONArray mappingArray = (JSONArray) JSONSerializer.toJSON(jsonMappings);
        for (int i = 0; i < mappingArray.size(); i++) {
            JSONObject mappingObject = mappingArray.getJSONObject(i);
            JSONObject entityAttribute = mappingObject.getJSONObject("entityAttribute");
            Field column = new Field(entityAttribute.getString("type"), entityAttribute.getString("name"));
            JSONObject modelAttribute = mappingObject.getJSONObject("modelAttribute");
            Field attribute = new Field(modelAttribute.getString("type"), modelAttribute.getString("name"));
            Object ratio = mappingObject.get("ratio");
            if (ratio instanceof JSONNull) {
                ratio = null;
            } else if (ratio instanceof Integer && attribute.getType().equals("float")) {
                ratio = ((Integer) ratio).floatValue();
            } else if (ratio instanceof Double && attribute.getType().equals("float")) {
                ratio = ((Double) ratio).floatValue();
            }
            JSONArray jsonRules = mappingObject.getJSONArray("rules");
            List<Rule> rules = new ArrayList<Rule>();
            for (int j = 0; j < jsonRules.size(); j++) {
                JSONObject ruleJSONObject = jsonRules.getJSONObject(j);
                Object low = ruleJSONObject.get("low");
                if (low instanceof JSONNull) {
                    low = null;
                } else if (low instanceof Integer && column.getType().equals("float")) {
                    low = ((Integer) low).floatValue();
                } else if (low instanceof Double && column.getType().equals("float")) {
                    low = ((Double) low).floatValue();
                }
                Object high = ruleJSONObject.get("high");
                if (high instanceof JSONNull) {
                    high = null;
                } else if (high instanceof Integer && column.getType().equals("float")) {
                    high = ((Integer) high).floatValue();
                } else if (high instanceof Double && column.getType().equals("float")) {
                    high = ((Double) high).floatValue();
                }
                Object value = ruleJSONObject.get("value");
                if (value instanceof JSONNull) {
                    value = null;
                } else if (value instanceof Integer && attribute.getType().equals("float_range")) {
                    value = ((Integer) value).floatValue();
                } else if (value instanceof Double && attribute.getType().equals("float_range")) {
                    value = ((Double) value).floatValue();
                }
                rules.add(new Rule(low, high, value));
            }
            Mapping mapping = new Mapping(column, attribute, null, ratio, rules);
            mappings.add(mapping);
        }
        metaclass.setMappings(mappings);
    }

    public static void addConstants(Metaclass metaclass, String jsonConstants) {
        // Add constants data to metaclass
        List<Field> constants = new ArrayList<Field>();
        JSONArray constantArray = (JSONArray) JSONSerializer.toJSON(jsonConstants);
        for (int i = 0; i < constantArray.size(); i++) {
            JSONObject constantObject = constantArray.getJSONObject(i);
            String name = constantObject.getString("name");
            String type = constantObject.getString("type");
            Object value = constantObject.get("value");
            if (value instanceof JSONNull) {
                value = null;
            } else if (value instanceof Integer && type.equals("float")) {
                value = ((Integer) value).floatValue();
            } else if (value instanceof Double && type.equals("float")) {
                value = ((Double) value).floatValue();
            }
            constants.add(new Field(type, name, value));
        }
        metaclass.setConstants(constants);
    }

    public static void addCaption(Metaclass metaclass, String jsonCaptionLines) {
        // Add caption data to metaclass
        Map<String, String> captionLines = new HashMap<String, String>();
        JSONArray captionArray = (JSONArray) JSONSerializer.toJSON(jsonCaptionLines);
        for (int i = 0; i < captionArray.size(); i++) {
            JSONObject captionObject = captionArray.getJSONObject(i);
            String label = captionObject.getString("label");
            String color = captionObject.getString("color");
            captionLines.put(label, color);
        }
        metaclass.setCaptionLines(new MyHashMapType(captionLines));
    }

    public static void saveProfile(Metaclass metaclass, String entity, String profileName) throws JAXBException {
        // Create XML from metaclass and place it in server
        String[] entityParts = entity.split("\\.");
        String filename = WordUtils
                .uncapitalize(entityParts[entityParts.length - 1])
                + "-"
                + profileName
                + "-"
                + Calendar.getInstance().getTimeInMillis()
                + ".xml";
        String path = Utilities.getRealPathToWebApplicationContext(PROFILE_FOLDER) + "\\" + filename;
        XMLAgent.marshal(path, Metaclass.class, metaclass);
    }
    
    public static List<Profile> getProfiles() throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        return profileDao.getProfiles();
    }
    
    public static String getLinkToXmlFile(String filename) {
        return Utilities.getRealPathToWebApplicationContext(PROFILE_FOLDER) + "\\" + filename;
    }
    
    public void setProfileDao (ProfileDAO profileDao) {
       ProfileManager.profileDao = profileDao;
    }

    public static void removeProfile(String filename) throws DeleteProfileException {
        profileDao.removeProfile(filename);
    }
}
