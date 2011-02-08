package persistence.dao.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistence.database.ConnectionManager;
import persistence.database.HibernateQuery;
import model.business.knowledge.Project;

public class ProjectDAO {

	private static final String TABLE = "Project";

	public static void insert(Project project) throws SQLException {
		try {
//			ConnectionManager.beginTransaction();
//			ConnectionManager.insert(project.clone());
			ConnectionManager.beginTransaction();
			Project foo = (Project)project.clone();
			ConnectionManager.insert(foo);
			project = (Project)ConnectionManager.merge(foo);
		} finally {
			ConnectionManager.endTransaction();
		}
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
