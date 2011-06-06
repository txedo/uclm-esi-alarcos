package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="companies")
public class Company {
	private int id;
	private String name;
	private String information;
	
	public Company(){}
	
	public Company(String name, String information) {
		this.name = name;
		this.information = information;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(nullable=false,length=45,unique=true)
	public String getName() {
		return name;
	}

	@Column(nullable=true)
	public String getInformation() {
		return information;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
