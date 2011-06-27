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
}
