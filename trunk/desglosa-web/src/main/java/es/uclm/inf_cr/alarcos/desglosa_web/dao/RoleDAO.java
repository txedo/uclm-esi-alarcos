package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Role;

public interface RoleDAO extends GenericDAO<Role, Long> {
	Role getRoleByName(String rolename);
	Role getRole(int id);
	List<Role> getRoles();
	void saveRole(Role role);
	void removeRole(int id);
}
