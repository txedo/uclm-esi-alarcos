package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.lang.reflect.InvocationTargetException;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.CompanyNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.FactoryNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.SpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class SubprojectManagerTest extends SpringTestCaseBase {
    // Fixtures
    private Subproject subproject;
    
    public void testCheckSubprojectExists() {
        // test by id
        assertFalse("Subproject with id=50 should not exists.", SubprojectManager.checkSubprojectExists(50));
        assertTrue("Subproject with id=1 should exists.", SubprojectManager.checkSubprojectExists(1));
    }
    
    public void testSaveGetAndRemoveSubproject() {
        try {
            // Generate a random subproject for factoryId=3 and projectId=1 and save it
            subproject = testUtils.generateRandomSubproject(FactoryManager.getFactory(3), ProjectManager.getProject(1));
            assertNotNull(subproject);
            SubprojectManager.saveSubproject(subproject);
            // Check that it exists in database
            Subproject aux = SubprojectManager.getSubproject(subproject.getName());
            assertNotNull(aux);
            // Remove it
            SubprojectManager.removeSubproject(aux.getId());
            try {
                // Check that it does not exists anymore
                SubprojectManager.getSubproject(aux.getId());
                fail("Random generated subproject should not exists in database.");
            } catch (SubprojectNotFoundException e) {
                // This is expected
                // Check that its factory and its project exists
                assertTrue(FactoryManager.checkFactoryExists(3));
                assertTrue(ProjectManager.checkProjectExists(1));
            }
        } catch (SubprojectNotFoundException e) {
            fail("Random generated subproject should exists in database.");
        } catch (FactoryNotFoundException e1) {
            fail("Factory with id=3 should exists in database for testing purposes.");
        } catch (ProjectNotFoundException e2) {
            fail("Project with id=1 should exists in database for testing purposes.");
        }
    }
    
    public void testGetAllSubprojects() {
        assertTrue(SubprojectManager.getAllSubprojects().size() == 5);
    }
    
    public void testGetDevelopingSubprojectsByCompanyId() {
        try {
            assertTrue(SubprojectManager.getDevelopingSubprojectsByCompanyId(1).size() == 5);
            assertTrue(SubprojectManager.getDevelopingSubprojectsByCompanyId(2).size() == 0);
        } catch (CompanyNotFoundException e) {
            fail("Company should exists in database");
        }
    }
    
    public void testUpdateMeasures() {
        int id = 1;
        try {
            subproject = SubprojectManager.getSubproject(id);
            assertNotNull(subproject);
            // Change value to a known base measure
            float oldActivityValue = subproject.getActividad();
            float foo = 3.0f;
            subproject.setActividad(oldActivityValue + foo);
            SubprojectManager.updateMeasures(id, subproject);
            // Check that the measure is updated
            assertTrue(SubprojectManager.getSubproject(id).getActividad() != oldActivityValue);
            assertTrue(SubprojectManager.getSubproject(id).getActividad() == oldActivityValue + foo);
        } catch (SubprojectNotFoundException e) {
            fail("Subproject id=1 should exists in database.");
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
