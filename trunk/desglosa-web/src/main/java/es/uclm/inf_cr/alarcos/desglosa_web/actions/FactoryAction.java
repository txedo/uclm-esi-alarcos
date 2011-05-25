package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.dao.FactoryDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;

/* Experience shows that chaining should be used with care. If chaining is overused, 
 * an application can turn into "spaghetti code". Actions should be treated as a 
 * Transaction Script, rather than as methods in a Business Facade. Be sure to ask yourself 
 * why you need to chain from one Action to another. Is a navigational issue, or could 
 * the logic in Action2 be pushed back to a support class or business facade so that Action1 
 * can call it too?
 * 
 * Ideally, Action classes should be as short as possible. All the core logic should be pushed 
 * back to a support class or a business facade, so that Actions only call methods. Actions 
 * are best used as adapters, rather than as a class where coding logic is defined.
*/
public class FactoryAction extends ActionSupport {
	// factoryDao is set from applicationContext.xml
	private FactoryDAO factoryDao;
	private CompanyDAO companyDao;
	// List Action needed attributes
	private List<Factory> factories;
	private List<Company> companies;
	
	public void setFactoryDao(FactoryDAO factoryDao) {
		this.factoryDao = factoryDao;
	}
	
	public void setCompanyDao(CompanyDAO companyDao) {
		this.companyDao = companyDao;
	}

	public List<Factory> getFactories() {
		return factories;
	}
	
	public List<Company> getCompanies() {
		return companies;
	}

	@Override
	public String execute() throws Exception {
		factories = factoryDao.getAll();
		return SUCCESS;
	}
	
	public String showForm() throws Exception {
		String result = SUCCESS;
		// Get all the companies
		companies = companyDao.getAll();
		// Get the factory id attribute from URL
		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			// If id <= 0, then ERROR
			if (id <= 0) {
				addActionError(getText("error.factory.id"));
				result = ERROR;
			} else {
				try {
					// Check if the factory id exists
					Factory f = factoryDao.getFactory(id);
					// Set attributes
//					setId(f.getId());
//					setName(f.getName());
//					setInformation(f.getInformation());
				} catch (FactoryNotFoundException e) {
					addActionError(getText("error.factory.id"));
					result = ERROR;
				}
			}
		} // Else, show a blank form
		return result;
	}
}
