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
            fail("Null id should have thrown NullIdParameterException");
        } catch (NullIdParameterException e) {
            // This is expected when ID is null
        } catch (NotValidIdParameterException e) {
        }

        String notNumericIds[] = { "", " ", "a", "4a", "a4" , "-1", "0", " 3", "3 " };
        for (String notNumericId : notNumericIds) {
            try {
                GenericManager.checkValidId(notNumericId);
                fail("Invalid id should have thrown NotValidIdParameterException");
            } catch (NullIdParameterException e) {
            } catch (NotValidIdParameterException e) {
                // This is expected when ID is not numeric or less than 1
            }
        }
        
        int nInvalidIds[] = { -1, 0 };
        for (int nInvalidId : nInvalidIds) {
            try {
                GenericManager.checkValidId(nInvalidId);
                fail("Invalid id should have thrown NotValidIdParameterException");
            } catch (NotValidIdParameterException e) {
             // This is expected when ID is not numeric or less than 1
            }
        }
        
        String sValidIds[] = { "1", "2", "500" };
        for (String sValidId : sValidIds) {
            try {
                GenericManager.checkValidId(sValidId);
            } catch (NullIdParameterException e) {
                fail("Valid id should have not thrown NullIdParameterException");
            } catch (NotValidIdParameterException e) {
                fail("Valid id should have not thrown NotValidIdParameterException");
            }
        }
        
        int nValidIds[] = {1, 2, 500 };
        for (int nValidId : nValidIds) {
            try {
                GenericManager.checkValidId(nValidId);
            } catch (NotValidIdParameterException e) {
                fail("Valid id should have not thrown NotValidIdParameterException");
            }
        }
    }
}
