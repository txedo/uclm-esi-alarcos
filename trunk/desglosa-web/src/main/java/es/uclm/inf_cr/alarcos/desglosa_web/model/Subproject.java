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

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Measure;
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
    @Property @Measure
    private Float fiabilidad;
    @Property @Measure
    private Float usabilidad;
    @Property @Measure
    private Float eficiencia;
    @Property @Measure
    private Float mantenibilidad;
    @Property @Measure
    private Float portabilidad;
    @Property @Measure
    private Integer lineasDeCodigo;
    @Property @Measure
    private Integer comentarios;
    @Property @Measure(base = false)
    private Float ratioComentariosLineasDeCodigo;
    @Property @Measure
    private Float puntosFuncion;
    @Property @Measure
    private Float fichajeCodigo;
    @Property @Measure
    private Float fichajeTotal;
    @Property @Measure(base = false)
    private Float ratioFichaje;
    @Property @Measure
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

    @Column(name = "lineas_de_codigo", nullable = true, columnDefinition = "int default 0.0")
    public Integer getLineasDeCodigo() {
        return lineasDeCodigo;
    }

    @Column(name = "comentarios", nullable = true, columnDefinition = "int default 0.0")
    public Integer getComentarios() {
        return comentarios;
    }

    @Formula("comentarios / lineas_de_codigo")
    public Float getRatioComentariosLineasDeCodigo() {
        // This evaluation is done to return 0 in case of divide by 0 expressions
        if (ratioComentariosLineasDeCodigo == null) {
            ratioComentariosLineasDeCodigo = new Float(0);
        }
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
        // This evaluation is done to return 0 in case of divide by 0 expressions
        if (ratioFichaje == null) {
            ratioFichaje = new Float(0);
        }
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

    public void setLineasDeCodigo(Integer lineasDeCodigo) {
        this.lineasDeCodigo = lineasDeCodigo;
    }

    public void setComentarios(Integer comentarios) {
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
