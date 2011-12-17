package es.uclm.inf_cr.alarcos.desglosa_web.dao;


import es.uclm.inf_cr.alarcos.desglosa_web.dao.CompanyDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class CompanyDAOTest extends SpringTestCaseBase {
    // Fixtures
    private Company company;
    private CompanyDAO companyDao;
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        companyDao = (CompanyDAO)applicationContext.getBean("companyDao");
        super.onSetUp();
    }
    
    /* (non-Javadoc)
     * @see es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestSuiteBase#onTearDown()
     */
    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
    }

    public void testGetCompany() {
        assertNotNull(companyDao);
        // by id 
        try {
            company = companyDao.getCompany(1);
            assertNotNull(company);
            assertEquals("Checking name: ", "test company name 1", company.getName());
            assertEquals("Cheking director id: ", 1, company.getDirector().getId());
            assertEquals("Cheking number of factories: ", 2, company.getFactories().size());
        } catch (CompanyNotFoundException e) {
            fail("Company not found by id=1");
        }

        // by name
        String name = "test company name 1";
        try {
            company = companyDao.getCompany(name);
            assertNotNull(company);
            assertEquals("Cheking id: ", 1, company.getId());
            assertEquals("Cheking director id: ", 1, company.getDirector().getId());
            assertEquals("Cheking number of factories: ", 2, company.getFactories().size());
        } catch (CompanyNotFoundException e) {
            fail("Company not found by name=\"" + name + "\"");
        }
    }
    
    public void testGetCompanyError() {
        assertNotNull(companyDao);
        // by id 
        try {
            company = companyDao.getCompany(2);
            fail("Getting company by id=2 should have thrown CompanyNotFoundException");
        } catch (CompanyNotFoundException e) {
            // This is expected
        }
        // by name
        String name = "test company name 2";
        try {
            company = companyDao.getCompany(name);
            fail("Getting company by name=\"" + name + "\" should have thrown CompanyNotFoundException");
        } catch (CompanyNotFoundException e) {
         // This is expected
        }
    }
    
    public void testGetCompanies() {
        assertNotNull(companyDao);
        assertEquals("Checking number of companies: ", 1, companyDao.getCompanies().size());
    }
    
    public void testRemoveCompany() {
        assertNotNull(companyDao);
        companyDao.removeCompany(1);
        // Check that the company nor its associations does not exist anymore
        try {
            companyDao.getCompany(2);
            fail("Company has not been removed successfully");
        } catch (CompanyNotFoundException e) {
            // This is expected
            // Now check that its factories does not exist
            FactoryDAO factoryDao = (FactoryDAO)applicationContext.getBean("factoryDao");
            try {
                factoryDao.getFactory(3);
                fail("The factory id=1 has not been removed with its parent");
            } catch (FactoryNotFoundException e1) {
                // This is expected
                try {
                    factoryDao.getFactory(4);
                    fail("The factory id=2 has not been removed with its parent");
                } catch (FactoryNotFoundException e2) {
                 // This is expected
                }
            }
        }
    }
    
    public void testSaveCompany() {
        assertNotNull(companyDao);
        company = testUtils.generateRandomCompany();
        assertNotNull(company);
        companyDao.saveCompany(company);
        Company companyAux;
        try {
            // Check that the company and its relations has been saved
            companyAux = companyDao.getCompany(company.getId());
            assertEquals("Checking id ", company.getId(), companyAux.getId());
            assertEquals("Checking name ", company.getName(), companyAux.getName());
            assertEquals("Checking number of factories ", company.getFactories().size(), companyAux.getFactories().size());
            assertEquals("Checking director ", company.getDirector().getName(), companyAux.getDirector().getName());
        } catch (CompanyNotFoundException e) {
            fail("The company has not been saved, so it can not be found");
        }
    }
    
}
