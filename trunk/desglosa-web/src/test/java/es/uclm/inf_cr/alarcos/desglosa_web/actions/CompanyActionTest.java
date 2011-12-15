package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.opensymphony.xwork2.Action;

public class CompanyActionTest extends
        AbstractDependencyInjectionSpringContextTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:applicationContext-resources.xml",
                "classpath:applicationContext-dao.xml",
                "classpath:applicationContext.xml" };
    }
    
    public void testShowCompany() {
        CompanyAction action = new CompanyAction();
        
    }

    public void testListCompaniesAction() {
        CompanyAction action = new CompanyAction();
        //action.setCompanyDao((CompanyDAO) applicationContext.getBean("companyDao"));
        String result = action.execute();
        
        assertEquals(Action.SUCCESS, result);
    }

}
