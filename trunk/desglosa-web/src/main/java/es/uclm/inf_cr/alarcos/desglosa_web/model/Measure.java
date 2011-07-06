package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="measures")
public class Measure {
	private int id;
	private String name;
	private String description;
	private String key;
	private float high;
	private float medium;
	private float low;
	private float highOffset;
	private float lowOffset;
	
	public Measure() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}

	@Column(name="key")
	public String getKey() {
		return key;
	}
	
	@Column(name="high")
	public float getHigh() {
		return high;
	}
	
	@Column(name="medium")
	public float getMedium() {
		return medium;
	}

	@Column(name="low")
	public float getLow() {
		return low;
	}
	
	@Column(name="high_offset")
	public float getHighOffset() {
		return highOffset;
	}
	
	@Column(name="low_offset")
	public float getLowOffset() {
		return lowOffset;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public void setHigh(float high) {
		this.high = high;
	}
	
	public void setMedium(float medium) {
		this.medium = medium;
	}

	public void setLow(float low) {
		this.low = low;
	}
	
	public void setHighOffset(float highOffset) {
		this.highOffset = highOffset;
	}
	
	public void setLowOffset(float lowOffset) {
		this.lowOffset = lowOffset;
	}

	@Override
	public Object clone() {
		Measure res = new Measure();
		res.setId(new Integer(id));
		if (name != null) res.setName(new String(name));
		if (description != null) res.setDescription(new String(description));
		if (key != null) res.setKey(new String(key));
		res.setHigh(new Float(high));
		res.setMedium(new Float(medium));
		res.setLow(new Float(low));
		return res;
	}
	
	


}
