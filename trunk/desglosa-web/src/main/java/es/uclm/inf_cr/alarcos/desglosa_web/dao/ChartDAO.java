package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.ChartNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Chart;

public interface ChartDAO extends GenericDAO<Chart, Long> {

	public Chart getChart(int id) throws ChartNotFoundException;
	
	public Chart getChart(String name) throws ChartNotFoundException;
	
	public List<Chart> getCharts();
	
	public void saveChart(Chart chart);
	
	public void removeChart(int id);
}
