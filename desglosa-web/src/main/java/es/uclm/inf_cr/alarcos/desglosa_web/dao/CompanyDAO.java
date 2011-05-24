package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public interface CompanyDAO extends GenericDAO<Company, Long> {

	public Company getCompany(int id) throws CompanyNotFoundException;
	
	public Company getCompany(String name) throws CompanyNotFoundException;
	
	public List<Company> getCompanies();
	
	public void saveCompany(Company company);
	
	public void removeCompany(int id);
}
