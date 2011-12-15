package es.uclm.inf_cr.alarcos.desglosa_web.actions;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import es.uclm.inf_cr.alarcos.desglosa_web.control.GenericManager;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;

public class GenericManagerTest extends
AbstractDependencyInjectionSpringContextTests {
    
    @Override
    protected String[] getConfigLocations() {
        return new String[] { "classpath:applicationContext-resources.xml",
                "classpath:applicationContext-dao.xml",
                "classpath:applicationContext.xml" };
    }
    
    public void testIsEmptyString() {
        String nullString = null;
        assertTrue(GenericManager.isEmptyString(nullString));
        
        String emptyStrings[] = { "", " ", "   " };
        for (String emptyString : emptyStrings) {
            assertTrue(GenericManager.isEmptyString(emptyString));
        }
        
        String notEmptyStrings[] = { "a", " a", "a "};
        for (String notEmptyString : notEmptyStrings) {
            assertFalse(GenericManager.isEmptyString(notEmptyString));
        }
    }
    
    public void testCheckValidId() {
        String nullId = null;
        try {
            GenericManager.checkValidId(nullId);
            fail("Null id should have thrown an exception");
        } catch (NullIdParameterException e) {
            // This is expected when ID is null
        } catch (NotValidIdParameterException e) {
        }

        String notNumericIds[] = { "", " ", "a", " 3", "4a", "a4" , "-1", "0" };
        for (String notNumericId : notNumericIds) {
            try {
                GenericManager.checkValidId(notNumericId);
            } catch (NullIdParameterException e) {
            } catch (NotValidIdParameterException e) {
                // This is expected when ID is not numeric or less than 1
            }
        }

    }
}
