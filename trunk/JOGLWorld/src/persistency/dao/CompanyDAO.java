package persistency.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import persistency.XMLAgent;

import exceptions.CompanyNotFoundException;


import model.business.knowledge.Address;
import model.business.knowledge.Company;
import model.business.knowledge.CompanyWrapper;
import model.business.knowledge.Factory;
import model.knowledge.Vector2f;

public class CompanyDAO {
	private String xmlfile;
	
	public CompanyDAO () throws ConfigurationException {
		this.xmlfile = XMLAgent.getXMLFilename("companies");
	}
	
	public void save (Company c) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		CompanyWrapper companyList = this.getAll();
		c.setId(companyList.getLastId());
		
		Factory f = new Factory();
		f.setId(c.getLastFactoryId());
		f.setName("factoria 1");
		f.setInformation("111111111");
		f.setDirector("txedo 1");
		f.setEmail("txedo@txedo.es 1");
		f.setEmployees(1);
		f.addLocation(0, new Vector2f(5.3f,3.5f));
		f.addLocation(1, new Vector2f(4.1f,1.4f));
		Address a = new Address();
		a.setCity("ciudad real");
		a.setCountry("españa");
		a.setState("ciudad real");
		a.setStreet("lirio");
		a.setZip("13000");
		f.setAddress(a);
		c.addFactory(f);
		
		f = new Factory();
		f.setId(c.getLastFactoryId());
		f.setName("factoria 2");
		f.setInformation("2222222222");
		f.setDirector("txedo 2");
		f.setEmail("txedo@txedo.es 2");
		f.setEmployees(2);
		f.setAddress(a);
		f.addLocation(2, new Vector2f(1.9f,9.1f));
		f.addLocation(3, new Vector2f(9.7f,7.9f));
		c.addFactory(f);
		
		companyList.addCompany(c);
		XMLAgent.marshal(this.xmlfile, CompanyWrapper.class, (CompanyWrapper)companyList);
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
