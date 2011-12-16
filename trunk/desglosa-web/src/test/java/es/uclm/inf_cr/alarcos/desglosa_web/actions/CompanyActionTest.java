package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

import es.uclm.inf_cr.alarcos.desglosa_web.model.Company;
import es.uclm.inf_cr.alarcos.desglosa_web.utils.testUtils;

public class CompanyActionTest extends StrutsSpringTestCase {

    
    /* (non-Javadoc)
     * @see org.apache.struts2.StrutsSpringTestCase#getContextLocations()
     */
    @Override
    protected String[] getContextLocations() {
        String contextLocations[] = { "classpath*:applicationContext-dao.xml",
                "classpath*:applicationContext-interceptor.xml",
                "classpath*:applicationContext-resources.xml",
                "classpath*:applicationContext-security.xml",
                "classpath*:applicationContext.xml" };
        return contextLocations;
    }

    private ActionProxy configureProxy(String namespace, String actionName, String sessionUsername) {
        ActionProxy proxy = getActionProxy(namespace + actionName);
        assertNotNull(proxy);
        CompanyAction action = (CompanyAction)proxy.getAction();
        assertNotNull(action);
        
        Map<String, Object> session = new HashMap<String, Object>();  
        request.setParameter("username", sessionUsername);  
        ActionContext actionContext = proxy.getInvocation().getInvocationContext();  
        actionContext.setSession(session);
        
        return proxy;
    }

    public void testShowForm() throws Exception {
        String sId = "1";
        request.setParameter("id", sId);
        ActionProxy proxy = configureProxy("/", "showCompanyForm", "admin");
        assertEquals(Action.SUCCESS, proxy.execute());
        CompanyAction action = (CompanyAction)proxy.getAction();
        
        Company c = action.getCompany();
        assertEquals(Integer.parseInt(sId), c.getId());
        assertEquals("test company name 1", c.getName());
        assertEquals("test company information 1", c.getInformation());
        assertEquals(1, c.getDirector().getId());
        assertEquals("test director name 1", c.getDirector().getName());
        assertEquals("test director last name 1", c.getDirector().getLastName());
        assertEquals("images/anonymous.gif", c.getDirector().getImagePath());
    }
    
    public void testShowFormInvalidId() throws Exception {
        String sInvalidIds[] = { "-1", "0", "", " ", "a", "3a", " a3", " 3" };
        for (String sId : sInvalidIds) {
            request.setParameter("id", sId);
            ActionProxy proxy = configureProxy("/", "showCompanyForm", "admin");
            assertEquals(Action.ERROR, proxy.execute());
            CompanyAction action = (CompanyAction)proxy.getAction();
            assertTrue(action.hasActionErrors());
            action.clearActionErrors();
        }
        // Company not found
        String sId = "2";
        request.setParameter("id", sId);
        ActionProxy proxy = configureProxy("/", "showCompanyForm", "admin");
        assertEquals(Action.ERROR, proxy.execute());
        CompanyAction action = (CompanyAction)proxy.getAction();
        assertTrue(action.hasActionErrors());
    }
    
    public void testSave() throws Exception {
        ActionProxy proxy = configureProxy("/", "saveCompany", "admin");
        CompanyAction action = (CompanyAction)proxy.getAction();
        Company c = testUtils.generateRandomCompany();
        action.setCompany(c);
        assertEquals(Action.SUCCESS, proxy.execute());
    }

    public void testExecute() throws Exception {
        ActionProxy proxy = configureProxy("/", "listCompanies", "admin");
        assertEquals(Action.SUCCESS, proxy.execute());
    }

}
