package es.uclm.inf_cr.alarcos.desglosa_web.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import es.uclm.inf_cr.alarcos.desglosa_web.util.MyHashMapType;

@XmlRootElement(name="profile")
@XmlAccessorType(XmlAccessType.FIELD)
public class Metaclass {
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="description")
	private String description;
	@XmlAttribute(name="entity")
	private String entityName;	
	@XmlAttribute(name="model")
	private String modelName;
	@XmlElement(name="mapping")
	private List<Mapping> mappings = new ArrayList<Mapping>();
	@XmlElement(name="caption")
	private MyHashMapType captionLines = new MyHashMapType();
	
	public Metaclass() {}

	public Metaclass(String name, String description, String entityName,
			String modelName, List<Mapping> mappings,
			MyHashMapType captionLines) {
		this.name = name;
		this.description = description;
		this.entityName = entityName;
		this.modelName = modelName;
		this.mappings = mappings;
		this.captionLines = captionLines;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getEntityName() {
		return entityName;
	}

	public String getModelName() {
		return modelName;
	}
	
	public List<Mapping> getMappings() {
		return mappings;
	}
	
	public MyHashMapType getCaptionLines() {
		return captionLines;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public void setMappings(List<Mapping> mappings) {
		this.mappings = mappings;
	}
	
	public void setCaptionLines(MyHashMapType captionLines) {
		this.captionLines = captionLines;
	}
	
}
