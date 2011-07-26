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
	private Metaclass metaclass;
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

	public Metaclass getMetaclass() {
		return metaclass;
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
		metaclass = new Metaclass();
		if (entity != null) {
			// Load all tables named by given name. It will return a list with 0 or 1 metaclass object
			List tableList = dataSourceUtil.loadTablesByTablename(entity);
			if (tableList.size() > 0) {
				metaclass = (Metaclass)tableList.get(0);
				metaclass.setTableName(entity);
			} else if (tableList.size() == 0) {
				// TODO error -> tabla no encontrada
			}
		}
		return SUCCESS;
	}
	
	public String loadClassAttributes() {
		metaclass = new Metaclass();
		if (model != null) {
			try {
				Class c = Class.forName(model);
				AnnotationParser ap = new AnnotationParser();
				metaclass.setClassTypes(ap.parse(c));
				metaclass.setClassName(model);
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
		// TODO Auto-generated method stub
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
