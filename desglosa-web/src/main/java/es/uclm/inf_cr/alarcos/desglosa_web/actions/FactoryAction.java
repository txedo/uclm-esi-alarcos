package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public class FactoryAction extends ActionSupport {
	// factoryDao is set from applicationContext.xml
	private FactoryDAO factoryDao;
	// List Action needed attributes
	private List<Factory> factories;

	public List<Factory> getFactories() {
		return factories;
	}

	public void setFactoryDao(FactoryDAO factoryDao) {
		this.factoryDao = factoryDao;
	}


	@Override
	public String execute() throws Exception {
		factories = factoryDao.getAll();
		return SUCCESS;
	}
	
	
}
