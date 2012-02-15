package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.MeasureDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;
import es.uclm.inf_cr.alarcos.desglosa_web.persistence.DataSourceUtil;
import es.uclm.inf_cr.alarcos.desglosa_web.util.ApplicationContextProvider;

public class MeasureDAOHibernate extends GenericDAOHibernate<Measure, Long> implements MeasureDAO {

    public MeasureDAOHibernate(Class<Measure> persistentClass) {
        super(persistentClass);
    }

    public Measure getMeasure(int id) throws MeasureNotFoundException {
        Measure c = (Measure) getHibernateTemplate().get(Measure.class, id);
        if (c == null) {
            throw new MeasureNotFoundException("Measure '" + id + "' not found...");
        }
                    
        return c;
    }

    @SuppressWarnings("rawtypes")
    public Measure getMeasure(String name) throws MeasureNotFoundException {
        List companies = getHibernateTemplate().find("from Measure where name=?", name);
        if (companies == null || companies.isEmpty()) {
            throw new MeasureNotFoundException("Measure '" + name + "' not found...");
        } else {
            return (Measure) companies.get(0);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Measure> getMeasuresByEntity(String entity) {
        return getHibernateTemplate().find("from Measure where entity=?", entity);
    }

    @SuppressWarnings("unchecked")
    public List<Measure> getMeasures() {
        return getHibernateTemplate().find("from Measure c order by upper(c.name)");
    }

    public void saveMeasure(Measure measure) {
        // Save measure information into measures table
        getHibernateTemplate().saveOrUpdate(measure);
        getHibernateTemplate().flush();
        // Alter entity table adding a new column
        ((DataSourceUtil) ApplicationContextProvider.getBean("dataSourceUtil")).alterTableByAddingColumn(measure.getDbTable(), measure.getName(), measure.getColumnType(), measure.getDefaultValue().toString());
    }

    public void removeMeasure(int id) {
        // Remove measure information from measures table
        Object measure = getHibernateTemplate().load(Measure.class, id);
        getHibernateTemplate().delete(measure);
        // Altery entity table removing a column
        ((DataSourceUtil) ApplicationContextProvider.getBean("dataSourceUtil")).alterTableByRemovingColumn(((Measure) measure).getDbTable(), ((Measure) measure).getName());
    }

}
