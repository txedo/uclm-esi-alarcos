package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.SubprojectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class SubprojectDAOHibernate extends
        GenericDAOHibernate<Subproject, Long> implements SubprojectDAO {

    public SubprojectDAOHibernate(Class<Subproject> persistentClass) {
        super(persistentClass);
    }

    public Subproject getSubproject(int id) throws SubprojectNotFoundException {
        Subproject sp = (Subproject) getHibernateTemplate().get(
                Subproject.class, id);
        if (sp == null)
            throw new SubprojectNotFoundException("subproject '" + id
                    + "' not found...");
        return sp;
    }

    @SuppressWarnings("rawtypes")
    public Subproject getSubproject(String name)
            throws SubprojectNotFoundException {
        List subprojects = getHibernateTemplate().find(
                "from Subproject where name=?", name);
        if (subprojects == null || subprojects.isEmpty()) {
            throw new SubprojectNotFoundException("subproject '" + name
                    + "' not found...");
        } else {
            return (Subproject) subprojects.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Subproject> getSubprojects() {
        return getHibernateTemplate().find(
                "from Subproject sp order by upper(sp.name)");
    }

    public void saveSubproject(Subproject subproject) {
        getHibernateTemplate().saveOrUpdate(subproject);
        getHibernateTemplate().flush();
    }

    public void removeSubproject(int id) {
        Object subproject = getHibernateTemplate().load(Subproject.class, id);
        getHibernateTemplate().delete(subproject);
    }

}