package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="profile")
@XmlAccessorType(XmlAccessType.FIELD)
public class Metaclass {
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="description")
	private String description;
	@XmlAttribute(name="table")
	private String tableName;	
	@XmlAttribute(name="class")
	private String className;
	@XmlElement(name="mapping")
	private List<Mapping> mappings;
	@XmlElement(name="caption")
	private HashMap<String,String> captionLines;
	
	public Metaclass() {}

	public Metaclass(String name, String description, String tableName,
			String className, List<Mapping> mappings,
			HashMap<String, String> captionLines) {
		this.name = name;
		this.description = description;
		this.tableName = tableName;
		this.className = className;
		this.mappings = mappings;
		this.captionLines = captionLines;
	}

	public void addCaptionLine(String label, String hexColor) {
		captionLines.put(label, hexColor);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getTableName() {
		return tableName;
	}

	public String getClassName() {
		return className;
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

	public Map<String, String> getCaptionLines() {
		return captionLines;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setMappings(List<Mapping> mappings) {
		this.mappings = mappings;
	}

	public void setCaptionLines(HashMap<String, String> captionLines) {
		this.captionLines = captionLines;
	}
	
}
