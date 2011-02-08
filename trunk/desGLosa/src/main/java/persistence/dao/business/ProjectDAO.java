package persistence.dao.business;

import java.sql.SQLException;

import persistence.database.ConnectionManager;
import model.business.knowledge.Project;

public class ProjectDAO {

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

}
