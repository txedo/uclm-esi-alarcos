package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate.SubprojectDAOHibernate;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.TransientAttributesListener;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

@Entity
@Table(name = "factories")
@NamedQueries({
    @NamedQuery(name = "findFactoriesByCompanyId", query = "select f from Factory f where f.company.id = :id "),
    @NamedQuery(name = "findFactoriesInvolvedInProjectId", query = "select distinct f from Factory f, Project p, Subproject sp where p.id = :id and sp.project.id = p.id and f.id = sp.factory.id")
})
@EntityListeners({TransientAttributesListener.class})
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
    @Property @Measure(base = false)
    private Integer planesProyectoGestionados;
    @Property @Measure(base = false)
    private Integer planesProyectoEnProceso;
    @Property @Measure(base = false)
    private Integer planesProyectoNoGestionados;
    @Property @Measure(base = false)
    private Integer auditoriasPlanificadas;
    @Property @Measure(base = false)
    private Integer auditoriasRealizadas;
    @Property @Measure(base = false)
    private Integer auditoriasCanceladas;
    @Property @Measure(base = false)
    private Integer auditoriasAplazadas;
    @Property @Measure(base = false)
    private Integer noConformidadesGestionadas;
    @Property @Measure(base = false)
    private Integer noConformidadesNoGestionadas;
    @Property @Measure(base = false)
    private Integer noConformiadadesEnProceso;
    @Property @Measure(base = false)
    private Integer noConformidadesCerradas;
    @Property @Measure(base = false)
    private Integer indicadoresReportados;
    @Property @Measure(base = false)
    private Integer indicadoresEnPlazo;
    @Property @Measure(base = false)
    private Integer indicadoresOk;

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
        if (projects != null) {
            numberOfLeadingProjects = projects.size();
        }
        return numberOfLeadingProjects;
    }
    
    @Transient
    public Integer getNumberOfDevelopingSubprojects() {
        if (subprojects != null) {
            numberOfDevelopingSubprojects = subprojects.size();
        }
        return numberOfDevelopingSubprojects;
    }
    
    @Transient
    public Market getMostRepresentativeMarket() {
        return mostRepresentativeMarket;
    }

    @Transient
    public Integer getPlanesProyectoGestionados() {
        return planesProyectoGestionados;
    }

    @Transient
    public Integer getPlanesProyectoEnProceso() {
        return planesProyectoEnProceso;
    }

    @Transient
    public Integer getPlanesProyectoNoGestionados() {
        return planesProyectoNoGestionados;
    }

    @Transient
    public Integer getAuditoriasPlanificadas() {
        return auditoriasPlanificadas;
    }

    @Transient
    public Integer getAuditoriasRealizadas() {
        return auditoriasRealizadas;
    }

    @Transient
    public Integer getAuditoriasCanceladas() {
        return auditoriasCanceladas;
    }

    @Transient
    public Integer getAuditoriasAplazadas() {
        return auditoriasAplazadas;
    }

    @Transient
    public Integer getNoConformidadesGestionadas() {
        return noConformidadesGestionadas;
    }

    @Transient
    public Integer getNoConformidadesNoGestionadas() {
        return noConformidadesNoGestionadas;
    }

    @Transient
    public Integer getNoConformiadadesEnProceso() {
        return noConformiadadesEnProceso;
    }

    @Transient
    public Integer getNoConformidadesCerradas() {
        return noConformidadesCerradas;
    }

    @Transient
    public Integer getIndicadoresReportados() {
        return indicadoresReportados;
    }

    @Transient
    public Integer getIndicadoresEnPlazo() {
        return indicadoresEnPlazo;
    }

    @Transient
    public Integer getIndicadoresOk() {
        return indicadoresOk;
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
    
    public void updateTransientFields() {
        this.updateMostRepresentativeMarket();
        this.updatePlanesProyecto();
        this.updateAuditorias();
        this.updateNoConformidades();
        this.updateIndicadores();
    }
    
    public void updatePlanesProyecto() {
        SubprojectDAOHibernate subprojectDao = (SubprojectDAOHibernate) ApplicationContextProvider.getBean("subprojectDao");
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        this.setPlanesProyectoGestionados(subprojectDao.numberByNamedQuery("numberSubprojectsPlanesGestionados", queryParams));
        this.setPlanesProyectoEnProceso(subprojectDao.numberByNamedQuery("numberSubprojectsPlanesEnProceso", queryParams));
        this.setPlanesProyectoNoGestionados(subprojectDao.numberByNamedQuery("numberSubprojectsPlanesNoGestionados", queryParams));
    }
    
    public void updateAuditorias() {
        SubprojectDAOHibernate subprojectDao = (SubprojectDAOHibernate) ApplicationContextProvider.getBean("subprojectDao");
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        this.setAuditoriasPlanificadas(subprojectDao.numberByNamedQuery("numberSubprojectsAuditoriasPlanificadas", queryParams));
        this.setAuditoriasRealizadas(subprojectDao.numberByNamedQuery("numberSubprojectsAuditoriasRealizadas", queryParams));
        this.setAuditoriasCanceladas(subprojectDao.numberByNamedQuery("numberSubprojectsAuditoriasCanceladas", queryParams));
        this.setAuditoriasAplazadas(subprojectDao.numberByNamedQuery("numberSubprojectsAuditoriasAplazadas", queryParams));
    }

    public void updateNoConformidades() {
        SubprojectDAOHibernate subprojectDao = (SubprojectDAOHibernate) ApplicationContextProvider.getBean("subprojectDao");
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        this.setNoConformidadesGestionadas(subprojectDao.numberByNamedQuery("numberSubprojectsNCGestionadas", queryParams));
        this.setNoConformidadesNoGestionadas(subprojectDao.numberByNamedQuery("numberSubprojectsNCNoGestionadas", queryParams));
        this.setNoConformiadadesEnProceso(subprojectDao.numberByNamedQuery("numberSubprojectsNCEnProceso", queryParams));
        this.setNoConformidadesCerradas(subprojectDao.numberByNamedQuery("numberSubprojectsNCCerradas", queryParams));
    }
    
    public void updateIndicadores() {
        SubprojectDAOHibernate subprojectDao = (SubprojectDAOHibernate) ApplicationContextProvider.getBean("subprojectDao");
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        this.setIndicadoresReportados(subprojectDao.numberByNamedQuery("numberSubprojectsIndicadoresReportados", queryParams));
        this.setIndicadoresEnPlazo(subprojectDao.numberByNamedQuery("numberSubprojectsIndicadoresEnPlazo", queryParams));
        this.setIndicadoresOk(subprojectDao.numberByNamedQuery("numberSubprojectsIndicadoresReportadosOk", queryParams));
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

    public void setNumberOfDevelopingSubprojects(
            Integer numberOfDevelopingSubprojects) {
        this.numberOfDevelopingSubprojects = numberOfDevelopingSubprojects;
    }

    public void setPlanesProyectoGestionados(Integer planesProyectoGestionados) {
        this.planesProyectoGestionados = planesProyectoGestionados;
    }

    public void setPlanesProyectoEnProceso(Integer planesProyectoEnProceso) {
        this.planesProyectoEnProceso = planesProyectoEnProceso;
    }

    public void setPlanesProyectoNoGestionados(Integer planesProyectoNoGestionados) {
        this.planesProyectoNoGestionados = planesProyectoNoGestionados;
    }

    public void setAuditoriasPlanificadas(Integer auditoriasPlanificadas) {
        this.auditoriasPlanificadas = auditoriasPlanificadas;
    }

    public void setAuditoriasRealizadas(Integer auditoriasRealizadas) {
        this.auditoriasRealizadas = auditoriasRealizadas;
    }

    public void setAuditoriasCanceladas(Integer auditoriasCanceladas) {
        this.auditoriasCanceladas = auditoriasCanceladas;
    }

    public void setAuditoriasAplazadas(Integer auditoriasAplazadas) {
        this.auditoriasAplazadas = auditoriasAplazadas;
    }

    public void setNoConformidadesGestionadas(Integer noConformidadesGestionadas) {
        this.noConformidadesGestionadas = noConformidadesGestionadas;
    }

    public void setNoConformidadesNoGestionadas(Integer noConformidadesNoGestionadas) {
        this.noConformidadesNoGestionadas = noConformidadesNoGestionadas;
    }

    public void setNoConformiadadesEnProceso(Integer noConformiadadesEnProceso) {
        this.noConformiadadesEnProceso = noConformiadadesEnProceso;
    }

    public void setNoConformidadesCerradas(Integer noConformidadesCerradas) {
        this.noConformidadesCerradas = noConformidadesCerradas;
    }

    public void setIndicadoresReportados(Integer indicadoresReportados) {
        this.indicadoresReportados = indicadoresReportados;
    }

    public void setIndicadoresEnPlazo(Integer indicadoresEnPlazo) {
        this.indicadoresEnPlazo = indicadoresEnPlazo;
    }

    public void setIndicadoresOk(Integer indicadoresOk) {
        this.indicadoresOk = indicadoresOk;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
