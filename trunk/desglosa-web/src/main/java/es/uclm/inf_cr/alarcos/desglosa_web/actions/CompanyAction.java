package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public class CompanyAction extends ActionSupport implements GenericActionInterface {
	private CompanyDAO companyDao;
	private Company company;
	private List<Company> companies;
	
	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	@Override
	public String execute() throws Exception {
		companies = companyDao.getAll();
		return SUCCESS;
	}

	public String save() throws Exception {
		String result = SUCCESS;
		// Check if the company exits
		Company c = companyDao.getCompany(company.getName());
		if (c != null) {
			//setFieldErrors(errorMap);
			result = ERROR;
		}
		else {
			companyDao.saveCompany(company);
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
