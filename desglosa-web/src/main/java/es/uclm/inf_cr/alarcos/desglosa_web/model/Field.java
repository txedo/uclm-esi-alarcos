package es.uclm.inf_cr.alarcos.desglosa_web.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Field {
	@XmlAttribute
	private String type;
	@XmlAttribute
	private String name;
	
	public Field() {}
	
	public Field(String type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public Class getParameterType() {
		Class parameterType = null;
		if (this.type.equals("int")) parameterType = Integer.TYPE;
		else if (this.type.equals("float")) parameterType = Float.TYPE;
		else if (this.type.equals("string")) parameterType = String.class;
		else if (this.type.equals("boolean")) parameterType = Boolean.TYPE;
		else if (this.type.equals("color")) parameterType = String.class; // hexCode
		else if (this.type.equals("float_range")) parameterType = Float.TYPE;
		return parameterType;
	}
	
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}