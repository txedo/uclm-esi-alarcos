package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.util.Utilities;

public class CompanyManager {
    private static CompanyDAO companyDao;

    private CompanyManager() {
    }
    
    public static boolean checkCompanyExists(int id) {
        boolean bExists = true;
        try {
            CompanyManager.getCompany(id);
            bExists = true;
        } catch (CompanyNotFoundException e) {
            bExists = false;
        }
        return bExists;
    }
    
    public static boolean checkCompanyExists(String name) {
        boolean bExists = true;
        try {
            CompanyManager.getCompany(name);
            bExists = true;
        } catch (CompanyNotFoundException e) {
            bExists = false;
        }
        return bExists;
    }
    
    public static void saveCompany(Company c) {
        companyDao.saveCompany(c);
    }
    
    public static Company getCompany(int id) throws CompanyNotFoundException {
        return companyDao.getCompany(id);
    }
    
    public static Company getCompany(String name) throws CompanyNotFoundException {
        return companyDao.getCompany(name);
    }
    
    public static List<Company> getAllCompanies() {
        return companyDao.getCompanies();
    }
    
    public static void removeCompany(int id) {
        companyDao.removeCompany(id);
    }
    
    public static void updateMeasures(int companyToUpdateId, Company companyWithUpdatedMeasures) throws CompanyNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, Exception {
        Company companyToUpdate = CompanyManager.getCompany(companyToUpdateId);
        Utilities.updateFieldsByReflection(Company.class, companyToUpdate, companyWithUpdatedMeasures);
        companyToUpdate.setMeasures(companyWithUpdatedMeasures.getMeasures());
        CompanyManager.saveCompany(companyToUpdate);
    }
    
    public void setCompanyDao(CompanyDAO companyDao) {
        CompanyManager.companyDao = companyDao;
    }
    
    
}
