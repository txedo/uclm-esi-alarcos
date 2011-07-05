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

	@Override
	public String toString() {
		String res = this.name;
		if (subprojects.size() > 1) res = this.name + " *";
		return res;
	}
	
}
