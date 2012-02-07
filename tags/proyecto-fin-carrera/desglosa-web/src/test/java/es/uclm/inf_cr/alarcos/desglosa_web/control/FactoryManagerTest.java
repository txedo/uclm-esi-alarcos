package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.lang.reflect.InvocationTargetException;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class FactoryManagerTest extends SpringTestCaseBase {
    // Fixtures
    private Factory factory;
    
    public void testCheckFactoryExists() {
        // test by id
        assertFalse("Factory with id=50 should not exists.", FactoryManager.checkFactoryExists(50));
        assertTrue("Factory with id=1 should exists.", FactoryManager.checkFactoryExists(1));
        // test by name
        assertFalse("Factory with name=\"FooBarBaz\" should not exists.", FactoryManager.checkFactoryExists("FooBarBaz"));
        assertTrue("Factory with name=\"test factory name 1\" should exists.", FactoryManager.checkFactoryExists("test factory name 1"));
    }
    
    public void testSaveGetAndRemoveFactory() {
        try {
            // Generate a random factory for companyId=2 and save it
            factory = testUtils.generateRandomFactory(CompanyManager.getCompany(2));
            assertNotNull(factory);
            FactoryManager.saveFactory(factory);
            // Check that it exists in database
            Factory aux = FactoryManager.getFactory(factory.getName());
            assertNotNull(aux);
            // Remove it
            FactoryManager.removeFactory(aux.getId());
            try {
                // Check that it does not exists anymore
                FactoryManager.getFactory(aux.getId());
                fail("Random generated factory should not exists in database.");
            } catch (FactoryNotFoundException e) {
                // This is expected
            }
        } catch (FactoryNotFoundException e) {
            fail("Random generated factory should exists in database.");
        } catch (CompanyNotFoundException e1) {
            fail("Company with id=2 should exists in database for testing purposes.");
        }
    }
    
    public void testGetAllFactories() {
        assertTrue(FactoryManager.getAllFactories().size() == 3);
    }
    
    public void testRemoveFactory() {
        // Factory id=1 and id=2 belongs to Company id=1
        FactoryManager.removeFactory(1);
        try {
            // Factory id=1 should not exists
            FactoryManager.getFactory(1);
            fail("Factory id=1 should have been deleted.");
        } catch (FactoryNotFoundException e) {
            // This is expected
            try {
                // Check that Company id=1 and Factory id=2 exist
                CompanyManager.getCompany(1);
                FactoryManager.getFactory(2);
            } catch (FactoryNotFoundException e1) {
                fail("Factory id=2 should not have been deleted.");
            } catch (CompanyNotFoundException e2) {
                fail("Company id=1 should not have been deleted.");
            }
        }
    }
    
    public void testGetFactoriesInvolvedInProject() {
        assertEquals(FactoryManager.getFactoriesInvolvedInProject(1).size(), 2);
        assertEquals(FactoryManager.getFactoriesInvolvedInProject(2).size(), 2);
    }
    
    public void testUpdateMeasures() {
        int id = 1;
        try {
            factory = FactoryManager.getFactory(id);
            assertNotNull(factory);
            // Here we should change values to any known base measure, but none exists when implementing this test
            FactoryManager.updateMeasures(id, factory);
            // Now we should read the factory again and check that its base measures are up to date.
        } catch (FactoryNotFoundException e) {
            fail("Factory id=1 should exists in database.");
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
