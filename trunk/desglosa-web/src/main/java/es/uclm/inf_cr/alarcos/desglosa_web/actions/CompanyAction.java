package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public class CompanyAction extends ActionSupport implements GenericActionInterface {
	// companyDao is set from applicationContext.xml
	private CompanyDAO companyDao;
	// Create company attributes
	private String name;
	private String information;
	// List company attributes
	private List<Company> companies;
	
	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public String getName() {
		return name;
	}

	public String getInformation() {
		return information;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInformation(String information) {
		this.information = information;
	}
	
	public void validateDoSave(){
		if (getName().length() == 0) addFieldError("name", getText("error.company.name"));
	}

	@Override
	public String execute() throws Exception {
		companies = companyDao.getAll();
		return SUCCESS;
	}
	
	public String add() throws Exception {
		return SUCCESS;
	}

	public String save() {
		String result = SUCCESS;
		try {
			// Check if the company exits
			companyDao.getCompany(name);
			addActionError(getText("error.company.already_exists"));
			result = ERROR;
		} catch (CompanyNotFoundException e) {
			// If the company does not exists, then save it
			Company newCompany = new Company(name, information);
			companyDao.saveCompany(newCompany);
		}
		return result;
	}

	public String edit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String get() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
