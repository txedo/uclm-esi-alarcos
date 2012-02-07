package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.control.FactoryManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Factory;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;


public class FactoryActionTest extends StrutsSpringTestCaseBase {
    private Factory factory;
    
    public void testExecute() throws Exception {
        configureProxy("/", "listFactories", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(3, ((FactoryAction) action).getFactories().size());
    }

    public void testShowForm() throws Exception {
        // Show a blank form
        configureProxy("/", "showFactoryForm", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Companies should be there to be able to choose one
        assertEquals(2, ((FactoryAction) action).getCompanies().size());
        
        // Show 
        String sId = "1";
        request.setParameter("id", sId);
        configureProxy("/", "showFactoryForm", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Companies should be there to be able to choose one
        assertEquals(2, ((FactoryAction) action).getCompanies().size());
        
        factory = ((FactoryAction)action).getFactory();
        assertEquals(Integer.parseInt(sId), factory.getId());
        assertEquals("test factory name 1", factory.getName());
        assertEquals("test factory information 1", factory.getInformation());
        assertEquals(2, factory.getDirector().getId());
        assertEquals("test director name 2", factory.getDirector().getName());
        assertEquals("test director last name 2", factory.getDirector().getLastName());
        assertEquals("images/anonymous.gif", factory.getDirector().getImagePath());
    }
    
    public void testShowFormError() throws Exception {
        String sInvalidIds[] = { "-1", "0", "", " ", "a", "3a", " a3", " 3" };
        for (String sId : sInvalidIds) {
            request.setParameter("id", sId);
            configureProxy("/", "showFactoryForm", "admin");
            assertTrue(action instanceof FactoryAction);
            assertEquals("Testing invalid id: " + sId, Action.INPUT, proxy.execute());
            assertTrue(((FactoryAction)action).hasActionErrors());
            ((FactoryAction)action).clearActionErrors();
        }
        // Factory not found
        String sId = "20";
        request.setParameter("id", sId);
        configureProxy("/", "showFactoryForm", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
        assertTrue(((FactoryAction)action).hasActionErrors());
    }
    
    public void testSave() throws Exception {
        // Save factory with default director picture
        configureProxy("/", "saveFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        factory = testUtils.generateRandomFactory(CompanyManager.getCompany(2));
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(2, CompanyManager.getCompany(2).getFactories().size());
        assertEquals(4, FactoryManager.getAllFactories().size());
    }
    
    public void testSaveError() throws Exception {
        // Try to save a null factory
        configureProxy("/", "saveFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        ((FactoryAction)action).setFactory(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a empty factory name, director name and director last_name
        configureProxy("/", "saveFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        factory = testUtils.generateRandomFactory(CompanyManager.getCompany(2));
        factory.setName("");
        factory.getDirector().setName("");
        factory.getDirector().setLastName("");
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a factory with a name already taken
        configureProxy("/", "saveFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        factory = testUtils.generateRandomFactory(CompanyManager.getCompany(2));
        factory.setName("test factory name 1");
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testEdit() throws Exception {
        String newName = "changed name";
        String oldName = "test factory name 1";
        // Edit a factory
        configureProxy("/", "editFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        // First, retrieve an example factory and set it to the action
        factory = FactoryManager.getFactory(oldName);
        assertEquals(1, factory.getId());
        // Change its name to a new one
        factory.setName(newName);
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the name has changed
        assertTrue(FactoryManager.checkFactoryExists(newName));
        assertFalse(FactoryManager.checkFactoryExists(oldName));
    }
    
    public void testEditError() throws Exception {
        // Generate and save a random factory to edit it
        factory = testUtils.generateRandomFactory(CompanyManager.getCompany(2));
        assertNotNull("Randomly generated factory should not be null");
        FactoryManager.saveFactory(factory);
        // Now get its id and set it to the memory object
        int realId = FactoryManager.getFactory(factory.getName()).getId();
        // Try to edit a null factory
        configureProxy("/", "editFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        ((FactoryAction)action).setFactory(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to set a invalid id
        factory.setId(-1);
        configureProxy("/", "editFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a factory with same name and different id
        factory.setId(realId);
        factory.setName("test factory name 1");
        configureProxy("/", "editFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a factory which does not exist
        factory.setId(10);
        configureProxy("/", "editFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        ((FactoryAction)action).setFactory(factory);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a factory with empty name, director name and director last name
        factory.setId(realId);
        factory.setName("");
        factory.getDirector().setName("");
        factory.getDirector().setLastName("");
        request.setParameter("id", realId+"");
        configureProxy("/", "editFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        ((FactoryAction)action).setFactory(factory);
        ((FactoryAction)action).setId(realId);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testDelete() throws Exception {
        String sId = "1";
        // Delete a factory
        request.setParameter("id", sId);
        configureProxy("/", "deleteFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the factory does not exist
        assertFalse(FactoryManager.checkFactoryExists(Integer.parseInt(sId)));
        assertEquals(2, FactoryManager.getAllFactories().size());
    }
    
    public void testDeleteError() throws Exception {
        // Try to get a factory without setting request id parameter
        configureProxy("/", "deleteFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a invalid id factory
        request.setParameter("id", "-1");
        configureProxy("/", "deleteFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a non existing factory
        request.setParameter("id", "100");
        configureProxy("/", "deleteFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testGet() throws Exception {
        // View a factory
        request.setParameter("id", "1");
        configureProxy("/", "viewFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        assertEquals(3, FactoryManager.getAllFactories().size());
    }
    
    public void testGetError() throws Exception {
        // Try to get a factory without setting request id parameter
        configureProxy("/", "viewFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a invalid id factory
        request.setParameter("id", "-1");
        configureProxy("/", "viewFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a non existing factory
        request.setParameter("id", "100");
        configureProxy("/", "viewFactory", "admin");
        assertTrue(action instanceof FactoryAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testUpdateMeasures() throws Exception {
        request.setParameter("id", "1");
        configureProxy("/", "editFactoryMeasures", "admin");
        assertTrue(action instanceof FactoryAction);
        factory = FactoryManager.getFactory(1);
        // Here we should update any base measure, but at the moment Factory does not have any
        ((FactoryAction) action).setFactory(factory);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
}
