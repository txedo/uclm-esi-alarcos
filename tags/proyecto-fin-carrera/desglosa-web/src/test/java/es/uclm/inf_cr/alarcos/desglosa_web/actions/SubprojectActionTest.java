package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.ProjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.SubprojectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class SubprojectActionTest extends StrutsSpringTestCaseBase {
    private Subproject subproject;
    
    public void testExecute() throws Exception {
        configureProxy("/", "listSubprojects", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(5, ((SubprojectAction) action).getSubprojects().size());
    }

    public void testShowForm() throws Exception {
        // Show a blank form
        configureProxy("/", "showSubprojectForm", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Factories and projects should be there to be able to choose one
        assertEquals(3, ((SubprojectAction) action).getFactories().size());
        assertEquals(2, ((SubprojectAction) action).getProjects().size());
        
        // Show 
        String sId = "1";
        request.setParameter("id", sId);
        configureProxy("/", "showSubprojectForm", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Factories and projects should be there to be able to choose one
        assertEquals(3, ((SubprojectAction) action).getFactories().size());
        assertEquals(2, ((SubprojectAction) action).getProjects().size());
        
        subproject = ((SubprojectAction)action).getSubproject();
        assertEquals(Integer.parseInt(sId), subproject.getId());
        assertEquals("dgs-graphics-engine", subproject.getName());
        assertEquals("test factory name 1", subproject.getFactory().getName());
        assertEquals("desglosa", subproject.getProject().getName());
    }
    
    public void testShowFormError() throws Exception {
        String sInvalidIds[] = { "-1", "0", "", " ", "a", "3a", " a3", " 3" };
        for (String sId : sInvalidIds) {
            request.setParameter("id", sId);
            configureProxy("/", "showSubprojectForm", "admin");
            assertTrue(action instanceof SubprojectAction);
            assertEquals("Testing invalid id: " + sId, Action.INPUT, proxy.execute());
            assertTrue(((SubprojectAction)action).hasActionErrors());
            ((SubprojectAction)action).clearActionErrors();
        }
        // Subproject not found
        String sId = "20";
        request.setParameter("id", sId);
        configureProxy("/", "showSubprojectForm", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
        assertTrue(((SubprojectAction)action).hasActionErrors());
    }
    
    public void testSave() throws Exception {
        // Save subproject with default director picture
        configureProxy("/", "saveSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        subproject = testUtils.generateRandomSubproject(FactoryManager.getFactory(3), ProjectManager.getProject(2));
        ((SubprojectAction)action).setSubproject(subproject);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(1, FactoryManager.getFactory(3).getSubprojects().size());
        assertEquals(3, ProjectManager.getProject(2).getSubprojects().size());
        assertEquals(6, SubprojectManager.getAllSubprojects().size());
    }
    
    public void testSaveError() throws Exception {
        // Try to save a null subproject
        configureProxy("/", "saveSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        ((SubprojectAction)action).setSubproject(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a empty subproject name, director name and director last_name
        configureProxy("/", "saveSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        subproject = testUtils.generateRandomSubproject(FactoryManager.getFactory(3), ProjectManager.getProject(2));
        subproject.setName("");
        ((SubprojectAction)action).setSubproject(subproject);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testEdit() throws Exception {
        String newName = "changed name";
        String oldName = "dgs-graphics-engine";
        // Edit a subproject
        configureProxy("/", "editSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        // First, retrieve an example subproject and set it to the action
        subproject = SubprojectManager.getSubproject(oldName);
        assertEquals(1, subproject.getId());
        // Change its name to a new one
        subproject.setName(newName);
        ((SubprojectAction)action).setSubproject(subproject);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the name has changed
        assertEquals(SubprojectManager.getSubproject(1).getName(), newName);
    }
    
    public void testEditError() throws Exception {
        // Generate and save a random subproject to edit it
        subproject = testUtils.generateRandomSubproject(FactoryManager.getFactory(3), ProjectManager.getProject(2));
        assertNotNull("Randomly generated subproject should not be null");
        SubprojectManager.saveSubproject(subproject);
        // Now get its id and set it to the memory object
        int realId = SubprojectManager.getSubproject(subproject.getName()).getId();
        // Try to edit a null subproject
        configureProxy("/", "editSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        ((SubprojectAction)action).setSubproject(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to set a invalid id
        subproject.setId(-1);
        configureProxy("/", "editSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        ((SubprojectAction)action).setSubproject(subproject);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a subproject which does not exist
        subproject.setId(10);
        configureProxy("/", "editSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        ((SubprojectAction)action).setSubproject(subproject);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a subproject with empty name, director name and director last name
        subproject.setId(realId);
        subproject.setName("");
        request.setParameter("id", realId+"");
        configureProxy("/", "editSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        ((SubprojectAction)action).setSubproject(subproject);
        ((SubprojectAction)action).setId(realId);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testDelete() throws Exception {
        String sId = "1";
        // Delete a subproject
        request.setParameter("id", sId);
        configureProxy("/", "deleteSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the subproject does not exist
        assertFalse(SubprojectManager.checkSubprojectExists(Integer.parseInt(sId)));
        assertEquals(4, SubprojectManager.getAllSubprojects().size());
    }
    
    public void testDeleteError() throws Exception {
        // Try to get a subproject without setting request id parameter
        configureProxy("/", "deleteSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a invalid id subproject
        request.setParameter("id", "-1");
        configureProxy("/", "deleteSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a non existing subproject
        request.setParameter("id", "100");
        configureProxy("/", "deleteSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testGet() throws Exception {
        // View a subproject
        request.setParameter("id", "1");
        configureProxy("/", "viewSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(5, SubprojectManager.getAllSubprojects().size());
    }
    
    public void testGetError() throws Exception {
        // Try to get a subproject without setting request id parameter
        configureProxy("/", "viewSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a invalid id subproject
        request.setParameter("id", "-1");
        configureProxy("/", "viewSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a non existing subproject
        request.setParameter("id", "100");
        configureProxy("/", "viewSubproject", "admin");
        assertTrue(action instanceof SubprojectAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testUpdateMeasures() throws Exception {
        request.setParameter("id", "1");
        configureProxy("/", "editSubprojectMeasures", "admin");
        assertTrue(action instanceof SubprojectAction);
        subproject = SubprojectManager.getSubproject(1);
        // update any measure
        float eficiencia = subproject.getEficiencia();
        float foo = 1.0f;
        subproject.setEficiencia(eficiencia + foo);
        ((SubprojectAction) action).setSubproject(subproject);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that measures are updated
        subproject = SubprojectManager.getSubproject(1);
        assertEquals(eficiencia + foo, subproject.getEficiencia());
    }
}
