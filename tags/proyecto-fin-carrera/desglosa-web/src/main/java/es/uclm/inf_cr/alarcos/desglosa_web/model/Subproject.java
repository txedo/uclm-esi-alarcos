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
    private Integer numeroDeCommits;
    @Property @Measure
    private Integer lcdModificadas;
    @Property @Measure
    private Integer funcionalidadesTotales;
    @Property @Measure
    private Integer funcionalidadesImplementadas;
    @Property @Measure(base = false)
    private Float ratioFuncionalidades;
    @Property @Measure
    private Integer testCasesEjecutados;
    @Property @Measure
    private Float coberturaPruebas;
    @Property @Measure
    private Float efectividadPruebas;
    @Property @Measure
    private Integer erroresDetectados;
    @Property @Measure
    private Integer erroresResueltos;
    @Property @Measure(base = false)
    private Float ratioErroresDetectadosResueltos;
    @Property @Measure
    private Float esfuerzoMtoCorrectivoEstimado;
    @Property @Measure
    private Float esfuerzoMtoCorrectivoReal;
    @Property @Measure(base = false)
    private Float ratioEsfuerzoMto;
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "factory_id")
    public Factory getFactory() {
        return factory;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "commits", insertable = false, columnDefinition = "int default 0")
    public Integer getNumeroDeCommits() {
        return numeroDeCommits;
    }

    @Column(name = "lcd_modificadas", insertable = false, columnDefinition = "int default 0")
    public Integer getLcdModificadas() {
        return lcdModificadas;
    }

    @Column(name = "func_totales", insertable = false, columnDefinition = "int default 0")
    public Integer getFuncionalidadesTotales() {
        return funcionalidadesTotales;
    }

    @Column(name = "func_implementadas", insertable = false, columnDefinition = "int default 0")
    public Integer getFuncionalidadesImplementadas() {
        return funcionalidadesImplementadas;
    }

    @Formula("func_implementadas / func_totales")
    public Float getRatioFuncionalidades() {
        if (ratioFuncionalidades == null) {
            ratioFuncionalidades = new Float(0);
        }
        return ratioFuncionalidades;
    }

    @Column(name = "testcases_exec", insertable = false, columnDefinition = "int default 0")
    public Integer getTestCasesEjecutados() {
        return testCasesEjecutados;
    }

    @Column(name = "test_cobertura", insertable = false, columnDefinition = "float default 0.0")
    public Float getCoberturaPruebas() {
        return coberturaPruebas;
    }

    @Column(name = "test_efectividad", insertable = false, columnDefinition = "float default 0.0")
    public Float getEfectividadPruebas() {
        return efectividadPruebas;
    }

    @Column(name = "errores_detectados", insertable = false, columnDefinition = "int default 0")
    public Integer getErroresDetectados() {
        return erroresDetectados;
    }

    @Column(name = "errores_resueltos", insertable = false, columnDefinition = "int default 0")
    public Integer getErroresResueltos() {
        return erroresResueltos;
    }

    @Formula("errores_resueltos / errores_detectados")
    public Float getRatioErroresDetectadosResueltos() {
        if (ratioErroresDetectadosResueltos == null) {
            ratioErroresDetectadosResueltos = new Float(0);
        }
        return ratioErroresDetectadosResueltos;
    }

    @Column(name = "esfuerzo_mto_estimado", insertable = false, columnDefinition = "float default 0.0")
    public Float getEsfuerzoMtoCorrectivoEstimado() {
        return esfuerzoMtoCorrectivoEstimado;
    }

    @Column(name = "esfuerzo_mto_real", insertable = false, columnDefinition = "float default 0.0")
    public Float getEsfuerzoMtoCorrectivoReal() {
        return esfuerzoMtoCorrectivoReal;
    }

    @Formula("esfuerzo_mto_estimado / esfuerzo_mto_real")
    public Float getRatioEsfuerzoMto() {
        if (ratioEsfuerzoMto == null) {
            ratioEsfuerzoMto = new Float(0);
        }
        return ratioEsfuerzoMto;
    }

    @Column(name = "fiabilidad", insertable = false, columnDefinition = "float default 0.0")
    public Float getFiabilidad() {
        return fiabilidad;
    }

    @Column(name = "usabilidad", insertable = false, columnDefinition = "float default 0.0")
    public Float getUsabilidad() {
        return usabilidad;
    }

    @Column(name = "eficiencia", insertable = false, columnDefinition = "float default 0.0")
    public Float getEficiencia() {
        return eficiencia;
    }

    @Column(name = "mantenibilidad", insertable = false, columnDefinition = "float default 0.0")
    public Float getMantenibilidad() {
        return mantenibilidad;
    }

    @Column(name = "portabilidad", insertable = false, columnDefinition = "float default 0.0")
    public Float getPortabilidad() {
        return portabilidad;
    }

    @Column(name = "lineas_de_codigo", insertable = false, columnDefinition = "int default 0")
    public Integer getLineasDeCodigo() {
        return lineasDeCodigo;
    }

    @Column(name = "comentarios", insertable = false, columnDefinition = "int default 0")
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

    @Column(name = "puntos_funcion", insertable = false, columnDefinition = "float default 0.0")
    public Float getPuntosFuncion() {
        return puntosFuncion;
    }

    @Column(name = "fichaje_codigo", insertable = false, columnDefinition = "float default 0.0")
    public Float getFichajeCodigo() {
        return fichajeCodigo;
    }

    @Column(name = "fichaje_total", insertable = false, columnDefinition = "float default 0.0")
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

    @Column(name = "actividad", insertable = false, columnDefinition = "float default 0.0")
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

    public void setNumeroDeCommits(Integer numeroDeCommits) {
        this.numeroDeCommits = numeroDeCommits;
    }

    public void setLcdModificadas(Integer lcdModificadas) {
        this.lcdModificadas = lcdModificadas;
    }

    public void setFuncionalidadesTotales(Integer funcionalidadesTotales) {
        this.funcionalidadesTotales = funcionalidadesTotales;
    }

    public void setFuncionalidadesImplementadas(Integer funcionalidadesImplementadas) {
        this.funcionalidadesImplementadas = funcionalidadesImplementadas;
    }

    public void setRatioFuncionalidades(Float ratioFuncionalidades) {
        this.ratioFuncionalidades = ratioFuncionalidades;
    }

    public void setTestCasesEjecutados(Integer testCasesEjecutados) {
        this.testCasesEjecutados = testCasesEjecutados;
    }

    public void setCoberturaPruebas(Float coberturaPruebas) {
        this.coberturaPruebas = coberturaPruebas;
    }

    public void setEfectividadPruebas(Float efectividadPruebas) {
        this.efectividadPruebas = efectividadPruebas;
    }

    public void setErroresDetectados(Integer erroresDetectados) {
        this.erroresDetectados = erroresDetectados;
    }

    public void setErroresResueltos(Integer erroresResueltos) {
        this.erroresResueltos = erroresResueltos;
    }

    public void setRatioErroresDetectadosResueltos(
            Float ratioErroresDetectadosResueltos) {
        this.ratioErroresDetectadosResueltos = ratioErroresDetectadosResueltos;
    }

    public void setEsfuerzoMtoCorrectivoEstimado(
            Float esfuerzoMtoCorrectivoEstimado) {
        this.esfuerzoMtoCorrectivoEstimado = esfuerzoMtoCorrectivoEstimado;
    }

    public void setEsfuerzoMtoCorrectivoReal(Float esfuerzoMtoCorrectivoReal) {
        this.esfuerzoMtoCorrectivoReal = esfuerzoMtoCorrectivoReal;
    }

    public void setRatioEsfuerzoMto(Float ratioEsfuerzoMto) {
        this.ratioEsfuerzoMto = ratioEsfuerzoMto;
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
    
    @Override
    public String toString() {
        return this.name;
    }

}
