package es.uclm.inf_cr.alarcos.desglosa_web.actions;


import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class CompanyActionTest extends StrutsSpringTestCaseBase {
    private Company company;
    
    public void testExecute() throws Exception {
        configureProxy("/", "listCompanies", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.SUCCESS, proxy.execute());
    }

    public void testShowForm() throws Exception {
        // Show a blank form
        configureProxy("/", "showCompanyForm", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        
        // Show 
        String sId = "1";
        request.setParameter("id", sId);
        configureProxy("/", "showCompanyForm", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        
        company = ((CompanyAction)action).getCompany();
        assertEquals(Integer.parseInt(sId), company.getId());
        assertEquals("test company name 1", company.getName());
        assertEquals("test company information 1", company.getInformation());
        assertEquals(1, company.getDirector().getId());
        assertEquals("test director name 1", company.getDirector().getName());
        assertEquals("test director last name 1", company.getDirector().getLastName());
        assertEquals("images/anonymous.gif", company.getDirector().getImagePath());
    }
    
    public void testShowFormError() throws Exception {
        String sInvalidIds[] = { "-1", "0", "", " ", "a", "3a", " a3", " 3" };
        for (String sId : sInvalidIds) {
            request.setParameter("id", sId);
            configureProxy("/", "showCompanyForm", "admin");
            assertTrue(action instanceof CompanyAction);
            assertEquals("Testing invalid id: " + sId, Action.INPUT, proxy.execute());
            assertTrue(((CompanyAction)action).hasActionErrors());
            ((CompanyAction)action).clearActionErrors();
        }
        // Company not found
        String sId = "2";
        request.setParameter("id", sId);
        configureProxy("/", "showCompanyForm", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
        assertTrue(((CompanyAction)action).hasActionErrors());
    }
    
    public void testSave() throws Exception {
        // Save company with default director picture
        configureProxy("/", "saveCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        company = testUtils.generateRandomCompany();
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testSaveError() throws Exception {
        // Try to save a null company
        configureProxy("/", "saveCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        ((CompanyAction)action).setCompany(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a empty company name, director name and director last_name
        configureProxy("/", "saveCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        company = testUtils.generateRandomCompany();
        company.setName("");
        company.getDirector().setName("");
        company.getDirector().setLastName("");
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a company with a name already taken
        configureProxy("/", "saveCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        company = testUtils.generateRandomCompany();
        company.setName("test company name 1");
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testEdit() throws Exception {
        String newName = "changed name";
        String oldName = "test company name 1";
        // Edit a company
        configureProxy("/", "editCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        // First, retrieve an example company and set it to the action
        company = CompanyManager.getCompany(oldName);
        assertEquals(1, company.getId());
        // Change its name to a new one
        company.setName(newName);
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the name has changed
        assertTrue(CompanyManager.checkCompanyExists(newName));
        assertFalse(CompanyManager.checkCompanyExists(oldName));
    }
    
    public void testEditError() throws Exception {
        // Generate and save a random company to edit it
        company = testUtils.generateRandomCompany();
        assertNotNull("Randomly generated company should not be null");
        CompanyManager.saveCompany(company);
        // Now get its id and set it to the memory object
        int realId = CompanyManager.getCompany(company.getName()).getId();
        // Try to edit a null company
        configureProxy("/", "editCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        ((CompanyAction)action).setCompany(null);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to set a invalid id
        company.setId(-1);
        configureProxy("/", "editCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a company with same name and different id
        company.setId(realId);
        company.setName("test company name 1");
        configureProxy("/", "editCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a company which does not exist
        company.setId(10);
        configureProxy("/", "editCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        ((CompanyAction)action).setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to edit a company with empty name, director name and director last name
        company.setId(realId);
        company.setName("");
        company.getDirector().setName("");
        company.getDirector().setLastName("");
        request.setParameter("id", realId+"");
        configureProxy("/", "editCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        ((CompanyAction)action).setCompany(company);
        ((CompanyAction)action).setId(realId);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testDelete() throws Exception {
        String sId = "1";
        // Delete a company
        request.setParameter("id", sId);
        configureProxy("/", "deleteCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the company does not exist
        assertFalse(CompanyManager.checkCompanyExists(Integer.parseInt(sId)));
    }
    
    public void testDeleteError() throws Exception {
        // Try to get a company without setting request id parameter
        configureProxy("/", "deleteCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a invalid id company
        request.setParameter("id", "-1");
        configureProxy("/", "deleteCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to delete a non existing company
        request.setParameter("id", "100");
        configureProxy("/", "deleteCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testGet() throws Exception {
        // View a company
        request.setParameter("id", "1");
        configureProxy("/", "viewCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testGetError() throws Exception {
        // Try to get a company without setting request id parameter
        configureProxy("/", "viewCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a invalid id company
        request.setParameter("id", "-1");
        configureProxy("/", "viewCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
        // Try to view a non existing company
        request.setParameter("id", "100");
        configureProxy("/", "viewCompany", "admin");
        assertTrue(action instanceof CompanyAction);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
}
