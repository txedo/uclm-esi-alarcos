package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;

public interface ProjectDAO extends GenericDAO<Project, Long> {
	Project getProject(int id) throws ProjectNotFoundException;
	Project getProject(String name) throws ProjectNotFoundException;
	List<Project> getProjects();
	void saveProject(Project project);
	void removeProject(int id);
}
