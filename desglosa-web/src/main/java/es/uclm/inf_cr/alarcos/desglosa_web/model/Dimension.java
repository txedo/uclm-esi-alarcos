package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dimension {
	@XmlTransient
	public static final Object EXPR = "expr";
	@XmlTransient
	public static final Object NUMERIC = "numeric";
	@XmlTransient
	public static final Object PERCENT = "percent";
	@XmlAttribute
	private String attr;
	@XmlAttribute(name="measure")
	private String measureKey;
	@XmlTransient
	private Measure measure;
	@XmlElement
	private String type;
	@XmlElement(name="csv_col")
	private String csvCol;
	@XmlTransient
	private Object value;
	@XmlElement(name="description", required=false)
	private String description;
	
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

	public String getCsvCol() {
		return csvCol;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getDescription() {
		return description;
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

	public void setCsvCol(String csvCol) {
		this.csvCol = csvCol;
	}
	
	public void setValue(Object obj) {
		this.value = obj;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Object clone() {
		Dimension res = new Dimension();
		if (attr != null) res.setAttr(new String(attr));
		if (measureKey != null) res.setMeasureKey(new String(measureKey));
		if (measure != null) res.setMeasure((Measure)measure.clone());
		if (type != null) res.setType(new String(type));
		if (csvCol != null) res.setCsvCol(new String(csvCol));
		if (value != null) res.setValue(value);
		if (description != null) res.setDescription(new String(description));
		
		return res;
	}
	
}
