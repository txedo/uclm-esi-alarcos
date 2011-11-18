package es.uclm.inf_cr.alarcos.desglosa_web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = -5782725748407324783L;
    
    private int id;
    private String name;
    private String description;
    
    public Role () {
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    @Column(nullable = false, length = 45, unique = true)
    public String getName() {
        return name;
    }

    @Column(nullable = true, length = 45, unique = false)
    public String getDescription() {
        return description;
    }
    
    @Transient
    public String getAuthority() {
        return getName();
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        final Role role = (Role) o;
        return !(name != null ? !name.equals(role.name) : role.name != null);
    }

    public int compareTo(Object o) {
        return (equals(o) ? 0 : -1);
    }

}
