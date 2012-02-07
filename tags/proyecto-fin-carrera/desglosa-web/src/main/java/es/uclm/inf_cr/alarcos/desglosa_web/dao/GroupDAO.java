package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Group;

public interface GroupDAO extends GenericDAO<Group, Long> {
    Group getGroupByName(String groupname);

    Group getGroup(int id);

    List<Group> getGroups();

    void saveGroup(Group group);

    void removeGroup(int id);
}
