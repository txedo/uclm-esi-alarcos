package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public class CompanyAction extends ActionSupport implements GenericActionInterface {
	// companyDao is set from applicationContext.xml
	private CompanyDAO companyDao;
	// Save Action needed attributes
	private String name;
	private String information;
	// List Action needed attributes
	private List<Company> companies;
	// Delete and Edit Action needed attributes
	private int id;
	
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		companies = companyDao.getAll();
		return SUCCESS;
	}
	
	public String showForm() throws Exception {
		String result = SUCCESS;
		// Get the company id attribute from URL
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			// If id <= 0, then ERROR
			if (id <= 0) {
				addActionError(getText("error.company.id"));
				result = ERROR;
			} else {
				try {
					// Check if the company id exists
					Company c = companyDao.getCompany(id);
					// Set attributes
					setId(c.getId());
					setName(c.getName());
					setInformation(c.getInformation());
				} catch (CompanyNotFoundException e) {
					addActionError(getText("error.company.id"));
					result = ERROR;
				}
			}
		} // Else, show a blank form
		return result;
	}
	
	public void validateDoSave(){
		if (getName().trim().length() == 0) addFieldError("name", getText("error.company.name"));
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
			addActionMessage(getText("message.company.added_successfully"));
		}
		return result;
	}

	public String edit() throws Exception {
		String result = SUCCESS;
		// If id <= 0, then ERROR
		if (getId() <= 0) {
			addActionError(getText("error.company.id"));
			result = ERROR;
		} else {
			try {
				// Check if the company id exists
				Company newCompany = companyDao.getCompany(getId());
				// Update it if it exists
				newCompany.setId(getId());
				newCompany.setName(getName());
				newCompany.setInformation(getInformation());
				companyDao.saveCompany(newCompany);
				addActionMessage(getText("message.company.updated_successfully"));
			} catch (CompanyNotFoundException e) {
				addActionError(getText("error.company.id"));
				result = ERROR;
			}
		}
		return result;
	}

	public String delete() {
		String result = SUCCESS;
		// Get the company id attribute from URL
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			// If id <= 0, then ERROR
			if (id <= 0) {
				addActionError(getText("error.company.id"));
				result = ERROR;
			} else {
				try {
					// Check if the company id exists
					companyDao.getCompany(id);
					// Remove it if it exists
					companyDao.removeCompany(id);
					addActionMessage(getText("message.company.deleted_successfully"));
				} catch (CompanyNotFoundException e) {
					addActionError(getText("error.company.id"));
					result = ERROR;
				}
			}
		} else {
			addActionError(getText("error.company.id"));
			result = ERROR;
		}
		return result;
	}

	public String get() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
