package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.Map;

public class Metaclass {
	private String tableName;
	private Map<String,String> tableTypes;	// <var_name, var_type>
	private Map<String,Object> tableValues;	// <var_name, var_value>
	
	private String className;
	private Map<String,String> classTypes;	// <var_name, var_type>
	private Map<String,Object> classValues;	// <var_name, var_value>
	
	public Metaclass() {
		tableName = new String();
		tableTypes = new HashMap<String, String>();
		tableValues = new HashMap<String, Object>();
		
		className = new String();
		classTypes = new HashMap<String, String>();
		classValues = new HashMap<String, Object>();
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTableColumnType(String colname) {
		return null;
	}
	
	public Object getTableColumnValue(String colname) {
		return null;
	}
	
	public String getClassAttributeType(String attrname) {
		return null;
	}
	
	public Object getClassAttributeValue(String attrname) {
		return null;
	}

	public void addTableColumn(String colname, String coltype, Object colvalue) {
		tableTypes.put(colname, coltype);
		tableValues.put(colname, colvalue);
	}
	
	public void addClassAttribute(String attrname, String attrtype, Object attrvalue) {
		classTypes.put(attrname, attrtype);
		classValues.put(attrname, attrvalue);
	}
	
}
