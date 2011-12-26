package es.uclm.inf_cr.alarcos.desglosa_web.model;

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
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name = "subprojects")
@NamedQueries({
        @NamedQuery(name = "findSubprojectsByCompanyId", query = "select sp from Subproject sp, Factory f where sp.factory.id = f.id and f.company.id = :id group by sp.name"),
        @NamedQuery(name = "findSubprojectsByFactoryId", query = "select sp from Subproject sp where sp.factory.id = :id "),
        @NamedQuery(name = "findSubprojectsByProjectId", query = "select sp from Subproject sp where sp.project.id = :id ") })
public class Subproject {
    @Property
    private int id;
    private Project project;
    @Property(embedded = true)
    private Factory factory;
    @Property
    private String name;
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

    public Subproject() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "factory_id")
    public Factory getFactory() {
        return factory;
    }

    @Column(name = "name")
    public String getName() {
        return name;
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

    public void setProject(Project project) {
        this.project = project;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    public void setName(String name) {
        this.name = name;
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

}
