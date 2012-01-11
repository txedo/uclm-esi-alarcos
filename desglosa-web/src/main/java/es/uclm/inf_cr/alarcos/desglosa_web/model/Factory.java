package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate.MarketDAOHibernate;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

@Entity
@Table(name = "factories")
@NamedQueries({
    @NamedQuery(name = "findFactoriesByCompanyId", query = "select f from Factory f where f.company.id = :id "),
    @NamedQuery(name = "findFactoriesInvolvedInProjectId", query = "select distinct f from Factory f, Project p, Subproject sp where p.id = :id and sp.project.id = p.id and f.id = sp.factory.id")
})
public class Factory {
    @Property
    private int id;
    @Property(embedded = true)
    private Company company;
    @Property
    private String name;
    @Property
    private String information;
    private Director director;
    private String email;
    @Property
    private Integer employees;
    private Address address;
    private Location location;
    private Set<Project> projects = new HashSet<Project>();
    private Set<Subproject> subprojects = new HashSet<Subproject>();
    @Property @Measure(base = false)
    private Integer numberOfLeadingProjects;
    @Property @Measure(base = false)
    private Integer numberOfDevelopingSubprojects;
    @Property(embedded = true)
    private Market mostRepresentativeMarket;

    public Factory() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    @Column(name = "name", nullable = false, length = 45, unique = true)
    public String getName() {
        return name;
    }

    @Column
    public String getInformation() {
        return information;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "director_id")
    public Director getDirector() {
        return director;
    }

    @Column(name = "contact_email")
    public String getEmail() {
        return email;
    }

    @Column
    public Integer getEmployees() {
        return employees;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return address;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    public Location getLocation() {
        return location;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "mainFactory", orphanRemoval = true)
    public Set<Project> getProjects() {
        return projects;
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "factory", orphanRemoval = true)
    public Set<Subproject> getSubprojects() {
        return subprojects;
    }

    @Transient
    public Integer getNumberOfLeadingProjects() {
        numberOfLeadingProjects = projects.size();
        return numberOfLeadingProjects;
    }
    
    @Transient
    public Integer getNumberOfDevelopingSubprojects() {
        numberOfDevelopingSubprojects = subprojects.size();
        return numberOfDevelopingSubprojects;
    }
    
    @Transient
    public Market getMostRepresentativeMarket() {
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

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    
    public void setSubprojects(Set<Subproject> subprojects) {
        this.subprojects = subprojects;
    }

    public void setNumberOfLeadingProjects(Integer numberOfLeadingProjects) {
        this.numberOfLeadingProjects = numberOfLeadingProjects;
    }
    
    public void setNumberOfDevelopingProjects(Integer numberOfDevelopingSubprojects) {
        this.numberOfDevelopingSubprojects = numberOfDevelopingSubprojects;
    }

    public void setMostRepresentativeMarket(Market mostRepresentativeMarket) {
        this.mostRepresentativeMarket = mostRepresentativeMarket;
    }
    
    public void updateMostRepresentativeMarket() {
        MarketDAOHibernate marketDao = (MarketDAOHibernate) ApplicationContextProvider.getBean("marketDao");
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        List<Market> markets = marketDao.findByNamedQuery("findMostRepresentativeMarketByFactoryId", queryParams);
        if (markets.size() > 0) {
            mostRepresentativeMarket = markets.get(0);
        } else {
            markets = marketDao.findByNamedQuery("findMostLeadedMarketByFactoryId", queryParams);
            if (markets.size() > 0) {
                mostRepresentativeMarket = markets.get(0);
            } else {
                mostRepresentativeMarket = null;
            }
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

}
