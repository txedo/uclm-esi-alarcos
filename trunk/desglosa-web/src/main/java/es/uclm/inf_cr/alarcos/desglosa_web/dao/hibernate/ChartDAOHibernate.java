package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.ChartDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ChartNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Chart;

public class ChartDAOHibernate extends GenericDAOHibernate<Chart, Long> implements
		ChartDAO {

	public ChartDAOHibernate(Class<Chart> persistentClass) {
		super(persistentClass);
	}

	public Chart getChart(int id) throws ChartNotFoundException {
		Chart c = (Chart) getHibernateTemplate().get(Chart.class, id);
		if (c == null) throw new ChartNotFoundException("chart '" + id + "' not found...");
		return c;
	}
	
	@SuppressWarnings("rawtypes")
	public Chart getChart(String name) throws ChartNotFoundException {
		List charts = getHibernateTemplate().find("from Chart where name=?", name);
	    if (charts == null || charts.isEmpty()) {
	    	throw new ChartNotFoundException("chart '" + name + "' not found...");
	    } else {
	    	return (Chart) charts.get(0);
	    }
	}

	@SuppressWarnings("unchecked")
	public List<Chart> getCharts() {
		return getHibernateTemplate().find("from Chart c order by upper(c.name)");
	}

	public void saveChart(Chart chart) {
		getHibernateTemplate().saveOrUpdate(chart);
		getHibernateTemplate().flush();
	}

	public void removeChart(int id) {
		Object chart = getHibernateTemplate().load(Chart.class, id);
		getHibernateTemplate().delete(chart);
	}


}
