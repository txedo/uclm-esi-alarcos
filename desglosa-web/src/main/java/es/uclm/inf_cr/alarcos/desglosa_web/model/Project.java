package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name = "projects")
@NamedQueries({
        @NamedQuery(name = "findProjectsByCompanyId", query = "select p from Project p, Subproject sp, Factory f where p.id = sp.project.id and sp.factory.id = f.id and f.company.id = :id "),
        @NamedQuery(name = "findProjectsByFactoryId", query = "select p from Project p, Subproject sp, Factory f where p.id = sp.project.id and sp.factory.id = :id group by p") })
public class Project {
    @Property
    private int id;
    @Property
    private String name;
    @Property
    private String code;
    @Property
    private String plan;
    private Factory mainFactory;
    private Set<Subproject> subprojects = new HashSet<Subproject>();
    @Property(embedded = true)
    private Market market;
    @Property
    private Boolean audited;
    @Property
    private Integer totalIncidences;
    @Property
    private Integer repairedIncidences;
    @Property
    private Integer nonRepairedIncidences;
    @Property
    private Integer size;
    @Property
    private Boolean delay;
    @Property
    private Float fiabilidad;
    @Property
    private Float usabilidad;
    @Property
    private Float eficiencia;
    @Property
    private Float mantenibilidad;
    @Property
    private Float portabilidad;
    @Property
    private Float lineasDeCodigo;
    @Property
    private Float comentarios;
    @Property
    private Float ratioComentariosLineasDeCodigo;
    @Property
    private Float puntosFuncion;
    @Property
    private Float fichajeCodigo;
    @Property
    private Float fichajeTotal;
    @Property
    private Float ratioFichaje;
    @Property
    private Float actividad;

    public Project() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    @Column(name = "plan")
    public String getPlan() {
        return plan;
    }

    @OneToOne
    @JoinColumn(name = "mainFactory_id", nullable = false)
    public Factory getMainFactory() {
        return mainFactory;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "project")
    public Set<Subproject> getSubprojects() {
        return subprojects;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "market_id")
    public Market getMarket() {
        return market;
    }

    @Column(name = "audited", columnDefinition = "boolean default false")
    public Boolean isAudited() {
        return audited;
    }

    @Column(name = "total_incidences", columnDefinition = "int default 0")
    public Integer getTotalIncidences() {
        return totalIncidences;
    }

    @Column(name = "repaired_incidences", columnDefinition = "int default 0")
    public Integer getRepairedIncidences() {
        return repairedIncidences;
    }

    @Formula("total_incidences - repaired_incidences")
    public Integer getNonRepairedIncidences() {
        return nonRepairedIncidences;
    }

    @Column(name = "size", columnDefinition = "int default 0")
    public Integer getSize() {
        return size;
    }

    @Column(name = "delay", columnDefinition = "boolean default false")
    public Boolean isDelay() {
        return delay;
    }

    @Column(name = "fiabilidad", nullable = true, columnDefinition = "float default 0.0")
    public Float getFiabilidad() {
        return fiabilidad;
    }

    @Column(name = "usabilidad", nullable = true, columnDefinition = "float default 0.0")
    public Float getUsabilidad() {
        return usabilidad;
    }

    @Column(name = "eficiencia", nullable = true, columnDefinition = "float default 0.0")
    public Float getEficiencia() {
        return eficiencia;
    }

    @Column(name = "mantenibilidad", nullable = true, columnDefinition = "float default 0.0")
    public Float getMantenibilidad() {
        return mantenibilidad;
    }

    @Column(name = "portabilidad", nullable = true, columnDefinition = "float default 0.0")
    public Float getPortabilidad() {
        return portabilidad;
    }

    @Column(name = "lineas_de_codigo", nullable = true, columnDefinition = "float default 0.0")
    public Float getLineasDeCodigo() {
        return lineasDeCodigo;
    }

    @Column(name = "comentarios", nullable = true, columnDefinition = "float default 0.0")
    public Float getComentarios() {
        return comentarios;
    }

    @Formula("comentarios / lineas_de_codigo")
    public Float getRatioComentariosLineasDeCodigo() {
        return ratioComentariosLineasDeCodigo;
    }

    @Column(name = "puntos_funcion", nullable = true, columnDefinition = "float default 0.0")
    public Float getPuntosFuncion() {
        return puntosFuncion;
    }

    @Column(name = "fichaje_codigo", nullable = true, columnDefinition = "float default 0.0")
    public Float getFichajeCodigo() {
        return fichajeCodigo;
    }

    @Column(name = "fichaje_total", nullable = true, columnDefinition = "float default 0.0")
    public Float getFichajeTotal() {
        return fichajeTotal;
    }

    @Formula("fichaje_codigo / fichaje_total")
    public Float getRatioFichaje() {
        return ratioFichaje;
    }

    @Column(name = "actividad", nullable = true, columnDefinition = "float default 0.0")
    public Float getActividad() {
        return actividad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void setMainFactory(Factory mainFactory) {
        this.mainFactory = mainFactory;
    }

    public void setSubprojects(Set<Subproject> subprojects) {
        this.subprojects = subprojects;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public void setAudited(Boolean audited) {
        this.audited = audited;
    }

    public void setTotalIncidences(Integer totalIncidences) {
        this.totalIncidences = totalIncidences;
    }

    public void setRepairedIncidences(Integer repairedIncidences) {
        this.repairedIncidences = repairedIncidences;
    }

    public void setNonRepairedIncidences(Integer nonRepairedIncidences) {
        this.nonRepairedIncidences = nonRepairedIncidences;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setDelay(Boolean delayed) {
        this.delay = delayed;
    }

    public void setFiabilidad(Float fiabilidad) {
        this.fiabilidad = fiabilidad;
    }

    public void setUsabilidad(Float usabilidad) {
        this.usabilidad = usabilidad;
    }

    public void setEficiencia(Float eficiencia) {
        this.eficiencia = eficiencia;
    }

    public void setMantenibilidad(Float mantenibilidad) {
        this.mantenibilidad = mantenibilidad;
    }

    public void setPortabilidad(Float portabilidad) {
        this.portabilidad = portabilidad;
    }

    public void setLineasDeCodigo(Float lineasDeCodigo) {
        this.lineasDeCodigo = lineasDeCodigo;
    }

    public void setComentarios(Float comentarios) {
        this.comentarios = comentarios;
    }

    public void setRatioComentariosLineasDeCodigo(
            Float ratioComentariosLineasDeCodigo) {
        this.ratioComentariosLineasDeCodigo = ratioComentariosLineasDeCodigo;
    }

    public void setPuntosFuncion(Float puntosFuncion) {
        this.puntosFuncion = puntosFuncion;
    }

    public void setFichajeCodigo(Float fichajeCodigo) {
        this.fichajeCodigo = fichajeCodigo;
    }

    public void setFichajeTotal(Float fichajeTotal) {
        this.fichajeTotal = fichajeTotal;
    }

    public void setRatioFichaje(Float ratioFichaje) {
        this.ratioFichaje = ratioFichaje;
    }

    public void setActividad(Float actividad) {
        this.actividad = actividad;
    }

    @Override
    public String toString() {
        String res = this.name;
        if (subprojects.size() > 1)
            res = this.name + " *";
        return res;
    }

}
