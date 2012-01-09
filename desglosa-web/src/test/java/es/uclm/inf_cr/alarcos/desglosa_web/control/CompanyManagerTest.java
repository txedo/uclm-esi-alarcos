package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.lang.reflect.InvocationTargetException;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class CompanyManagerTest extends SpringTestCaseBase {
    // Fixtures
    private Company company;
    
    public void testCheckCompanyExists() {
        // test by id
        assertFalse("Company with id=50 should not exists.", CompanyManager.checkCompanyExists(50));
        assertTrue("Company with id=1 should exists.", CompanyManager.checkCompanyExists(1));
        // test by name
        assertFalse("Company with name=\"FooBarBaz\" should not exists.", CompanyManager.checkCompanyExists("FooBarBaz"));
        assertTrue("Company with name=\"test company name 1\" should exists.", CompanyManager.checkCompanyExists("test company name 1"));
    }
    
    public void testSaveGetAndRemoveCompany() {
        // Generate a random company and save it
        company = testUtils.generateRandomCompany();
        CompanyManager.saveCompany(company);
        try {
            // Check that it exists in database
            Company aux = CompanyManager.getCompany(company.getName());
            assertNotNull(aux);
            // Remove it
            CompanyManager.removeCompany(aux.getId());
            try {
                // Check that it does not exists anymore
                CompanyManager.getCompany(aux.getId());
                fail("Random generated company should not exists in database.");
            } catch (CompanyNotFoundException e) {
                // This is expected
            }
        } catch (CompanyNotFoundException e) {
            fail("Random generated company should exists in database.");
        }
    }
    
    public void testRemoveCompany() {
        CompanyManager.removeCompany(1);
        // Check that Company id=1 not exists
        try {
            CompanyManager.getCompany(1);
            fail("Company id=1 should have been deleted.");
        } catch (CompanyNotFoundException e) {
            // This is expected.
            // Factory id=1 and id=2 should not exists
            try {
                FactoryManager.getFactory(1);
                fail("Factory id=1 should have been deleted along with Company id=1.");
            } catch (FactoryNotFoundException e1) {
                // This is expected.
                try {
                    FactoryManager.getFactory(2);
                    fail("Factory id=2 should have been deleted along with Company id=1.");
                } catch (FactoryNotFoundException e2) {
                    // This is expected.
                }
            }
        }
    }
    
    public void testGetAllCompanies() {
        assertTrue(CompanyManager.getAllCompanies().size() == 2);
    }
    
    public void testUpdateMeasures() {
        int id = 1;
        try {
            company = CompanyManager.getCompany(id);
            assertNotNull(company);
            // Here we should change values to any known base measure, but none exists when implementing this test
            CompanyManager.updateMeasures(id, company);
            // Now we should read the company again and check that its base measures are up to date.
        } catch (CompanyNotFoundException e) {
            fail("Company id=1 should exists in database.");
        } catch (SecurityException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
