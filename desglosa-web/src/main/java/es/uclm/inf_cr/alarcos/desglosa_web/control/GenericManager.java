package es.uclm.inf_cr.alarcos.desglosa_web.control;


import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;

public class GenericManager {
    public static final String ID = "id";
    
    private GenericManager() {
    }
    
    public static boolean isEmptyString(String field) {
        boolean bEmpty = false;
        if (field == null) {
            bEmpty = true;
        } else {
            if (field.trim().length() == 0) {
                bEmpty = true;
            }
        }
        return bEmpty;
    }
    
    public static void checkValidId(int id) throws NotValidIdParameterException {
        // Id must be greater than 0
        if (id <= 0) {
            throw new NotValidIdParameterException();
        }
    }
    
    public static Integer checkValidId(String sId) throws NullIdParameterException, NotValidIdParameterException {
        Integer nId = null;
        
        // Check if id is null
        if (sId == null) {
            throw new NullIdParameterException();
        } else {
            try {
                nId = Integer.parseInt(sId);
                GenericManager.checkValidId(nId);
            } catch (NumberFormatException nfe) {
                throw new NotValidIdParameterException();
            }
        }
        
        return nId;
    }
}
