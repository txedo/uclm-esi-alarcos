package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name = "companies")
public class Company {
    @Property
    private int id;
    private Map<String, Object> measures = new HashMap<String, Object>();
    @Property
    private String name;
    @Property
    private String information;
    private Director director;
    private Set<Factory> factories = new HashSet<Factory>();
    @Property @Measure(base = false)
    private Integer numberOfFactories;
    @Property @Measure(base = false)
    private Integer numberOfEmployees;
    @Property @Measure(base = false)
    private Integer numberOfLeadedProjects;
    @Property @Measure(base = false)
    private Integer numberOfSharedProjects;
    @Property @Measure(base = false)
    private Integer numberOfDevelopingSubprojects;

    public Company() {
    }

    public Company(String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }
    
    @Transient
    public Map<String, Object> getMeasures() {
        return measures;
    }

    @Column(nullable = false, length = 45, unique = true)
    public String getName() {
        return name;
    }

    @Column(nullable = true)
    public String getInformation() {
        return information;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @JoinColumn(name = "director_id")
    public Director getDirector() {
        return director;
    }
    
    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, mappedBy = "company", orphanRemoval = true)
    public Set<Factory> getFactories() {
        return factories;
    }

    @Formula("(select count(*) from companies c, factories f where c.id = id and c.id = f.company_id)")
    public Integer getNumberOfFactories() {
        return numberOfFactories;
    }

    @Formula("(select sum(f.employees) from companies c, factories f where c.id = id and c.id = f.company_id)")
    public Integer getNumberOfEmployees() {
        if (numberOfEmployees == null) {
            numberOfEmployees = 0;
        }
        return numberOfEmployees;
    }

    @Formula("(select count(p.id) from companies c, factories f, projects p where c.id = id and c.id = f.company_id and f.id = p.mainFactory_id)")
    public Integer getNumberOfLeadedProjects() {
        return numberOfLeadedProjects;
    }

    @Formula("(select count(distinct p.name) from companies c, factories f, projects p, subprojects sp where p.id = sp.project_id and sp.factory_id = f.id and f.company_id = id)")
    public Integer getNumberOfSharedProjects() {
        return numberOfSharedProjects;
    }

    @Formula("(select count(distinct sp.name) from companies c, factories f, projects p, subprojects sp where sp.factory_id = f.id and f.company_id = id)")
    public Integer getNumberOfDevelopingSubprojects() {
        return numberOfDevelopingSubprojects;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setMeasures(Map<String, Object> measures) {
        this.measures = measures;
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
    
    public void setFactories(Set<Factory> factories) {
        this.factories = factories;
    }

    public void setNumberOfFactories(Integer numberOfFactories) {
        this.numberOfFactories = numberOfFactories;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public void setNumberOfLeadedProjects(Integer numberOfLeadedProjects) {
        this.numberOfLeadedProjects = numberOfLeadedProjects;
    }

    public void setNumberOfSharedProjects(Integer numberOfSharedProjects) {
        this.numberOfSharedProjects = numberOfSharedProjects;
    }

    public void setNumberOfDevelopingSubprojects(Integer numberOfDevelopingSubprojects) {
        this.numberOfDevelopingSubprojects = numberOfDevelopingSubprojects;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
