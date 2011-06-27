package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.MeasureNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Measure;

public interface MeasureDAO extends GenericDAO<Measure, Long> {

	public Measure getMeasure(int id) throws MeasureNotFoundException;
	
	public Measure getMeasure(String key) throws MeasureNotFoundException;
	
	public List<Measure> getMeasures();
	
	public void saveMeasure(Measure measure);
	
	public void removeMeasure(int id);
}
