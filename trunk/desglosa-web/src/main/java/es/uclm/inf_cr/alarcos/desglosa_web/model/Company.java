package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name="companies")
public class Company {
    @Property
    private int id;
    @Property
    private String name;
    @Property
    private String information;
    private Director director;
    @Property
    private Integer numberOfFactories;
    @Property
    private Integer numberOfProjects;
    @Property
    private Integer numberOfEmployees;
    
    public Company(){}
    
    public Company(String name, String information) {
        this.name = name;
        this.information = information;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @Column(nullable = false, length = 45, unique=true)
    public String getName() {
        return name;
    }

    @Column(nullable = true)
    public String getInformation() {
        return information;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "director_id")
    public Director getDirector() {
        return director;
    }

    @Formula("(select count(*) from companies c, factories f where c.id = id and c.id = f.company_id)")
    public Integer getNumberOfFactories() {
        return numberOfFactories;
    }

    @Formula("(select count(distinct(p.name)) from projects p, subprojects sp, factories f, companies c where p.id = sp.project_id and sp.factory_id = f.id and f.company_id = c.id and c.id = id)")
    public Integer getNumberOfProjects() {
        return numberOfProjects;
    }

    @Formula("(select sum(f.employees) from companies c, factories f where c.id = id and c.id = f.company_id)")
    public Integer getNumberOfEmployees() {
        return numberOfEmployees;
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

    public void setDirector(Director director) {
        this.director = director;
    }
    
    public void setNumberOfFactories(Integer numberOfFactories) {
        this.numberOfFactories = numberOfFactories;
    }

    public void setNumberOfProjects(Integer numberOfProjects) {
        this.numberOfProjects = numberOfProjects;
    }

    public void setNumberOfEmployees(Integer numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
