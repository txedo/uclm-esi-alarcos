package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;

import net.sf.json.JSONArray;
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
import es.uclm.inf_cr.alarcos.desglosa_web.util.MyHashMapType;
import es.uclm.inf_cr.alarcos.desglosa_web.util.PropertyAnnotationParser;
import es.uclm.inf_cr.alarcos.desglosa_web.util.XMLAgent;

public class ProfileAction extends ActionSupport implements GenericActionInterface {
	private String entity;
	private String model;
	private String profileName;
	private String profileDescription;
	private String jsonMappings;
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

	public void setJsonCaptionLines(String jsonCaptionLines) {
		this.jsonCaptionLines = jsonCaptionLines;
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
			Field column = new Field(mappingObject.getString("entityAttrType"), mappingObject.getString("entityAttrName"));
			Field attribute = new Field(mappingObject.getString("modelAttrType"), mappingObject.getString("modelAttrName"));
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setRootClass(Rule.class);
			JSONArray jsonRules = mappingObject.getJSONArray("rules");
			List<Rule> rules = (List)JSONSerializer.toJava(jsonRules, jsonConfig);
			Mapping mapping = new Mapping(column, attribute, null, rules);
			mappings.add(mapping);
		}
		metaclass.setMappings(mappings);
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
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("profiles") + "\\" + profileName + "-" + Calendar.getInstance().getTimeInMillis() + ".xml";
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
		// TODO Auto-generated method stub
		return null;
	}

}