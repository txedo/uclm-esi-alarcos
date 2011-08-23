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
import javax.persistence.Transient;

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
	private int id;
	private String name;
	private String code;
	private String plan;
	private Factory mainFactory;
	private Set<Subproject> subprojects;
	private Market market;
	private boolean audited;
	private int totalIncidences;
	private int repairedIncidences;
	private int size;
	private boolean delayed;
	private String profileName;
	private Profile profile;
	private Float fiabilidad;
	private Float usabilidad;
	private Float eficiencia;
	private Float mantenibilidad;
	private Float portabilidad;
	private Float lineasDeCodigo;
	private Float comentarios;
	private Float puntosFuncion;
	private Float fichajeCodigo;
	private Float fichajeTotal;
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

	@Column(name="size")
	public int getSize() {
		return size;
	}

	@Column(name="delayed")
	public boolean isDelayed() {
		return delayed;
	}
	
	@Column(name="profile")
	public String getProfileName() {
		return profileName;
	}
	
	@Transient
	public Profile getProfile() {
		return profile;
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

	public void setSize(int size) {
		this.size = size;
	}

	public void setDelayed(boolean delayed) {
		this.delayed = delayed;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
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

	public void setPuntosFuncion(Float puntosFuncion) {
		this.puntosFuncion = puntosFuncion;
	}

	public void setFichajeCodigo(Float fichajeCodigo) {
		this.fichajeCodigo = fichajeCodigo;
	}

	public void setFichajeTotal(Float fichajeTotal) {
		this.fichajeTotal = fichajeTotal;
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
