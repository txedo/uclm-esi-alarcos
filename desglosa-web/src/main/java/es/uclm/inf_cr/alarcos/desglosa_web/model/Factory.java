package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name="factories")
@NamedQueries ({
    @NamedQuery(
        name = "findFactoriesByCompanyId",
        query = "select f from Factory f where f.company.id = :id "
        )
})
public class Factory {
	@Property
	private int id;
	@Property(embedded=true)
	private Company company;
	@Property(type="string")
	private String name;
	@Property(type="string")
	private String information;
	private Director director;
	private String email;
	@Property
	private int employees;
	private Address address;
	private Location location;
	@Property
	private int numberOfProjects;
	
	public Factory() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="company_id")
	public Company getCompany() {
		return company;
	}

	@Column(name="name",nullable=false,length=45,unique=true)
	public String getName() {
		return name;
	}

	@Column
	public String getInformation() {
		return information;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
    @JoinColumn(name="director_id")
	public Director getDirector() {
		return director;
	}

	@Column(name="contact_email")
	public String getEmail() {
		return email;
	}

	@Column
	public int getEmployees() {
		return employees;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="address_id")
	public Address getAddress() {
		return address;
	}
	
	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="location_id")
	public Location getLocation() {
		return location;
	}
	
	@Formula("(select count(distinct(p.name)) from projects p, subprojects sp, factories f where p.id = sp.project_id and sp.factory_id = id)")
	public int getNumberOfProjects() {
		return numberOfProjects;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmployees(int employees) {
		this.employees = employees;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public void setLocation(Location location){
		this.location = location;
	}

	public void setNumberOfProjects(int numberOfProjects) {
		this.numberOfProjects = numberOfProjects;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
