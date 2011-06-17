package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;

public interface ProjectDAO extends GenericDAO<Project, Long> {

	public Project getProject(int id) throws ProjectNotFoundException;
	
	public Project getProject(String name) throws ProjectNotFoundException;
	
	public List<Project> getProjects();
	
	public void saveProject(Project project);
	
	public void removeProject(int id);
}
