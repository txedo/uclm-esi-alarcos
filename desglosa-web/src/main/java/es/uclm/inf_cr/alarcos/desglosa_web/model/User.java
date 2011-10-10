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
	private boolean accountExpired;
	private boolean credentialsExpired;
	private boolean accountLocked;
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
	public boolean isAccountExpired() {
		return accountExpired;
	}

	@Column(name="credentials_expired",nullable=false)
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	@Column(name="account_locked",nullable=false)
	public boolean isAccountLocked() {
		return accountLocked;
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
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Group g : this.groups) {
			authorities.addAll(g.getAuthorities());
		}
		return authorities.toArray(new GrantedAuthority[0]);
	}
	
	@Transient
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return !accountLocked;
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

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

}
