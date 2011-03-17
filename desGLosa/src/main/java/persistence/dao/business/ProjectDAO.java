package persistence.dao.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.ProjectNotFoundException;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;
import model.business.knowledge.Project;

public class ProjectDAO {
	private static final String TABLE = "Project";
	private static final String ID_COLUMN = "id";

	public static void insert(Project project) throws SQLException {
		try {
			ConnectionManager.beginTransaction();
			ConnectionManager.insert(project.clone());
		} finally {
			ConnectionManager.endTransaction();
		}
	}
	
	public static Project get(int id) throws SQLException, ProjectNotFoundException {
		HibernateQuery hquery;
		List<?> resultset;
		Project result = null;
		
		hquery = new HibernateQuery("FROM " + TABLE + " WHERE " + ID_COLUMN + " = ?", id);
		resultset = ConnectionManager.query(hquery);
		
		if (resultset.size() != 0) {
			// Clone the read Factory
			result = (Project)((Project)resultset.get(0)).clone();
			ConnectionManager.freeResultset(resultset);
		} else throw new ProjectNotFoundException();
		
		return result;
	}

	public static List<Project> getAll() throws SQLException {
		HibernateQuery hquery;
		List<?> resultset;
		List<Project> result = new ArrayList<Project>();
		
		hquery = new HibernateQuery("FROM " + TABLE);
		resultset = ConnectionManager.query(hquery);
		
		// Clone read factories
		for (Object obj : resultset) {
			result.add((Project) ((Project)obj).clone());
		}
		ConnectionManager.freeResultset(resultset);
	
		return result;
	}

}
