package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="markets")
public class Market {
	private int id;
	private String name;
	private String color;
	
	public Market() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="color")
	public String getColor() {
		return color;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
