package es.uclm.inf_cr.alarcos.desglosa_web.actions;


import com.opensymphony.xwork2.Action;

import es.uclm.inf_cr.alarcos.desglosa_web.control.CompanyManager;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.StrutsSpringTestCaseBase;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class CompanyActionTest extends StrutsSpringTestCaseBase {
    private Company company;
    
    public void testExecute() throws Exception {
        proxy = configureProxy("/", "listCompanies", "admin");
        assertEquals(Action.SUCCESS, proxy.execute());
    }

    public void testShowForm() throws Exception {
        // Show a blank form
        proxy = configureProxy("/", "showCompanyForm", "admin");
        assertEquals(Action.SUCCESS, proxy.execute());
        
        // Show 
        String sId = "1";
        request.setParameter("id", sId);
        proxy = configureProxy("/", "showCompanyForm", "admin");
        assertEquals(Action.SUCCESS, proxy.execute());
        action = (CompanyAction)proxy.getAction();
        
        company = action.getCompany();
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
            proxy = configureProxy("/", "showCompanyForm", "admin");
            assertEquals("Testing invalid id: " + sId, Action.INPUT, proxy.execute());
            action = (CompanyAction)proxy.getAction();
            assertTrue(action.hasActionErrors());
            action.clearActionErrors();
        }
        // Company not found
        String sId = "2";
        request.setParameter("id", sId);
        proxy = configureProxy("/", "showCompanyForm", "admin");
        assertEquals(Action.INPUT, proxy.execute());
        action = (CompanyAction)proxy.getAction();
        assertTrue(action.hasActionErrors());
    }
    
    public void testSave() throws Exception {
        // Save company with default director picture
        proxy = configureProxy("/", "saveCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        company = testUtils.generateRandomCompany();
        action.setCompany(company);
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testSaveError() throws Exception {
        // Save a null company
        proxy = configureProxy("/", "saveCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        company = null;
        action.setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a empty company name, director name and director last_name
        proxy = configureProxy("/", "saveCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        company = testUtils.generateRandomCompany();
        company.setName("");
        company.getDirector().setName("");
        company.getDirector().setLastName("");
        action.setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
        // Save a company with a name already taken
        proxy = configureProxy("/", "saveCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        company = testUtils.generateRandomCompany();
        company.setName("test company name 1");
        action.setCompany(company);
        assertEquals(Action.INPUT, proxy.execute());
    }
    
    public void testEdit() throws Exception {
        String newName = "changed name";
        String oldName = "test company name 1";
        // Edit a company
        proxy = configureProxy("/", "editCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        // First, retrieve an example company and set it to the action
        company = CompanyManager.getCompany(oldName);
        assertEquals(1, company.getId());
        // Change its name to a new one
        company.setName(newName);
        action.setCompany(company);
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the name has changed
        assertTrue(CompanyManager.checkCompanyExists(newName));
        assertFalse(CompanyManager.checkCompanyExists(oldName));
    }
    
    public void testEditError() throws Exception {
        
    }
    
    public void testDelete() throws Exception {
        String sId = "1";
        // Delete a company
        request.setParameter("id", sId);
        proxy = configureProxy("/", "deleteCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        assertEquals(Action.SUCCESS, proxy.execute());
        // Check that the company does not exist
        assertFalse(CompanyManager.checkCompanyExists(Integer.parseInt(sId)));
    }
    
    public void testDeleteError() throws Exception {
        
    }
    
    public void testGet() throws Exception {
        // View a company
        request.setParameter("id", "1");
        proxy = configureProxy("/", "viewCompany", "admin");
        action = (CompanyAction)proxy.getAction();
        assertEquals(Action.SUCCESS, proxy.execute());
    }
    
    public void testGetError() throws Exception {
        
    }
    
}
