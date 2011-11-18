package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.RoleDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Role;

public class RoleDAOHibernate extends GenericDAOHibernate<Role, Long> implements RoleDAO {

    public RoleDAOHibernate(Class<Role> persistentClass) {
        super(persistentClass);
    }

    @SuppressWarnings("unchecked")
    public Role getRoleByName(String rolename) {
        List<Role> roles = getHibernateTemplate().find("from Role where name=?", rolename);
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }

    public Role getRole(int id) {
        return (Role) getHibernateTemplate().get(Role.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        return getHibernateTemplate().find("from Role r order by upper(r.name)");
    }

    public void saveRole(Role role) {
        getHibernateTemplate().saveOrUpdate(role);
        getHibernateTemplate().flush();
    }

    public void removeRole(int id) {
        Object role = getHibernateTemplate().load(Role.class, id);
        getHibernateTemplate().delete(role);
    }

}
