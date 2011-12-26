package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class ProjectDAOTest extends SpringTestCaseBase {
    // Fixtures
    private Project project;
    private ProjectDAO projectDao;
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        projectDao = (ProjectDAO)applicationContext.getBean("projectDao");
        super.onSetUp();
    }
    
    /* (non-Javadoc)
     * @see es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestSuiteBase#onTearDown()
     */
    @Override
    protected void onTearDown() throws Exception {
        super.onTearDown();
    }

    public void testGetProject() {
        assertNotNull(projectDao);
        // by id 
        try {
            project = projectDao.getProject(1);
            assertNotNull(project);
            assertEquals("Error checking project name", "desglosa", project.getName());
            assertEquals("Error checking project factory", "test factory name 1", project.getMainFactory().getName());
            assertEquals("Error checking project market", "Defensa", project.getMarket().getName());
            assertEquals("Error checking project subprojects size", 3, project.getSubprojects().size());
        } catch (ProjectNotFoundException e) {
            fail("Project not found by id=1");
        }

        // by name
        String name = "desglosa";
        try {
            project = projectDao.getProject(name);
            assertNotNull(project);
            assertEquals("Error checking project id", 1, project.getId());
            assertEquals("Error checking project factory", "test factory name 1", project.getMainFactory().getName());
            assertEquals("Error checking project market", "Defensa", project.getMarket().getName());
            assertEquals("Error checking project subprojects size", 3, project.getSubprojects().size());
        } catch (ProjectNotFoundException e) {
            fail("Project not found by name=\"" + name + "\"");
        }
    }
    
    public void testGetProjectError() {
        assertNotNull(projectDao);
        // by id 
        try {
            project = projectDao.getProject(15);
            fail("Getting project by id=15 should have thrown ProjectNotFoundException");
        } catch (ProjectNotFoundException e) {
            // This is expected
        }
        // by name
        String name = "adsfasdfsadfdsaf";
        try {
            project = projectDao.getProject(name);
            fail("Getting project by name=\"" + name + "\" should have thrown ProjectNotFoundException");
        } catch (ProjectNotFoundException e) {
         // This is expected
        }
    }
    
    public void testGetProjects() {
        assertNotNull(projectDao);
        assertEquals("Checking number of factories: ", 2, projectDao.getProjects().size());
    }
    
    public void testRemoveProject() {
        assertNotNull(projectDao);
        projectDao.removeProject(1);
        // Check that the company exists and the factory exits
        assertTrue("Company should exist after project deletion", CompanyManager.checkCompanyExists(1));
        assertTrue("Factory should exists after project deletion", FactoryManager.checkFactoryExists(1));
        // Check that market exists
        assertEquals(1, hibernateTemplate.find("from Market where name=?", "Defensa").size());
        // Check that subprojects does not exist anymore
        assertEquals(0, hibernateTemplate.find("from Subproject where name=?", "dgs-graphics-engine").size());
        assertEquals(0, hibernateTemplate.find("from Subproject where name=?", "dgs-webapp").size());
        assertEquals(0, hibernateTemplate.find("from Subproject where name=?", "dgs-final").size());
    }
    
    public void testSaveProject() {
        assertNotNull(projectDao);
        try {
            project = testUtils.generateRandomProject(FactoryManager.getFactory(1));
            assertNotNull(project);
            projectDao.saveProject(project);
            Project projectAux;
            try {
                // Check that the project exits and the factory has one more project
                assertEquals(2, FactoryManager.getFactory(1).getProjects().size());
                projectAux = projectDao.getProject(project.getId());
                assertEquals("Error checking project name", projectAux.getName(), project.getName());
                assertEquals("Error checking project factory", projectAux.getMainFactory().getName(), project.getMainFactory().getName());
                assertEquals("Error checking project market", projectAux.getMarket().getName(), project.getMarket().getName());
                assertEquals("Error checking project subprojects size", projectAux.getSubprojects().size(), project.getSubprojects().size());
            } catch (ProjectNotFoundException e) {
                fail("The project has not been saved, so it can not be found");
            }
        } catch (FactoryNotFoundException e1) {
            fail("The factory id=1 should be present in the database since creation.");
        }

    }
    
}
