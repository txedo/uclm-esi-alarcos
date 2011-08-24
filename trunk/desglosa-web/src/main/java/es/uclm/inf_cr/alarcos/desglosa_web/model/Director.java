package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="directors")
public class Director {
	private int id;
	private String name;
	private String lastName;
	private String imagePath;
	
	
	public Director() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	@Column
	public String getName() {
		return name;
	}
	
	@Column(name="last_name")
	public String getLastName() {
		return lastName;
	}
	
	@Column(name="image_path")
	public String getImagePath() {
		return imagePath;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
