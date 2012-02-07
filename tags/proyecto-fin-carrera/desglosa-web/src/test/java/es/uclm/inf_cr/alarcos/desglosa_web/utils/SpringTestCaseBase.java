package es.uclm.inf_cr.alarcos.desglosa_web.utils;


import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class SpringTestCaseBase extends AbstractDependencyInjectionSpringContextTests {
    protected HibernateTemplate hibernateTemplate;
    
    protected String[] getConfigLocations() {
        String contextLocations[] = { "classpath*:applicationContext-dao.xml",
                "classpath*:applicationContext-interceptor.xml",
                "classpath*:applicationContext-resources.xml",
                "classpath*:applicationContext-security.xml",
                "classpath*:applicationContext.xml" };
        return contextLocations;
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onSetUp()
     */
    @Override
    protected void onSetUp() throws Exception {
        hibernateTemplate = (HibernateTemplate) applicationContext.getBean("hibernateTemplate");
        DBUnitUtils.getInstance().setUp();
        super.onSetUp();
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#onTearDown()
     */
    @Override
    protected void onTearDown() throws Exception {
        DBUnitUtils.getInstance().tearDown();
        super.onTearDown();
    }
    
    
}
