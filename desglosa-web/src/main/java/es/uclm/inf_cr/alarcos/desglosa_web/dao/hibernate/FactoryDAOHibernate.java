package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public class FactoryDAOHibernate extends GenericDAOHibernate<Factory, Long> implements
        FactoryDAO {

    public FactoryDAOHibernate(Class<Factory> persistentClass) {
        super(persistentClass);
    }

    public Factory getFactory(int id) throws FactoryNotFoundException {
        Factory f = (Factory) getHibernateTemplate().get(Factory.class, id);
        if (f == null) throw new FactoryNotFoundException("factory '" + id + "' not found...");
        return f;
    }

    @SuppressWarnings("rawtypes")
    public Factory getFactory(String name) throws FactoryNotFoundException {
        List factories = getHibernateTemplate().find("from Factory where name=?", name);
        if (factories == null || factories.isEmpty()) {
            throw new FactoryNotFoundException("factory '" + name + "' not found...");
        } else {
            return (Factory) factories.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Factory> getFactories() {
        return getHibernateTemplate().find("from Factory f order by upper(f.name)");
    }

    public void saveFactory(Factory factory) {
        getHibernateTemplate().saveOrUpdate(factory);
        getHibernateTemplate().flush();
    }

    public void removeFactory(int id) {
        Object factory = getHibernateTemplate().load(Factory.class, id);
        getHibernateTemplate().delete(factory);
    }

}
