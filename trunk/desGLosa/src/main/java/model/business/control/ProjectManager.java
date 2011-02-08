package model.business.control;

import java.sql.SQLException;
import java.util.List;

import persistence.dao.business.ProjectDAO;
import exceptions.FactoryNotFoundException;
import exceptions.MandatoryFieldException;
import exceptions.WorkingFactoryIsNotInvolvedFactoryException;
import model.business.knowledge.Factory;
import model.business.knowledge.Project;


public class ProjectManager {

	public static boolean addProject(Project project) throws MandatoryFieldException, WorkingFactoryIsNotInvolvedFactoryException, SQLException, FactoryNotFoundException {
		boolean success = false;
		if (project.getWorkingFactory() == null) throw new MandatoryFieldException();
		if (project.getInvolvedFactories().size() < 1) throw new MandatoryFieldException();
		// Working factory must appear in Involved Factories Set
		if (!project.getInvolvedFactories().contains((Factory)project.getWorkingFactory())) throw new WorkingFactoryIsNotInvolvedFactoryException();
		// Insert the project
		ProjectDAO.insert(project);
		success = true;
		return success;
	}

	public static List<Project> getAllProjects() throws SQLException {
		return ProjectDAO.getAll();
	}
	
}
