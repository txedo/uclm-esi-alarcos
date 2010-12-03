package persistency.business;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.commons.configuration.ConfigurationException;

import persistency.XMLAgent;

import model.business.knowledge.Address;
import model.business.knowledge.Company;
import model.business.knowledge.CompanyList;
import model.business.knowledge.Factory;
import model.knowledge.Vector2f;

public class CompanyDAO {
	private String xmlfile;
	
	public CompanyDAO () throws ConfigurationException {
		this.xmlfile = XMLAgent.getXMLFilename("companies");
	}
	
	public boolean save (Company c) throws JAXBException, IOException {
		boolean result = true;
//		CompanyList companyList = this.getAll();
//		c.setId(companyList.getLastId());
		CompanyList companyList = new CompanyList();
		
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
		XMLAgent.marshal(this.xmlfile, CompanyList.class, (CompanyList)companyList);
		return result;
	}
	
	public Company get (String companyName) throws Exception {
		Company result = null;
//		XMLAgent.unmarshal(this.xmlfile, Company.class);
		return result;
	}
	
	public CompanyList getAll () throws JAXBException, IOException {
		return (CompanyList)XMLAgent.unmarshal(this.xmlfile, CompanyList.class);
	}
}
