package persistence.database;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;


/**
 * Interfaz que deben implementar las clases que proporcionen acceso a una
 * base de datos para poder ser utilizadas por el gestor de conexiones de
 * bases de datos.
 */
public interface IDBConnection extends Remote {
	
	public String getIdentifier();
	
	public List<?> select(HibernateQuery hquery) throws RemoteException, SQLException;

	public void beginTransaction() throws RemoteException, SQLException;
	
	public Object insert(Object obj) throws RemoteException, SQLException;

	public void update(Object obj) throws RemoteException, SQLException;

	public void delete(Object obj) throws RemoteException, SQLException;

	public void freeResultset(Object obj) throws RemoteException, SQLException;
	
	public void commit() throws RemoteException, SQLException;
	
	public void rollback() throws RemoteException, SQLException;
		
}
