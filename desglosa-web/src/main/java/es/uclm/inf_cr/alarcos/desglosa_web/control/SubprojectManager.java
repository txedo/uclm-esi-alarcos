package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.SubprojectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.SubprojectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Subproject;

public class SubprojectManager {
    private static SubprojectDAO subprojectDao;
    
    private SubprojectManager() {
    }
    
    public static boolean checkSubprojectExists(int id) {
        boolean bExists = true;
        try {
            SubprojectManager.getSubproject(id);
            bExists = true;
        } catch (SubprojectNotFoundException e) {
            bExists = false;
        }
        return bExists;
    }

    public static void saveSubproject(Subproject p) {
        subprojectDao.saveSubproject(p);
    }
    
    public static Subproject getSubproject(int id) throws SubprojectNotFoundException {
        return subprojectDao.getSubproject(id);
    }
    
    public static Subproject getSubproject(String name) throws SubprojectNotFoundException {
        return subprojectDao.getSubproject(name);
    }
    
    public static List<Subproject> getAllSubprojects() {
        return subprojectDao.getAll();
    }
    
    public static void removeSubproject(int id) {
        subprojectDao.removeSubproject(id);
    }
    
    public static List<Subproject> getDevelopingSubprojectsByCompanyId(int id) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        return subprojectDao.findByNamedQuery("findSubprojectsByCompanyId", queryParams);
    }

    /**
     * @param subprojectDao the subprojectDao to set
     */
    public void setSubprojectDao(SubprojectDAO subprojectDao) {
        SubprojectManager.subprojectDao = subprojectDao;
    }

}
