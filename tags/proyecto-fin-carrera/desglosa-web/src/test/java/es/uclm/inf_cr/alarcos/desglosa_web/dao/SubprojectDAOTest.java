package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.ProjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.SubprojectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class SubprojectDAOTest extends SpringTestCaseBase {
    // Fixtures
    private Subproject subproject;
    private SubprojectDAO subprojectDao;
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        subprojectDao = (SubprojectDAO)applicationContext.getBean("subprojectDao");
        super.onSetUp();
    }
    
    /* (non-Javadoc)
     * @see es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestSuiteBase#onTearDown()
     */
    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
    }

    public void testGetSubproject() {
        assertNotNull(subprojectDao);
        // by id 
        try {
            subproject = subprojectDao.getSubproject(1);
            assertNotNull(subproject);
            assertEquals("Error checking subproject name", "dgs-graphics-engine", subproject.getName());
            assertEquals("Error checking subproject project name", "desglosa", subproject.getProject().getName());
            assertEquals("Error checking subproject factory name", "test factory name 1", subproject.getFactory().getName());
        } catch (SubprojectNotFoundException e) {
            fail("Subproject not found by id=1");
        }

        // by name
        String name = "dgs-graphics-engine";
        try {
            subproject = subprojectDao.getSubproject(name);
            assertNotNull(subproject);
            assertEquals("Error checking subproject name", 1, subproject.getId());
            assertEquals("Error checking subproject project name", "desglosa", subproject.getProject().getName());
            assertEquals("Error checking subproject factory name", "test factory name 1", subproject.getFactory().getName());
        } catch (SubprojectNotFoundException e) {
            fail("Subproject not found by name=\"" + name + "\"");
        }
    }
    
    public void testGetSubprojectError() {
        assertNotNull(subprojectDao);
        // by id 
        try {
            subproject = subprojectDao.getSubproject(15);
            fail("Getting subproject by id=15 should have thrown SubprojectNotFoundException");
        } catch (SubprojectNotFoundException e) {
            // This is expected
        }
        // by name
        String name = "adsfasdfsadfdsaf";
        try {
            subproject = subprojectDao.getSubproject(name);
            fail("Getting subproject by name=\"" + name + "\" should have thrown SubprojectNotFoundException");
        } catch (SubprojectNotFoundException e) {
         // This is expected
        }
    }
    
    public void testGetSubprojects() {
        assertNotNull(subprojectDao);
        assertEquals("Checking number of factories: ", 5, subprojectDao.getSubprojects().size());
    }
    
    public void testRemoveSubproject() {
        assertNotNull(subprojectDao);
        subprojectDao.removeSubproject(1);
        // Check that the company exists, the factory and the project exists yet
        assertTrue("Company should exist after subproject deletion", CompanyManager.checkCompanyExists(1));
        assertTrue("Factory should exist after subproject deletion", FactoryManager.checkFactoryExists(1));
        assertTrue("Project should exist after subproject deletion", ProjectManager.checkProjectExists(1));
        // Check that subproject does not exist anymore but its brothers does
        assertFalse("Subproject should not exist after its deletion", SubprojectManager.checkSubprojectExists(1));
        assertTrue("Subproject should exist after subproject deletion", SubprojectManager.checkSubprojectExists(2));
        assertTrue("Subproject should exist after subproject deletion", SubprojectManager.checkSubprojectExists(3));
    }
    
    public void testSaveSubproject() {
        assertNotNull(subprojectDao);
        try {
            subproject = testUtils.generateRandomSubproject(FactoryManager.getFactory(1), ProjectManager.getProject(1));
            assertNotNull(subproject);
            subprojectDao.saveSubproject(subproject);
            Subproject subprojectAux;
            // Check that the subproject exits and the factory and project have one more subproject
            assertEquals(4, FactoryManager.getFactory(1).getSubprojects().size());
            assertEquals(4, ProjectManager.getProject(1).getSubprojects().size());
            subprojectAux = subprojectDao.getSubproject(subproject.getId());
            assertEquals("Error checking subproject name", subprojectAux.getName(), subproject.getName());
            assertEquals("Error checking subproject factory", subprojectAux.getFactory().getName(), subproject.getFactory().getName());
            assertEquals("Error checking subproject project", subprojectAux.getProject().getName(), subproject.getProject().getName());
        } catch (ProjectNotFoundException e) {
            fail("The project id=1 should be present in the database since creation.");
        } catch (SubprojectNotFoundException e) {
            fail("The subproject has not been found, hence it has not been saved successfully.");
        } catch (FactoryNotFoundException e1) {
            fail("The factory id=1 should be present in the database since creation.");
        }

    }
    
}
