package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.HashMap;
import java.util.Map;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

@Entity
@Table(name = "subprojects")
@NamedQueries({
        @NamedQuery(name = "findSubprojectsByCompanyId", query = "select sp from Subproject sp, Factory f where sp.factory.id = f.id and f.company.id = :id group by sp.name"),
        @NamedQuery(name = "findSubprojectsByFactoryId", query = "select sp from Subproject sp where sp.factory.id = :id "),
        @NamedQuery(name = "findSubprojectsByProjectId", query = "select sp from Subproject sp where sp.project.id = :id "),
        @NamedQuery(name = "numberSubprojectsPlanesGestionados", query = "select count(sp) from Subproject sp where sp.plan='Gestionado' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsPlanesEnProceso", query = "select count(sp) from Subproject sp where sp.plan='En proceso' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsPlanesNoGestionados", query = "select count(sp) from Subproject sp where sp.plan='No gestionado' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsAuditoriasPlanificadas", query = "select count(sp) from Subproject sp where sp.audit='Planificada' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsAuditoriasRealizadas", query = "select count(sp) from Subproject sp where sp.audit='Realizada' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsAuditoriasCanceladas", query = "select count(sp) from Subproject sp where sp.audit='Cancelada' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsAuditoriasAplazadas", query = "select count(sp) from Subproject sp where sp.audit='Aplazada' and sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsNCGestionadas", query = "select sum(sp.noConformidadesGestionadas) from Subproject sp where sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsNCNoGestionadas", query = "select sum(sp.noConformidadesNoGestionadas) from Subproject sp where sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsNCEnProceso", query = "select sum(sp.noConformidadesEnProceso) from Subproject sp where sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsNCCerradas", query = "select sum(sp.noConformidadesCerradas) from Subproject sp where sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsIndicadoresReportados", query = "select sum(sp.indicadoresReportados) from Subproject sp where sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsIndicadoresEnPlazo", query = "select sum(sp.indicadoresEnPlazo) from Subproject sp where sp.factory.id = :id"),
        @NamedQuery(name = "numberSubprojectsIndicadoresReportadosOk", query = "select sum(sp.indicadoresOk) from Subproject sp where sp.factory.id = :id")
        })
public class Subproject {
    @Property
    private int id;
    private Map<String, Object> measures = new HashMap<String, Object>();
    private Project project;
    @Property(embedded = true)
    private Factory factory;
    @Property
    private String name;
    @Property @Measure
    private Integer funcionalidadesTotales;
    @Property @Measure
    private Integer funcionalidadesImplementadas;
    @Property @Measure(base = false)
    private Float ratioFuncionalidades;
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
    private Integer lineasDeCodigo;
    @Property @Measure
    private Integer comentarios;
    @Property @Measure(base = false)
    private Float ratioComentariosLineasDeCodigo;
    @Property @Measure
    private Float fichajeCodigo;
    @Property @Measure
    private Float fichajeTotal;
    @Property @Measure(base = false)
    private Float ratioFichaje;
    @Property @Measure
    private Float actividad;
    @Property(description = "Los valores posibles son Gestionado, En proceso, No gestionado.") @Measure (description = "Los valores posibles son Gestionados, En proceso, No gestionados.")
    private String plan;
    @Property(description = "Los valores posibles son Planificada, Realizada, Cancelada, Aplazada") @Measure (description = "Los valores posibles son Planificada, Realizada, Cancelada, Aplazada")
    private String audit;
    @Property @Measure
    private Integer noConformidadesGestionadas;
    @Property @Measure
    private Integer noConformidadesNoGestionadas;
    @Property @Measure
    private Integer noConformidadesEnProceso;
    @Property @Measure
    private Integer noConformidadesCerradas;
    @Property @Measure
    private Integer indicadoresReportados;
    @Property @Measure
    private Integer indicadoresEnPlazo;
    @Property @Measure
    private Integer indicadoresOk;

    public Subproject() {
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
    
    @Column(name = "plan")
    public String getPlan() {
        if (plan == null) {
            plan = "";
        }
        return plan;
    }
    
    @Column(name = "audit")
    public String getAudit() {
        if (audit == null) {
            audit = "";
        }
        return audit;
    }
    
    @Column(name = "ncGestionadas", insertable = false, columnDefinition = "int default 0")
    public Integer getNoConformidadesGestionadas() {
        return noConformidadesGestionadas;
    }

    @Column(name = "ncNoGestionadas", insertable = false, columnDefinition = "int default 0")
    public Integer getNoConformidadesNoGestionadas() {
        return noConformidadesNoGestionadas;
    }

    @Column(name = "ncEnProceso", insertable = false, columnDefinition = "int default 0")
    public Integer getNoConformidadesEnProceso() {
        return noConformidadesEnProceso;
    }

    @Column(name = "ncCerradas", insertable = false, columnDefinition = "int default 0")
    public Integer getNoConformidadesCerradas() {
        return noConformidadesCerradas;
    }
    
    @Column(name = "iReportados", insertable = false, columnDefinition = "int default 0")
    public Integer getIndicadoresReportados() {
        return indicadoresReportados;
    }

    @Column(name = "iReportadosEnPlazo", insertable = false, columnDefinition = "int default 0")
    public Integer getIndicadoresEnPlazo() {
        return indicadoresEnPlazo;
    }

    @Column(name = "iReportadosOk", insertable = false, columnDefinition = "int default 0")
    public Integer getIndicadoresOk() {
        return indicadoresOk;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setMeasures(Map<String, Object> measures) {
        this.measures = measures;
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

    public void setFuncionalidadesTotales(Integer funcionalidadesTotales) {
        this.funcionalidadesTotales = funcionalidadesTotales;
    }

    public void setFuncionalidadesImplementadas(Integer funcionalidadesImplementadas) {
        this.funcionalidadesImplementadas = funcionalidadesImplementadas;
    }

    public void setRatioFuncionalidades(Float ratioFuncionalidades) {
        this.ratioFuncionalidades = ratioFuncionalidades;
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
    
    public void setPlan(String plan) {
        this.plan = plan;
    }
    
    public void setAudit(String audit) {
        this.audit = audit;
    }
    
    public void setNoConformidadesGestionadas(Integer noConformidadesGestionadas) {
        this.noConformidadesGestionadas = noConformidadesGestionadas;
    }

    public void setNoConformidadesNoGestionadas(Integer noConformidadesNoGestionadas) {
        this.noConformidadesNoGestionadas = noConformidadesNoGestionadas;
    }

    public void setNoConformidadesEnProceso(Integer noConformidadesEnProceso) {
        this.noConformidadesEnProceso = noConformidadesEnProceso;
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
