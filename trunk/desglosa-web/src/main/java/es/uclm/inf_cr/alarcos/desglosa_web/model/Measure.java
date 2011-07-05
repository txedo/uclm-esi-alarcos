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

	@Override
	public Object clone() {
		Measure res = new Measure();
		res.setId(id);
		res.setName(name);
		res.setDescription(description);
		res.setKey(key);
		res.setHigh(high);
		res.setMedium(medium);
		res.setLow(low);
		return res;
	}
	
	


}
