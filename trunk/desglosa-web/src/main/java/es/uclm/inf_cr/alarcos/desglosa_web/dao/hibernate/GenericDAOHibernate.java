package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.GenericDAO;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * 
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *      &lt;bean id="fooDao" class="es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate.GenericDAOHibernate"&gt;
 *          &lt;constructor-arg value="es.uclm.inf_cr.alarcos.desglosa_web.model.Foo"/&gt;
 *          &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 * 
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 */
public class GenericDAOHibernate<T, PK extends Serializable> extends
        HibernateDaoSupport implements GenericDAO<T, PK> {

    private Class<T> persistentClass;

    public GenericDAOHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return super.getHibernateTemplate().loadAll(this.persistentClass);
    }

    public List<T> getAllDistinct() {
        Collection<T> result = new LinkedHashSet<T>(getAll());
        return new ArrayList<T>(result);
    }

    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass,
                id);

        if (entity == null)
            throw new ObjectRetrievalFailureException(this.persistentClass, id);

        return entity;
    }

    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = (T) super.getHibernateTemplate().get(this.persistentClass,
                id);
        return entity != null;
    }

    @SuppressWarnings("unchecked")
    public T save(T object) {
        return (T) super.getHibernateTemplate().merge(object);
    }

    public void remove(PK id) {
        super.getHibernateTemplate().delete(this.get(id));
    }

    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];
        int index = 0;
        Iterator<String> i = queryParams.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next();
            params[index] = key;
            values[index++] = queryParams.get(key);
        }
        return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values);
    }
    
    public int numberByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];
        int index = 0;
        Iterator<String> i = queryParams.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next();
            params[index] = key;
            values[index++] = queryParams.get(key);
        }
        Long number = (Long)getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, params, values).get(0);
        if (number == null) number = 0l;
        return number.intValue();
    }
    
}
