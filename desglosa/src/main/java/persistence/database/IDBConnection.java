package persistence.database;

import java.sql.SQLException;
import java.util.List;


/**
 * Interfaz que deben implementar las clases que proporcionen acceso a una
 * base de datos para poder ser utilizadas por el gestor de conexiones de
 * bases de datos.
 */
public interface IDBConnection {
	
	public String getIdentifier();
	
	public List<?> select(HibernateQuery hquery) throws SQLException;

	public void beginTransaction() throws SQLException;
	
	public Object insert(Object obj) throws SQLException;
	
	public Object merge(Object obj) throws SQLException;

	public void update(Object obj) throws SQLException;

	public void delete(Object obj) throws SQLException;

	public void freeResultset(Object obj) throws SQLException;
	
	public void commit() throws SQLException;
	
	public void rollback() throws SQLException;
		
}
