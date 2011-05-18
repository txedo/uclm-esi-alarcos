package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Group;

public interface GroupDAO extends GenericDAO<Group, Long> {
	
	public Group getGroupByName(String groupname);
	
	public Group getGroup(int id);
	
	public List<Group> getGroups();
	
	public void saveGroup(Group group);
	
	public void removeGroup(int id);
}
