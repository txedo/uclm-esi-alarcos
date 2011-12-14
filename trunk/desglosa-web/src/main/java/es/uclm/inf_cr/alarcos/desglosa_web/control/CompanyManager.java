package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;

public class CompanyManager {
    private static CompanyDAO companyDao;

    private CompanyManager() {
    }
    
    public static Company checkCompanyExists(int id) throws CompanyNotFoundException {
        // Check if the company id exists
        return CompanyManager.getCompany(id);
    }
    
    public static Company getCompany(int id) throws CompanyNotFoundException {
        return companyDao.getCompany(id);
    }
    
    public static List<Company> getAllCompanies() {
        return companyDao.getAll();
    }
    
    public static void removeCompany(int id) {
        companyDao.removeCompany(id);
    }
    
    public void setCompanyDao(CompanyDAO companyDao) {
        CompanyManager.companyDao = companyDao;
    }
    
    
}
