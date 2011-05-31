package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public class CompanyAction extends ActionSupport implements GenericActionInterface {
	private int id;
	// companyDao is set from applicationContext.xml
	private CompanyDAO companyDao;
	// Attributes required by Save, Delete and Edit action methods
	private Company company;
	// Attributes required by List action (default method execute)
	private List<Company> companies;
	
	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String showForm() throws Exception {
		// validateDoShowForm is not necessary since it would do the same as this method
		String result = SUCCESS;
		// Get the company id attribute from URL
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
			// If id <= 0, then ERROR
			if (id <= 0) {
				addActionError(getText("error.company.id"));
			} else {
				try {
					// Check if the company id exists and place it in value stack
					company = companyDao.getCompany(id);
				} catch (CompanyNotFoundException e) {
					addActionError(getText("error.company.id"));
				}
			}
		} // Else, show a blank form
		if (hasActionErrors()) {
			result = ERROR;
			companies = companyDao.getAll();
		}
		return result;
	}
	
	public void validateDoSave(){
		if (company != null) {
			// Check company name is not empty
			if (company.getName().trim().length() == 0) addFieldError("company.name", getText("error.company.name"));
			// Check company name is not already taken
			try {
				companyDao.getCompany(company.getName());
				addFieldError("company.name", getText("error.company.already_exists"));
			} catch (CompanyNotFoundException e) {
				// Name not taken. Nothing to do here
			}
		} else {
			addActionError(getText("error.general"));
		}
	}

	public String save() {
		companyDao.saveCompany(company);
		addActionMessage(getText("message.company.added_successfully"));

		return SUCCESS;
	}
	
	public void validateDoEdit () {
		Company cAux;
		if (company != null) {
			// Check if company id is valid
			if (company.getId() <= 0) addActionError(getText("error.company.id"));
			try {
				cAux = companyDao.getCompany(company.getId());
			} catch (CompanyNotFoundException e1) {
				addActionError(getText("error.company.id"));
			}
			// Check company name is not empty
			if (company.getName().trim().length() == 0) addFieldError("company.name", getText("error.company.name"));
			// Check that there is no company with same name and different id
			try {
				cAux = companyDao.getCompany(company.getName());
				if (cAux.getId() != company.getId()) addFieldError("company.name", getText("error.company.already_exists"));
			} catch (CompanyNotFoundException e) {
				// Name not taken. Nothing to do here.
			}
		} else {
			addActionError(getText("error.general"));
		}
	}

	public String edit() throws Exception {
		companyDao.saveCompany(company);
		addActionMessage(getText("message.company.updated_successfully"));

		return SUCCESS;
	}
	
	public void validateDoDelete() {
		// Get the company id attribute from URL
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getParameter("id") != null) {
			id = Integer.parseInt(request.getParameter("id"));
			// If id <= 0, then ERROR
			if (id <= 0) {
				addActionError(getText("error.company.id"));
			} else {
				try {
					// Check if the company id exists
					companyDao.getCompany(id);
				} catch (CompanyNotFoundException e) {
					addActionError(getText("error.company.id"));
				}
			}
		} else {
			addActionError(getText("error.company.id"));
		}
		if (hasActionErrors()) companies = companyDao.getAll();
	}

	public String delete() {
		companyDao.removeCompany(id);
		addActionMessage(getText("message.company.deleted_successfully"));

		return SUCCESS;
	}

	public String get() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
