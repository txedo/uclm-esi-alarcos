package es.uclm.inf_cr.alarcos.desglosa_web.model;

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
@Table(name="projects")
@NamedQueries ({
    @NamedQuery(
        name = "findProjectsByCompanyId",
        query = "select p from Project p, Subproject sp, Factory f where p.id = sp.project.id and sp.factory.id = f.id and f.company.id = :id "
        ),
    @NamedQuery(
    	name = "findProjectsByFactoryId",
        query = "select p from Project p, Subproject sp, Factory f where p.id = sp.project.id and sp.factory.id = :id group by p"
        )
})
public class Project {
	@Property
	private int id;
	@Property(type="string")
	private String name;
	@Property(type="string")
	private String code;
	@Property(type="string")
	private String plan;
	private Factory mainFactory;
	private Set<Subproject> subprojects;
	@Property(embedded=true)
	private Market market;
	@Property
	private boolean audited;
	@Property
	private int totalIncidences;
	@Property
	private int repairedIncidences;
	@Property
	private int nonRepairedIncidences;
	@Property
	private int size;
	@Property
	private boolean delayed;
	@Property(type="float")
	private Float fiabilidad;
	@Property(type="float")
	private Float usabilidad;
	@Property(type="float")
	private Float eficiencia;
	@Property(type="float")
	private Float mantenibilidad;
	@Property(type="float")
	private Float portabilidad;
	@Property(type="float")
	private Float lineasDeCodigo;
	@Property(type="float")
	private Float comentarios;
	@Property(type="float")
	private Float ratioComentariosLineasDeCodigo;
	@Property(type="float")
	private Float puntosFuncion;
	@Property(type="float")
	private Float fichajeCodigo;
	@Property(type="float")
	private Float fichajeTotal;
	@Property(type="float")
	private Float ratioFichaje;
	@Property(type="float")
	private Float actividad;
	
	public Project(){}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}

	@Column(name="code")
	public String getCode() {
		return code;
	}

	@Column(name="plan")
	public String getPlan() {
		return plan;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="mainFactory_id")
	public Factory getMainFactory() {
		return mainFactory;
	}

    @OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="project")
	public Set<Subproject> getSubprojects() {
		return subprojects;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="market_id")
	public Market getMarket() {
		return market;
	}

	@Column(name="audited")
	public boolean isAudited() {
		return audited;
	}

	@Column(name="total_incidences")
	public int getTotalIncidences() {
		return totalIncidences;
	}

	@Column(name="repaired_incidences")
	public int getRepairedIncidences() {
		return repairedIncidences;
	}
	
	@Formula("total_incidences - repaired_incidences")
	public int getNonRepairedIncidences() {
		return nonRepairedIncidences;
	}

	@Column(name="size")
	public int getSize() {
		return size;
	}

	@Column(name="delayed")
	public boolean isDelayed() {
		return delayed;
	}
	
	@Column(name="fiabilidad", nullable=true)
	public Float getFiabilidad() {
		return fiabilidad;
	}

	@Column(name="usabilidad", nullable=true)
	public Float getUsabilidad() {
		return usabilidad;
	}

	@Column(name="eficiencia", nullable=true)
	public Float getEficiencia() {
		return eficiencia;
	}

	@Column(name="mantenibilidad", nullable=true)
	public Float getMantenibilidad() {
		return mantenibilidad;
	}

	@Column(name="portabilidad", nullable=true)
	public Float getPortabilidad() {
		return portabilidad;
	}

	@Column(name="lineas_de_codigo", nullable=true)
	public Float getLineasDeCodigo() {
		return lineasDeCodigo;
	}

	@Column(name="comentarios", nullable=true)
	public Float getComentarios() {
		return comentarios;
	}
	
	@Formula("comentarios / lineas_de_codigo")
	public Float getRatioComentariosLineasDeCodigo() {
		return ratioComentariosLineasDeCodigo;
	}

	@Column(name="puntos_funcion", nullable=true)
	public Float getPuntosFuncion() {
		return puntosFuncion;
	}

	@Column(name="fichaje_codigo", nullable=true)
	public Float getFichajeCodigo() {
		return fichajeCodigo;
	}

	@Column(name="fichaje_total", nullable=true)
	public Float getFichajeTotal() {
		return fichajeTotal;
	}
	
	@Formula("fichaje_codigo / fichaje_total")
	public Float getRatioFichaje() {
		return ratioFichaje;
	}
	
	@Column(name="actividad", nullable=true)
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

	public void setAudited(boolean audited) {
		this.audited = audited;
	}

	public void setTotalIncidences(int totalIncidences) {
		this.totalIncidences = totalIncidences;
	}

	public void setRepairedIncidences(int repairedIncidences) {
		this.repairedIncidences = repairedIncidences;
	}
	
	public void setNonRepairedIncidences(int nonRepairedIncidences) {
		this.nonRepairedIncidences = nonRepairedIncidences;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setDelayed(boolean delayed) {
		this.delayed = delayed;
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
		if (subprojects.size() > 1) res = this.name + " *";
		return res;
	}
	
}