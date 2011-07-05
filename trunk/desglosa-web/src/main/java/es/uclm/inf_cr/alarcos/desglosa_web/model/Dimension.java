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
		res.setAttr(attr);
		res.setMeasureKey(measureKey);
		res.setMeasure((Measure)measure.clone());
		res.setType(type);
		res.setCsvCol(csvCol);
		res.setValue(value);
		res.setDescription(description);
		
		return res;
	}
	
}
