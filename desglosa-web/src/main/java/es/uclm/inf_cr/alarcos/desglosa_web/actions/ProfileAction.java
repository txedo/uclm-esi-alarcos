package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.springframework.web.context.ContextLoader;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import util.AnnotationParser;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Field;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Mapping;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Rule;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.PropertyWrapper;
import es.uclm.inf_cr.alarcos.desglosa_web.util.FileUtil;
import es.uclm.inf_cr.alarcos.desglosa_web.util.MyHashMapType;
import es.uclm.inf_cr.alarcos.desglosa_web.util.PropertyAnnotationParser;
import es.uclm.inf_cr.alarcos.desglosa_web.util.XMLAgent;

public class ProfileAction extends ActionSupport implements GenericActionInterface {
	private Map<String,String> profileNames;
	private Metaclass profile;
	private String entity;
	private String model;
	private String profileName;
	private String profileDescription;
	private String jsonMappings;
	private String jsonConstants;
	private String jsonCaptionLines;
	private List<PropertyWrapper> entityAttributes;
	private Map<String, String> modelAttributes;
	private Map<String, String> entities = new HashMap<String,String>() {{
		put("es.uclm.inf_cr.alarcos.desglosa_web.model.Company", getText("label.company"));
		put("es.uclm.inf_cr.alarcos.desglosa_web.model.Factory", getText("label.factory"));
		put("es.uclm.inf_cr.alarcos.desglosa_web.model.Project", getText("label.project"));
		put("es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject", getText("label.subproject"));
	}};
	private Map<String, String> models = new HashMap<String,String>() {{
		put("model.gl.knowledge.GLTower", getText("label.model.towers"));
		put("model.gl.knowledge.GLAntennaBall", getText("label.model.projects"));
		put("model.gl.knowledge.GLFactory", getText("label.model.factories"));
	}};

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}

	public void setJsonMappings(String jsonMappings) {
		this.jsonMappings = jsonMappings;
	}

	public void setJsonConstants(String jsonConstants) {
		this.jsonConstants = jsonConstants;
	}

	public void setJsonCaptionLines(String jsonCaptionLines) {
		this.jsonCaptionLines = jsonCaptionLines;
	}

	public Map<String, String> getProfileNames() {
		return profileNames;
	}

	public Metaclass getProfile() {
		return profile;
	}

	public String getEntity() {
		return entity;
	}

	public String getModel() {
		return model;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<PropertyWrapper> getEntityAttributes() {
		return entityAttributes;
	}

	public Map<String, String> getModelAttributes() {
		return modelAttributes;
	}

	public Map<String, String> getEntities() {
		return entities;
	}
	
	public Map<String, String> getModels() {
		return models;
	}
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String showForm() throws Exception {
		return SUCCESS;
	}
	
	public String updateProfileForm() {
		return SUCCESS;
	}
	
	public String loadEntityAttributes() {
		if (entity != null) {
			try {
				Class c = Class.forName(entity);
				PropertyAnnotationParser pap = new PropertyAnnotationParser();
				entityAttributes = pap.parse(c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public String loadModelAttributes() {
		if (model != null) {
			try {
				Class c = Class.forName(model);
				AnnotationParser ap = new AnnotationParser();
				modelAttributes = ap.parse(c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
	
	public void validateDoSave() {
		if (profileName == null || profileName.equals("")) {
			
		}
		if (profileDescription == null || profileDescription.equals("")) {
			
		}
		if (entity == null || entity.equals("")) {
			
		}
		if (model == null || model.equals("")) {
			
		}
		// check type compatibility server side
	}
	
	public String save() throws Exception {
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
			if (ratio instanceof JSONNull) ratio = null;
			else if (ratio instanceof Integer && attribute.getType().equals("float")) ratio = ((Integer)ratio).floatValue();
			else if (ratio instanceof Double && attribute.getType().equals("float")) ratio = ((Double)ratio).floatValue();
//			JsonConfig jsonConfig = new JsonConfig();
//			jsonConfig.setRootClass(Rule.class);
//			JSONArray jsonRules = mappingObject.getJSONArray("rules");
//			List<Rule> rules = (List)JSONSerializer.toJava(jsonRules, jsonConfig);
			JSONArray jsonRules = mappingObject.getJSONArray("rules");
			List<Rule> rules = new ArrayList<Rule>();
			for (int j = 0; j < jsonRules.size(); j++) {
				JSONObject ruleJSONObject = jsonRules.getJSONObject(j);
				Object low = ruleJSONObject.get("low");
				if (low instanceof JSONNull) low = null;
				else if (low instanceof Integer && column.getType().equals("float")) low = ((Integer)low).floatValue();
				else if (low instanceof Double && column.getType().equals("float")) low = ((Double)low).floatValue();
				Object high = ruleJSONObject.get("high");
				if (high instanceof JSONNull) high = null;
				else if (high instanceof Integer && column.getType().equals("float")) high = ((Integer)high).floatValue();
				else if (high instanceof Double && column.getType().equals("float")) high = ((Double)high).floatValue();
				Object value = ruleJSONObject.get("value");
				if (value instanceof JSONNull) value = null;
				else if (value instanceof Integer && attribute.getType().equals("float_range")) value = ((Integer)value).floatValue();
				else if (value instanceof Double && attribute.getType().equals("float_range")) value = ((Double)value).floatValue();
				rules.add(new Rule(low, high, value));
			}
			Mapping mapping = new Mapping(column, attribute, null, ratio, rules);
			mappings.add(mapping);
		}
		metaclass.setMappings(mappings);
		// Add constants data to metaclass
		List<Field> constants = new ArrayList<Field>();
		JSONArray constantArray = (JSONArray) JSONSerializer.toJSON(jsonConstants);
		for (int i = 0; i < constantArray.size(); i++) {
			JSONObject constantObject = constantArray.getJSONObject(i);
			String name = constantObject.getString("name");
			String type = constantObject.getString("type");			
			Object value = constantObject.get("value");
			if (value instanceof Integer && type.equals("float")) value = ((Integer)value).floatValue();
			else if (value instanceof Double && type.equals("float")) value = ((Double)value).floatValue();
			constants.add(new Field(type, name, value));
		}
		metaclass.setConstants(constants);
		// Add caption data to metaclass
		Map<String,String> captionLines = new HashMap<String,String>();
		JSONArray captionArray = (JSONArray) JSONSerializer.toJSON(jsonCaptionLines);
		for (int i = 0; i < captionArray.size(); i++) {
			JSONObject captionObject = captionArray.getJSONObject(i);
			String label = captionObject.getString("label");
			String color = captionObject.getString("color");
			captionLines.put(label, color);
		}
		metaclass.setCaptionLines(new MyHashMapType(captionLines));
		// Create XML from metaclass and place it in server
		String[] entityParts = entity.split("\\.");
		String filename = WordUtils.uncapitalize(entityParts[entityParts.length-1]) + "-" + profileName + "-" + Calendar.getInstance().getTimeInMillis() + ".xml";
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("profiles") + "\\" + filename;
		XMLAgent.marshal(path, Metaclass.class, metaclass);
		
		return SUCCESS;
	}

	public String edit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String get() throws Exception {
		// entity o profileName
		if (entity != null) {
			profileNames = FileUtil.getProfiles(entity);
		} else if (profileName != null) {
			profile = FileUtil.getProfile(profileName);
		}
		return SUCCESS;
	}

}
