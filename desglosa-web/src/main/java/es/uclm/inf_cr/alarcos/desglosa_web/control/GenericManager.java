package es.uclm.inf_cr.alarcos.desglosa_web.control;

import javax.servlet.http.HttpServletRequest;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.NotValidIdParameterException;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.NullIdParameterException;

public class GenericManager {
    public static final String ID = "id";
    
    private GenericManager() {
    }
    
    public static Integer checkValidId(HttpServletRequest request) throws NullIdParameterException, NotValidIdParameterException {
        String sId = request.getParameter(ID);
        Integer nId = null;
        
        // Check if id is null
        if (sId == null) {
            throw new NullIdParameterException();
        } else {
            try {
                nId = Integer.parseInt(sId);
                // Id must be greater than 0
                if (nId <= 0) {
                    throw new NotValidIdParameterException();
                }
            } catch (NumberFormatException nfe) {
                throw new NotValidIdParameterException();
            }
        }
        
        return nId;
    }
}
