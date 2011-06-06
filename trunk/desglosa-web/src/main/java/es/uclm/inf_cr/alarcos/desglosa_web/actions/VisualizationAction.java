package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public class VisualizationAction extends ActionSupport {
	private FactoryDAO factoryDao;
	private CompanyDAO companyDao;
	
	private List<Factory> factories;
	private List<Company> companies;

	public List<Factory> getFactories() {
		return factories;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setFactoryDao(FactoryDAO factoryDao) {
		this.factoryDao = factoryDao;
	}

	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	@Override
	public String execute() throws Exception {
		factories = factoryDao.getFactories();
		companies = companyDao.getCompanies();
		return SUCCESS;
	}

}
