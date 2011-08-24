package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate.MarketDAOHibernate;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

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
	@Property(embedded=true)
	private Market mostRepresentativeMarket;
	
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
	
	@Transient
	public Market getMostRepresentativeMarket() {
		MarketDAOHibernate marketDao = (MarketDAOHibernate) ApplicationContextProvider.getBean("marketDao");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("id", id);
		List<Market> markets = marketDao.findByNamedQuery("findMostRepresentativeMarketByFactoryId", queryParams);
		if (markets.size() > 0) mostRepresentativeMarket = markets.get(0);
		else mostRepresentativeMarket = null;
		return mostRepresentativeMarket;
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
	
	public void setMostRepresentativeMarket(Market mostRepresentativeMarket) {
		this.mostRepresentativeMarket = mostRepresentativeMarket;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
