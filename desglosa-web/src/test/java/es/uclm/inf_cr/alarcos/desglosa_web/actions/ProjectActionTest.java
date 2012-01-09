package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.ProjectManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class ProjectActionTest extends StrutsSpringTestCaseBase {
    private Project project;
    
    public void testExecute() throws Exception {
        configureProxy("/", "listProjects", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(2, ((ProjectAction) action).getProjects().size());
    }

    public void testShowForm() throws Exception {
        // Show a blank form
        configureProxy("/", "showProjectForm", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Factories should be there to be able to choose one
        assertEquals(3, ((ProjectAction) action).getFactories().size());
        
        // Show 
        String sId = "1";
        request.setParameter("id", sId);
        configureProxy("/", "showProjectForm", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Factories should be there to be able to choose one
        assertEquals(3, ((ProjectAction) action).getFactories().size());
        
        project = ((ProjectAction)action).getProject();
        assertEquals(Integer.parseInt(sId), project.getId());
        assertEquals("desglosa", project.getName());
        assertEquals("dgs", project.getCode());
        assertEquals("1112", project.getPlan());
        assertEquals("test factory name 1", project.getMainFactory().getName());
        assertEquals("Defensa", project.getMarket().getName());
    }
    
    public void testShowFormError() throws Exception {
        String sInvalidIds[] = { "-1", "0", "", " ", "a", "3a", " a3", " 3" };
        for (String sId : sInvalidIds) {
            request.setParameter("id", sId);
            configureProxy("/", "showProjectForm", "admin");
            assertTrue(action instanceof ProjectAction);
            assertEquals("Testing invalid id: " + sId, Action.INPUT, proxy.execute());
            assertTrue(((ProjectAction)action).hasActionErrors());
            ((ProjectAction)action).clearActionErrors();
        }
        // Project not found
        String sId = "20";
        request.setParameter("id", sId);
        configureProxy("/", "showProjectForm", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
        assertTrue(((ProjectAction)action).hasActionErrors());
    }
    
    public void testSave() throws Exception {
        // Save project with default director picture
        configureProxy("/", "saveProject", "admin");
        assertTrue(action instanceof ProjectAction);
        project = testUtils.generateRandomProject(FactoryManager.getFactory(3));
        ((ProjectAction)action).setProject(project);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(1, FactoryManager.getFactory(3).getProjects().size());
        assertEquals(3, ProjectManager.getAllProjects().size());
    }
    
    public void testSaveError() throws Exception {
        // Try to save a null project
        configureProxy("/", "saveProject", "admin");
        assertTrue(action instanceof ProjectAction);
        ((ProjectAction)action).setProject(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a empty project name, director name and director last_name
        configureProxy("/", "saveProject", "admin");
        assertTrue(action instanceof ProjectAction);
        project = testUtils.generateRandomProject(FactoryManager.getFactory(3));
        project.setName("");
        ((ProjectAction)action).setProject(project);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testEdit() throws Exception {
        String newName = "changed name";
        String oldName = "desglosa";
        // Edit a project
        configureProxy("/", "editProject", "admin");
        assertTrue(action instanceof ProjectAction);
        // First, retrieve an example project and set it to the action
        project = ProjectManager.getProject(oldName);
        assertEquals(1, project.getId());
        // Change its name to a new one
        project.setName(newName);
        ((ProjectAction)action).setProject(project);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the name has changed
        assertEquals(ProjectManager.getProject(1).getName(), newName);
    }
    
    public void testEditError() throws Exception {
        // Generate and save a random project to edit it
        project = testUtils.generateRandomProject(FactoryManager.getFactory(3));
        assertNotNull("Randomly generated project should not be null");
        ProjectManager.saveProject(project);
        // Now get its id and set it to the memory object
        int realId = ProjectManager.getProject(project.getName()).getId();
        // Try to edit a null project
        configureProxy("/", "editProject", "admin");
        assertTrue(action instanceof ProjectAction);
        ((ProjectAction)action).setProject(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to set a invalid id
        project.setId(-1);
        configureProxy("/", "editProject", "admin");
        assertTrue(action instanceof ProjectAction);
        ((ProjectAction)action).setProject(project);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a project which does not exist
        project.setId(10);
        configureProxy("/", "editProject", "admin");
        assertTrue(action instanceof ProjectAction);
        ((ProjectAction)action).setProject(project);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a project with empty name, director name and director last name
        project.setId(realId);
        project.setName("");
        request.setParameter("id", realId+"");
        configureProxy("/", "editProject", "admin");
        assertTrue(action instanceof ProjectAction);
        ((ProjectAction)action).setProject(project);
        ((ProjectAction)action).setId(realId);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testDelete() throws Exception {
        String sId = "1";
        // Delete a project
        request.setParameter("id", sId);
        configureProxy("/", "deleteProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the project does not exist
        assertFalse(ProjectManager.checkProjectExists(Integer.parseInt(sId)));
        assertEquals(1, ProjectManager.getAllProjects().size());
    }
    
    public void testDeleteError() throws Exception {
        // Try to get a project without setting request id parameter
        configureProxy("/", "deleteProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a invalid id project
        request.setParameter("id", "-1");
        configureProxy("/", "deleteProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a non existing project
        request.setParameter("id", "100");
        configureProxy("/", "deleteProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testGet() throws Exception {
        // View a project
        request.setParameter("id", "1");
        configureProxy("/", "viewProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(2, ProjectManager.getAllProjects().size());
    }
    
    public void testGetError() throws Exception {
        // Try to get a project without setting request id parameter
        configureProxy("/", "viewProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a invalid id project
        request.setParameter("id", "-1");
        configureProxy("/", "viewProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a non existing project
        request.setParameter("id", "100");
        configureProxy("/", "viewProject", "admin");
        assertTrue(action instanceof ProjectAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testUpdateMeasures() throws Exception {
        request.setParameter("id", "1");
        configureProxy("/", "editProjectMeasures", "admin");
        assertTrue(action instanceof ProjectAction);
        project = ProjectManager.getProject(1);
        // update any measure
        boolean isDelay = project.isDelay();
        project.setDelay(!isDelay);
        float eficiencia = project.getEficiencia();
        float foo = 1.0f;
        project.setEficiencia(eficiencia + foo);
        ((ProjectAction) action).setProject(project);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that measures are updated
        project = ProjectManager.getProject(1);
        assertTrue(!isDelay == project.isDelay());
        assertEquals(eficiencia + foo, project.getEficiencia());
    }
}
