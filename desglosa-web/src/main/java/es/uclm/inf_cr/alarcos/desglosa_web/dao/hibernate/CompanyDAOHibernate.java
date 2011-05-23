package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import org.springframework.security.userdetails.UsernameNotFoundException;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public class CompanyDAOHibernate extends GenericDAOHibernate<Company, Long> implements
		CompanyDAO {

	public CompanyDAOHibernate(Class<Company> persistentClass) {
		super(persistentClass);
	}

	public Company getCompany(int id) {
		return (Company) getHibernateTemplate().get(Company.class, id);
	}
	
	@SuppressWarnings("rawtypes")
	public Company getCompany(String name) throws CompanyNotFoundException {
		List companies = getHibernateTemplate().find("from Company where name=?", name);
	    if (companies == null || companies.isEmpty()) {
	    	throw new CompanyNotFoundException("company '" + name + "' not found...");
	    } else {
	    	return (Company) companies.get(0);
	    }
	}

	@SuppressWarnings("unchecked")
	public List<Company> getCompanies() {
		return getHibernateTemplate().find("from Company c order by upper(c.name)");
	}

	public void saveCompany(Company company) {
		getHibernateTemplate().saveOrUpdate(company);
		getHibernateTemplate().flush();
	}

	public void removeCompany(int id) {
		Object company = getHibernateTemplate().load(Company.class, id);
		getHibernateTemplate().delete(company);
	}

}
