package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dimension {
	@XmlAttribute
	private String attr;
	@XmlAttribute
	private String measureKey;
	@XmlTransient
	private Measure measure;
	@XmlElement
	private String type;
	@XmlElement(name="csv_col")
	private int csvCol;
	@XmlTransient
	private Object value;
	
	public Dimension(){}

	public String getAttr() {
		return attr;
	}

	public String getMeasureKey() {
		return measureKey;
	}
	
	public Measure getMeasure() {
		return measure;
	}

	public String getType() {
		return type;
	}

	public int getCsvCol() {
		return csvCol;
	}
	
	public Object getValue() {
		return value;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public void setMeasureKey(String measureKey) {
		this.measureKey = measureKey;
	}
	
	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCsvCol(int csvCol) {
		this.csvCol = csvCol;
	}
	
	public void setValue(Object obj) {
		this.value = obj;
	}
	
}
