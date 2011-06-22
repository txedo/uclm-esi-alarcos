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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="subprojects")
public class Subproject {
	private int id;
	private Project project;
	private Factory factory;
	private Profile profile;
	private String name;
	private String pathToData;
	private Set<Profile> profiles;
	
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
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
    @JoinColumn(name="profile_id", nullable=false)
    	public Profile getProfile() {
		return profile;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}
	
	@Column(name="path_to_data")
	public String getPathToData() {
		return pathToData;
	}

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
            name="subprojects_has_profiles",
            joinColumns = { @JoinColumn( name="subproject_id") },
            inverseJoinColumns = @JoinColumn( name="profile_id")
    )
	public Set<Profile> getProfiles() {
		return profiles;
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
	
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPathToData(String pathToData) {
		this.pathToData = pathToData;
	}

	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}
	
}
