package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

public class VisualizationAction extends ActionSupport {
	private int idCompany;
	private int idFactory;
	private FactoryDAO factoryDao;
	private CompanyDAO companyDao;
	
	private List<Factory> factories;
	private List<Company> companies;

	public int getIdCompany() {
		return idCompany;
	}
	
	public int getIdFactory() {
		return idFactory;
	}
	
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
	
	public void setIdCompany(int id) {
		this.idCompany = id;
	}
	
	public void setIdFactory(int id) {
		this.idFactory = id;
	}

	@Override
	public String execute() throws Exception {
		factories = factoryDao.getFactories();
		companies = companyDao.getCompanies();
		return SUCCESS;
	}
	
	public String getFactoriesJSON() {
		if (idFactory == 0) {
			factories = factoryDao.getFactories();
		}
		else {
			try {
				factories = new ArrayList<Factory>();
				factories.add(factoryDao.getFactory(idFactory));
			} catch (FactoryNotFoundException e) {
				return ERROR;
			}
		}
		return SUCCESS;
	}
	
	public String getFactoriesFromCompanyJSON() {
		if (idCompany == 0) {
			factories = factoryDao.getFactories();
		}
		else {
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("idCompany", idCompany);
			factories = factoryDao.findByNamedQuery("findByCompanyId", queryParams);
		}
		return SUCCESS;
	}
	
	public String getCompaniesJSON() {
		if (idCompany == 0) {
			companies = companyDao.getCompanies();
		}
		else {
			try {
				companies = new ArrayList<Company>();
				companies.add(companyDao.getCompany(idCompany));
			} catch (CompanyNotFoundException e) {
				return ERROR;
			}
		}
		return SUCCESS;
	}
	

}
