package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.MeasureDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;

public class MeasureDAOHibernate extends GenericDAOHibernate<Measure, Long> implements
		MeasureDAO {

	public MeasureDAOHibernate(Class<Measure> persistentClass) {
		super(persistentClass);
	}

	public Measure getMeasure(int id) throws MeasureNotFoundException {
		Measure m = (Measure) getHibernateTemplate().get(Measure.class, id);
		if (m == null) throw new MeasureNotFoundException("measure '" + id + "' not found...");
		return m;
	}
	
	@SuppressWarnings("rawtypes")
	public Measure getMeasure(String key) throws MeasureNotFoundException {
		List measures = getHibernateTemplate().find("from Measure where key=?", key);
	    if (measures == null || measures.isEmpty()) {
	    	throw new MeasureNotFoundException("measure '" + key + "' not found...");
	    } else {
	    	return (Measure) measures.get(0);
	    }
	}

	@SuppressWarnings("unchecked")
	public List<Measure> getMeasures() {
		return getHibernateTemplate().find("from Measure m order by upper(m.name)");
	}

	public void saveMeasure(Measure measure) {
		getHibernateTemplate().saveOrUpdate(measure);
		getHibernateTemplate().flush();
	}

	public void removeMeasure(int id) {
		Object measure = getHibernateTemplate().load(Measure.class, id);
		getHibernateTemplate().delete(measure);
	}

}
