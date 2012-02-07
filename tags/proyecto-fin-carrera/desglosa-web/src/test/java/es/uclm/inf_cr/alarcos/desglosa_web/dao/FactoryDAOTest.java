package es.uclm.inf_cr.alarcos.desglosa_web.dao;


import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class FactoryDAOTest extends SpringTestCaseBase {
    // Fixtures
    private Factory factory;
    private FactoryDAO factoryDao;
    
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        factoryDao = (FactoryDAO)applicationContext.getBean("factoryDao");
        super.onSetUp();
    }
    
    /* (non-Javadoc)
     * @see es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestSuiteBase#onTearDown()
     */
    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
    }

    public void testGetFactory() {
        assertNotNull(factoryDao);
        // by id 
        try {
            factory = factoryDao.getFactory(1);
            assertNotNull(factory);
            assertEquals("Checking name: ", "test factory name 1", factory.getName());
            assertEquals("Cheking director name: ", "test director name 2", factory.getDirector().getName());
            assertEquals("Cheking number of factories: ", 1, factory.getProjects().size());
        } catch (FactoryNotFoundException e) {
            fail("Factory not found by id=1");
        }

        // by name
        String name = "test factory name 1";
        try {
            factory = factoryDao.getFactory(name);
            assertNotNull(factory);
            assertEquals("Cheking id: ", 1, factory.getId());
            assertEquals("Cheking director name: ", "test director name 2", factory.getDirector().getName());
            assertEquals("Cheking number of projects: ", 1, factory.getProjects().size());
        } catch (FactoryNotFoundException e) {
            fail("Factory not found by name=\"" + name + "\"");
        }
    }
    
    public void testGetFactoryError() {
        assertNotNull(factoryDao);
        // by id 
        try {
            factory = factoryDao.getFactory(5);
            fail("Getting factory by id=5 should have thrown FactoryNotFoundException");
        } catch (FactoryNotFoundException e) {
            // This is expected
        }
        // by name
        String name = "test factory name 5";
        try {
            factory = factoryDao.getFactory(name);
            fail("Getting factory by name=\"" + name + "\" should have thrown FactoryNotFoundException");
        } catch (FactoryNotFoundException e) {
         // This is expected
        }
    }
    
    public void testGetFactories() {
        assertNotNull(factoryDao);
        assertEquals("Checking number of factories: ", 3, factoryDao.getFactories().size());
    }
    
    public void testRemoveFactory() {
        assertNotNull(factoryDao);
        factoryDao.removeFactory(1);
        // Check that the company exists and the other factory exits
        assertTrue("Parent company exits after factory deletion", CompanyManager.checkCompanyExists(1));
        assertTrue("Brother factory exists after factory deletion", FactoryManager.checkFactoryExists(2));
        // Check that director, address, location nor projects does not exist anymore
        try {
            factoryDao.getFactory(1);
            fail("Factory has not been removed successfully");
        } catch (FactoryNotFoundException e) {
            // This is expected
            // Now check that the company exists yet
            assertTrue(CompanyManager.checkCompanyExists(1));
            // Check that all associations are removed
            assertEquals(0, hibernateTemplate.find("from Director where name=?", "test director name 2").size());
            assertEquals(0, hibernateTemplate.find("from Address where id=?", 1).size());
            assertEquals(0, hibernateTemplate.find("from Location where id=?", 1).size());
            assertEquals(0, hibernateTemplate.find("from Project where mainFactory.id=?", 1).size());
            assertEquals(0, hibernateTemplate.find("from Subproject where factory.id=?", 1).size());
        }
    }
    
    public void testSaveFactory() {
        assertNotNull(factoryDao);
        try {
            factory = testUtils.generateRandomFactory(CompanyManager.getCompany(1));
            assertNotNull(factory);
            factoryDao.saveFactory(factory);
            Factory factoryAux;
            try {
                // Check that the company and its relations has been saved
                factoryAux = factoryDao.getFactory(factory.getId());
                assertEquals("Checking id ", factory.getId(), factoryAux.getId());
                assertEquals("Checking name ", factory.getName(), factoryAux.getName());
                assertEquals("Checking number of projects ", factory.getProjects().size(), factoryAux.getProjects().size());
                assertEquals("Checking director ", factory.getDirector().getName(), factoryAux.getDirector().getName());
                assertEquals("Checking address ", factory.getAddress().getAddress(), factoryAux.getAddress().getAddress());
                assertEquals("Checking location ", factory.getLocation().getLatitude(), factoryAux.getLocation().getLatitude());
            } catch (FactoryNotFoundException e) {
                fail("The factory has not been saved, so it can not be found");
            }
        } catch (CompanyNotFoundException e1) {
            fail("The company id=1 should be present in the database since creation.");
        }

    }
    
}
