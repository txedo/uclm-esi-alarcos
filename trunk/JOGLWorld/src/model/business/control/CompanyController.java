package model.business.control;

import exceptions.CompanyNotFoundException;
import persistency.business.CompanyDAO;
import model.business.knowledge.Company;

public class CompanyController {
	
	static public void addCompany (String name, String information) throws Exception {
		Company c = new Company (name, information);
		CompanyDAO cdao;
		cdao = new CompanyDAO();
		cdao.save(c);
	}
	
	static public Company getCompany (String name) throws Exception {
		Company result = null;
		CompanyDAO cdao;
		cdao = new CompanyDAO();
		result = cdao.get(name);
		if (result == null) throw new CompanyNotFoundException ();
		return result;
	}
	
	static public void getAllCompanies () {
		
	}
}
