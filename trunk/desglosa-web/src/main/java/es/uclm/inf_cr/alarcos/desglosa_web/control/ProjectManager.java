package es.uclm.inf_cr.alarcos.desglosa_web.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;

public class ProjectManager {
    private static ProjectDAO projectDao;
    
    private ProjectManager() {
    }
    
    public static boolean checkProjectExists(int id) {
        boolean bExists = true;
        try {
            ProjectManager.getProject(id);
            bExists = true;
        } catch (ProjectNotFoundException e) {
            bExists = false;
        }
        return bExists;
    }

    public static void saveProject(Project p) {
        projectDao.saveProject(p);
    }
    
    public static Project getProject(int id) throws ProjectNotFoundException {
        return projectDao.getProject(id);
    }
    
    public static Project getProject(String name) throws ProjectNotFoundException {
        return projectDao.getProject(name);
    }
    
    public static List<Project> getAllProjects() {
        return projectDao.getAll();
    }
    
    public static void removeProject(int id) {
        projectDao.removeProject(id);
    }
    
    public static List<Project> getDevelopingProjectsByCompanyId(int id) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        return projectDao.findByNamedQuery("findProjectsByCompanyId", queryParams);
    }
    
    public static List<Project> getDevelopingProjectsByFactoryId(int id) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("id", id);
        return projectDao.findByNamedQuery("findProjectsByFactoryId", queryParams);
    }

    /**
     * @param projectDao the projectDao to set
     */
    public void setProjectDao(ProjectDAO projectDao) {
        ProjectManager.projectDao = projectDao;
    }

}
