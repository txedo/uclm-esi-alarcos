package model.business.control;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import persistency.dao.CompanyDAO;

import exceptions.CompanyAlreadyExistsException;
import exceptions.CompanyNotFoundException;
import exceptions.EmptyFieldException;
import model.NotifyUIController;
import model.business.knowledge.Company;

public class CompanyController {
	
	static public void addCompany (Company c) throws ConfigurationException, JAXBException, IOException, CompanyAlreadyExistsException, InstantiationException, IllegalAccessException, EmptyFieldException {
		try {
			if (c.getName().equals("")) throw new EmptyFieldException();
			CompanyController.getCompany(c.getName());
			throw new CompanyAlreadyExistsException();
		} catch (CompanyNotFoundException e) {
			CompanyDAO cdao;
			cdao = new CompanyDAO();
			cdao.save(c);
			NotifyUIController.notifyCompanyListUpdate();
		}
	}
	
	static public Company getCompany (String name) throws ConfigurationException, CompanyNotFoundException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		Company result = null;
		CompanyDAO cdao = new CompanyDAO();
		result = cdao.get(name);
		if (result == null) throw new CompanyNotFoundException ();
		return result;
	}
	
	static public List<Company> getAllCompanies () throws ConfigurationException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		CompanyDAO cdao = new CompanyDAO();
		return cdao.getAll().getInnerList();
	}
}
