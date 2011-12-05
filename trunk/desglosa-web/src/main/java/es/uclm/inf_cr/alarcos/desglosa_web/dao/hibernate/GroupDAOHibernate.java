package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.GroupDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Group;

public class GroupDAOHibernate extends GenericDAOHibernate<Group, Long>
        implements GroupDAO {

    public GroupDAOHibernate(Class<Group> persistentClass) {
        super(persistentClass);
    }

    @SuppressWarnings("unchecked")
    public Group getGroupByName(String groupname) {
        List<Group> groups = getHibernateTemplate().find(
                "from Group where name=?", groupname);
        if (groups.isEmpty()) {
            return null;
        } else {
            return (Group) groups.get(0);
        }
    }

    public Group getGroup(int id) {
        return (Group) getHibernateTemplate().get(Group.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Group> getGroups() {
        return getHibernateTemplate().find(
                "from Group g order by upper(g.name)");
    }

    public void saveGroup(Group group) {
        getHibernateTemplate().saveOrUpdate(group);
        getHibernateTemplate().flush();
    }

    public void removeGroup(int id) {
        Object group = getHibernateTemplate().load(Group.class, id);
        getHibernateTemplate().delete(group);
    }

}
