package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Mapping {
	@XmlElement
	private Field column;
	@XmlElement
	private Field attribute;
	@XmlAttribute
	private Object value;
	@XmlElement(name="rule")
	private List<Rule> rules;
	
	public Mapping () {}
	
	public Mapping(Field column, Field attribute, Object value, List<Rule> rules) {
		this.column = column;
		this.attribute = attribute;
		this.value = value;
		this.rules = rules;
	}

	public Field getColumn() {
		return column;
	}
	public Field getAttribute() {
		return attribute;
	}
	public Object getValue() {
		return value;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setColumn(Field column) {
		this.column = column;
	}
	public void setAttribute(Field attribute) {
		this.attribute = attribute;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	
	
}
