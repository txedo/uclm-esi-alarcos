package es.uclm.inf_cr.alarcos.desglosa_web.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsSpringTestCase;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

import es.uclm.inf_cr.alarcos.desglosa_web.actions.CompanyAction;

public class StrutsSpringTestCaseBase extends StrutsSpringTestCase {
    protected ActionProxy proxy;
    protected CompanyAction action;
    protected ActionContext actionContext;
    
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
    
    protected ActionProxy configureProxy(String namespace, String actionName, String sessionUsername) {
        proxy = getActionProxy(namespace + actionName);
        assertNotNull(proxy);
        action = (CompanyAction)proxy.getAction();
        assertNotNull(action);
        
        Map<String, Object> session = new HashMap<String, Object>();  
        request.setParameter("username", sessionUsername);  
        actionContext = proxy.getInvocation().getInvocationContext();  
        actionContext.setSession(session);
        
        return proxy;
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.StrutsTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        DBUnitUtils.getInstance().setUp();
        super.setUp();
    }

    /* (non-Javadoc)
     * @see org.apache.struts2.StrutsTestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        DBUnitUtils.getInstance().tearDown();
        super.tearDown();
    }

}
