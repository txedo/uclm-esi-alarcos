package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Role;

public interface RoleDAO extends GenericDAO<Role, Long> {
	
	public Role getRoleByName(String rolename);
	
	public Role getRole(int id);
	
	public List<Role> getRoles();
	
	public void saveRole(Role role);
	
	public void removeRole(int id);
}
