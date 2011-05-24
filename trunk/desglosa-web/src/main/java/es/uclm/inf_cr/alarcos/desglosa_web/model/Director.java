package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="directors")
public class Director {
	private int id;
	private String name;
	private String firstSurname;
	private String lastSurname;
	private Image picture;
	
	
	public Director() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	@Column
	public String getName() {
		return name;
	}
	
	@Column(name="first_surname")
	public String getFirstSurname() {
		return firstSurname;
	}
	
	@Column(name="last_surname")
	public String getLastSurname() {
		return lastSurname;
	}
	
	@OneToOne
	@PrimaryKeyJoinColumn
	public Image getPicture() {
		return picture;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}
	
	public void setLastSurname(String lastSurname) {
		this.lastSurname = lastSurname;
	}
	
	public void setPicture(Image picture) {
		this.picture = picture;
	}
	
	
}
