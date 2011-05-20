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
import org.springframework.security.userdetails.UserDetails;

@Entity
@Table(name="users")
public class User implements UserDetails {
	private int id;
	private String username;
	private String password;
	private boolean enabled;
	private boolean accountNonExpired;
	private boolean credentialsNonExpired;
	private boolean accountNonLocked;
	private Set<Group> groups = new HashSet<Group>();
	
	public User() {
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	@Column(nullable=false,length=45,unique=true)
	public String getUsername() {
		return username;
	}

	@Column(nullable=false)
	public String getPassword() {
		return password;
	}

	@Column(name="enabled")
	public boolean isEnabled() {
		return enabled;
	}

	@Column(name="account_expired",nullable=false)
	public boolean isAccountNonExpired() {
		return !accountNonExpired;
	}

	@Column(name="credentials_expired",nullable=false)
	public boolean isCredentialsNonExpired() {
		return !credentialsNonExpired;
	}

	@Column(name="account_locked",nullable=false)
	public boolean isAccountNonLocked() {
		return !accountNonLocked;
	}

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
            name="users_groups",
            joinColumns = { @JoinColumn( name="user_id") },
            inverseJoinColumns = @JoinColumn( name="group_id")
    )    
	public Set<Group> getGroups() {
		return groups;
	}

	@Transient
	public GrantedAuthority[] getAuthorities() {
		//return authorities;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Group g : this.groups) {
			authorities.addAll(g.getAuthorities());
		}
		//return new GrantedAuthority[]{new GrantedAuthorityImpl("ROLE_ADMIN")};
		return authorities.toArray(new GrantedAuthority[0]);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

}
