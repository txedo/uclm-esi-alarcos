package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public interface FactoryDAO extends GenericDAO<Factory, Long> {
	
	public Factory getFactory(int id) throws FactoryNotFoundException;
	
	public Factory getFactory(String name) throws FactoryNotFoundException;
	
	public List<Factory> getFactories();
	
	public void saveFactory(Factory factory);
	
	public void removeFactory(int id);
}
