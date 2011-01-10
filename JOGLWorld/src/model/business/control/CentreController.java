package model.business.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import exceptions.CompanyNotFoundException;

import model.business.knowledge.Centre;
import model.business.knowledge.Company;
import model.business.knowledge.Factory;
import model.knowledge.Vector2f;

public class CentreController {
	
	public static HashMap<Integer,Vector2f> getCentreLocations (Centre centre) throws ConfigurationException, CompanyNotFoundException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		HashMap<Integer,Vector2f> res = new HashMap<Integer,Vector2f>();
		
		boolean found = false;
		Company c = CompanyController.getCompany(centre.getIdCompany());
		for (int i = 0; i < c.getFactories().size() && !found; i++) {
			Factory aux = c.getFactories().get(i);
			if (aux.getId() == centre.getIdFactory()) {
				res = (HashMap<Integer, Vector2f>) aux.getLocations();
				found = true;
			}
		}
		return res;
	}
	
	public static HashMap<Integer,Vector2f> getAllCentreLocations () throws ConfigurationException, JAXBException, IOException, InstantiationException, IllegalAccessException {
		HashMap<Integer,Vector2f> res = new HashMap<Integer,Vector2f>();
		
		List<Company> companies = CompanyController.getAllCompanies();
		for (Company company : companies) {
			for (int i = 0; i < company.getFactories().size(); i++) {
				res = (HashMap<Integer, Vector2f>) company.getFactories().get(i).getLocations();
			}
		}

		return res;
	}
}
