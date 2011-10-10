package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

@Entity
@Table(name="groups")
public class Group {
	private int id;
	private String name;
	private String description;
	private Set<Role> roles = new HashSet<Role>();
	
	public Group() {
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(nullable=false,length=45,unique=true)
	public String getName() {
		return name;
	}

	@Column(nullable=true,length=45,unique=false)
	public String getDescription() {
		return description;
	}

	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(
			name="groups_roles",
			joinColumns = { @JoinColumn( name="group_id") },
			inverseJoinColumns = @JoinColumn( name="role_id")
	)   
	public Set<Role> getRoles() {
		return roles;
	}
    
	@Transient
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role r : this.roles) {
			authorities.add(new GrantedAuthorityImpl(r.getName()));
		}
		return authorities;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
