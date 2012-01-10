package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.lang.reflect.InvocationTargetException;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class ProjectManagerTest extends SpringTestCaseBase {
    // Fixtures
    private Project project;
    
    public void testCheckProjectExists() {
        // test by id
        assertFalse("Project with id=50 should not exists.", ProjectManager.checkProjectExists(50));
        assertTrue("Project with id=1 should exists.", ProjectManager.checkProjectExists(1));
    }
    
    public void testSaveGetAndRemoveProject() {
        try {
            // Generate a random project for factoryId=3 and save it
            project = testUtils.generateRandomProject(FactoryManager.getFactory(3));
            assertNotNull(project);
            ProjectManager.saveProject(project);
            // Check that it exists in database
            Project aux = ProjectManager.getProject(project.getName());
            assertNotNull(aux);
            // Remove it
            ProjectManager.removeProject(aux.getId());
            try {
                // Check that it does not exists anymore
                ProjectManager.getProject(aux.getId());
                fail("Random generated project should not exists in database.");
            } catch (ProjectNotFoundException e) {
                // This is expected
            }
        } catch (ProjectNotFoundException e) {
            fail("Random generated project should exists in database.");
        } catch (FactoryNotFoundException e1) {
            fail("Factory with id=3 should exists in database for testing purposes.");
        }
    }
    
    public void testGetAllProjects() {
        assertTrue(ProjectManager.getAllProjects().size() == 2);
    }
    
    public void testGetDevelopingProjectsByCompanyId() {
        try {
            assertTrue(ProjectManager.getDevelopingProjectsByCompanyId(1).size() == 2);
            assertTrue(ProjectManager.getDevelopingProjectsByCompanyId(2).size() == 0);
        } catch (CompanyNotFoundException e) {
            fail("Company should exists in database");
        }
    }
    
    public void testGetDevelopingProjectsByFactoryId() {
        try {
            assertTrue(ProjectManager.getDevelopingProjectsByFactoryId(1).size() == 2);
            assertTrue(ProjectManager.getDevelopingProjectsByFactoryId(2).size() == 2);
            assertTrue(ProjectManager.getDevelopingProjectsByFactoryId(3).size() == 0);
        } catch (FactoryNotFoundException e) {
            fail("Factory should exists in database");
        }
    }
    
    public void testUpdateMeasures() {
        int id = 1;
        try {
            project = ProjectManager.getProject(id);
            assertNotNull(project);
            // Change value to a known base measure
            boolean oldDelayValue = project.isDelay();
            project.setDelay(!oldDelayValue);
            ProjectManager.updateMeasures(id, project);
            // Check that the measure is updated
            assertTrue(ProjectManager.getProject(id).isDelay() != oldDelayValue);
        } catch (ProjectNotFoundException e) {
            fail("Project id=1 should exists in database.");
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
