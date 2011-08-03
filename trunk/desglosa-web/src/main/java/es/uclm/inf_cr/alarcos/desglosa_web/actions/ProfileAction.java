package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.AnnotationParser;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Metaclass;
import es.uclm.inf_cr.alarcos.desglosa_web.util.DataSourceUtil;

public class ProfileAction extends ActionSupport implements GenericActionInterface {
	private DataSourceUtil dataSourceUtil;
	private String entity;
	private String model;
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
	private Map<String,String> associations;
	private String jsonAssociations;
	private Map<String,String> captionLines;
	private String jsonCaptionLines;
	
	public void setDataSourceUtil(DataSourceUtil dataSourceUtil) {
		this.dataSourceUtil = dataSourceUtil;
	}

	public void setJsonAssociations(String jsonAssociations) {
		this.jsonAssociations = jsonAssociations;
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
	
	public String save() throws Exception {
		// TODO Build metaclass
		return null;
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
