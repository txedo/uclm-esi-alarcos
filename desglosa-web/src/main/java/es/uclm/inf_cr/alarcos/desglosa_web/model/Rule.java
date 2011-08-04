package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Rule {
	@XmlAttribute
	private Object low;
	@XmlAttribute
	private Object high;
	@XmlAttribute
	private Object value;
	
	public Rule() {}

	public Rule(Object low, Object high, Object value) {
		this.low = low;
		this.high = high;
		this.value = value;
	}
	
	public Object getLow() {
		return low;
	}
	public Object getHigh() {
		return high;
	}
	public Object getValue() {
		return value;
	}
	public void setLow(Object low) {
		this.low = low;
	}
	public void setHigh(Object high) {
		this.high = high;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
