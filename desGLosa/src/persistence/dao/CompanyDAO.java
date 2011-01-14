package persistence.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import persistence.XMLAgent;

import exceptions.CompanyNotFoundException;


import model.business.knowledge.Company;
import model.business.knowledge.CompanyWrapper;

public class CompanyDAO {
	private String xmlfile;
	
	public CompanyDAO () throws ConfigurationException {
		this.xmlfile = XMLAgent.getXMLFilename("companies");
	}
	
	public void save (Company c) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		CompanyWrapper companyList = this.getAll();
		c.setId(companyList.getLastId());		
		companyList.addCompany(c);
		XMLAgent.marshal(this.xmlfile, CompanyWrapper.class, (CompanyWrapper)companyList);
	}
	
	public void saveAll (List<Company> companies) throws JAXBException {
		CompanyWrapper companyList = new CompanyWrapper();
		companyList.addAllCompanies(companies);
		XMLAgent.marshal(this.xmlfile, CompanyWrapper.class, (CompanyWrapper)companyList);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Company get (int id) throws JAXBException, IOException, CompanyNotFoundException, InstantiationException, IllegalAccessException {
		boolean found = false;
		Company result = null;
		ArrayList<Company> companies = (ArrayList)XMLAgent.unmarshal(this.xmlfile, CompanyWrapper.class).getInnerList();
		Iterator it = companies.iterator();
		while (!found && it.hasNext()) {
			Company aux = (Company)it.next();
			if (aux.getId() == id) {
				result = aux;
				found = true;
			}
		}
		if (result == null) throw new CompanyNotFoundException ();
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Company get (String companyName) throws JAXBException, IOException, CompanyNotFoundException, InstantiationException, IllegalAccessException {
		boolean found = false;
		Company result = null;
		ArrayList<Company> companies = (ArrayList)XMLAgent.unmarshal(this.xmlfile, CompanyWrapper.class).getInnerList();
		Iterator it = companies.iterator();
		while (!found && it.hasNext()) {
			Company aux = (Company)it.next();
			if (aux.getName().equals(companyName)) {
				result = aux;
				found = true;
			}
		}
		if (result == null) throw new CompanyNotFoundException ();
		return result;
	}
	
	public CompanyWrapper getAll () throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		return (CompanyWrapper)XMLAgent.unmarshal(this.xmlfile, CompanyWrapper.class);
	}
}
