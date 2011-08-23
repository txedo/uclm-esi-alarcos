package es.uclm.inf_cr.alarcos.desglosa_web.model;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="subprojects")
@NamedQueries ({
    @NamedQuery(
        name = "findSubprojectsByCompanyId",
        query = "select sp from Subproject sp, Factory f where sp.factory.id = f.id and f.company.id = :id group by sp.name"
        ),
    @NamedQuery(
        name = "findSubprojectsByProjectId",
        query = "select sp from Subproject sp where sp.project.id = :id "
        )
})
public class Subproject {
	private int id;
	private Project project;
	private Factory factory;
	private String name;
	private String csvData;
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
	
	public Subproject() {}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
    @JoinColumn(name="project_id", nullable=false)
	public Project getProject() {
		return project;
	}

	@OneToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name="factory_id")
	public Factory getFactory() {
		return factory;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	
	@Column(name="csv_data")
	public String getCsvData() {
		return csvData;
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

	public void setProject(Project project) {
		this.project = project;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCsvData(String csvData) {
		this.csvData = csvData;
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
	
}
