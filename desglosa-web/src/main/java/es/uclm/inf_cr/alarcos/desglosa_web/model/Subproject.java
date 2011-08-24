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

import org.hibernate.annotations.Formula;

import es.uclm.inf_cr.alarcos.desglosa_web.model.util.Property;

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
	@Property
	private int id;
	private Project project;
	@Property(embedded=true)
	private Factory factory;
	@Property(type="string")
	private String name;
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
