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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="projects")
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

	@Override
	public String toString() {
		String res = this.name;
		if (subprojects.size() > 1) res = this.name + " *";
		return res;
	}
	
}
