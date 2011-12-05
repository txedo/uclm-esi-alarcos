package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public interface CompanyDAO extends GenericDAO<Company, Long> {
    Company getCompany(int id) throws CompanyNotFoundException;

    Company getCompany(String name) throws CompanyNotFoundException;

    List<Company> getCompanies();

    void saveCompany(Company company);

    void removeCompany(int id);
}
