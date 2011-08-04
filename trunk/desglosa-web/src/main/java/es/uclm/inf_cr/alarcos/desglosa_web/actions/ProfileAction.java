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
import es.uclm.inf_cr.alarcos.desglosa_web.util.DataSourceUtil;
import es.uclm.inf_cr.alarcos.desglosa_web.util.XMLAgent;

public class ProfileAction extends ActionSupport implements GenericActionInterface {
	private DataSourceUtil dataSourceUtil;
	private String entity;
	private String model;
	private String profileName;
	private String profileDescription;
	private String jsonMappings;
	private String jsonCaptionLines;
	private Map<String, String> tableColumns;
	private Map<String, String> classAttributes;
	private Map<String, String> entities = new HashMap<String,String>() {{
		put("companies", getText("label.company"));
		put("factories", getText("label.factory"));
		put("projects", getText("label.project"));
		put("subprojects", getText("label.subproject"));
	}};
	private Map<String, String> models = new HashMap<String,String>() {{
		put("model.gl.knowledge.GLTower", getText("label.model.towers"));
		put("model.gl.knowledge.GLAntennaBall", getText("label.model.projects"));
		put("model.gl.knowledge.GLFactory", getText("label.model.factories"));
	}};
	
	public void setDataSourceUtil(DataSourceUtil dataSourceUtil) {
		this.dataSourceUtil = dataSourceUtil;
	}

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

	public Map<String, String> getTableColumns() {
		return tableColumns;
	}

	public Map<String, String> getClassAttributes() {
		return classAttributes;
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
	
	public String loadTableColumns() {
		if (entity != null) {
			// Load all tables named by given name. It will return a list with 0 or 1 metaclass object
			List tableList = dataSourceUtil.loadTablesByTablename(entity);
			if (tableList.size() > 0) {
				tableColumns = (HashMap<String, String>)tableList.get(0);
			} else if (tableList.size() == 0) {
				// TODO error -> tabla no encontrada
			}
		}
		return SUCCESS;
	}
	
	public String loadClassAttributes() {
		if (model != null) {
			try {
				Class c = Class.forName(model);
				AnnotationParser ap = new AnnotationParser();
				classAttributes = ap.parse(c);
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
	}
	
	public String save() throws Exception {
		Metaclass metaclass = new Metaclass();
		// Add profile name and description
		metaclass.setName(profileName);
		metaclass.setDescription(profileDescription);
		// Add table and class name
		metaclass.setTableName(entity);
		metaclass.setClassName(model);
		// Add mapping data to metaclass
		List<Mapping> mappings = new ArrayList<Mapping>();
		JSONArray mappingArray = (JSONArray) JSONSerializer.toJSON(jsonMappings);
		for (int i = 0; i < mappingArray.size(); i++) {
			JSONObject mappingObject = mappingArray.getJSONObject(i);
			Field column = new Field(mappingObject.getString("columnName"), mappingObject.getString("columnType"));
			Field attribute = new Field(mappingObject.getString("attributeName"), mappingObject.getString("attributeType"));
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
		metaclass.setCaptionLines((HashMap<String, String>)captionLines);
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
