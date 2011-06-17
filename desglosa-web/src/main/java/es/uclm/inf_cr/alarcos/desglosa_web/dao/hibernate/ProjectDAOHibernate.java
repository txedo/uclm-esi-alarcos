package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.ProjectDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.exception.ProjectNotFoundException;
import es.uclm.inf_cr.alarcos.desglosa_web.model.Project;

public class ProjectDAOHibernate extends GenericDAOHibernate<Project, Long> implements
		ProjectDAO {

	public ProjectDAOHibernate(Class<Project> persistentClass) {
		super(persistentClass);
	}

	public Project getProject(int id) throws ProjectNotFoundException {
		Project p = (Project) getHibernateTemplate().get(Project.class, id);
		if (p == null) throw new ProjectNotFoundException("project '" + id + "' not found...");
		return p;
	}

	@SuppressWarnings("rawtypes")
	public Project getProject(String name) throws ProjectNotFoundException {
		List projects = getHibernateTemplate().find("from Project where name=?", name);
	    if (projects == null || projects.isEmpty()) {
	    	throw new ProjectNotFoundException("project '" + name + "' not found...");
	    } else {
	    	return (Project) projects.get(0);
	    }
	}

	@SuppressWarnings("unchecked")
	public List<Project> getProjects() {
		return getHibernateTemplate().find("from Project p order by upper(p.name)");
	}

	public void saveProject(Project project) {
		getHibernateTemplate().saveOrUpdate(project);
		getHibernateTemplate().flush();
	}

	public void removeProject(int id) {
		Object project = getHibernateTemplate().load(Project.class, id);
		getHibernateTemplate().delete(project);
	}


}
