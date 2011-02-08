package persistence.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor que permite acceder y modificar de forma sincronizada varias
 * bases de datos (locales o remotas).
 */
public class ConnectionManager {

	private static ArrayList<IDBConnection> connection = new ArrayList<IDBConnection>();
	
	public static void addConnection(IDBConnection conn) {
		if(!connection.contains(conn)) {
			connection.add(conn);
		}
	}

	public static void removeAll() {
		connection.clear();
	}
	
	public static List<?> query(HibernateQuery hquery) throws SQLException {
		List<?> resultset;
		
		// Para hacer una consulta utilizamos sólo la primera conexión
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		try {
			resultset = connection.get(0).select(hquery);
		} catch(Exception ex) {
			throw new SQLException("Error en el acceso al " + connection.get(0).getIdentifier() + ".", ex);
		}
		
		return resultset;
	}
	
	public static void beginTransaction() throws SQLException {
		// Iniciamos una transacción que puede estar formada
		// por más de una operación sobre la base de datos
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection conn : connection) {
			try {
				conn.beginTransaction();
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso al " + conn.getIdentifier() + ".", ex);
			}
		}
	}
	
	public static void endTransaction() throws SQLException {
		SQLException exception;
		boolean error;
		
		// Intentamos finalizar la última transacción iniciada
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		error = false;
		exception = null;
		for(IDBConnection conexion : connection) {
			try {
				conexion.commit();
			} catch(Exception ex) {
				try {
					conexion.rollback();
				} catch(Exception ex2) {
				}
				error = true;
				exception = new SQLException("Error en el acceso al " + conexion.getIdentifier() + ".", ex);
			}
		}
		if(error) {
			throw exception;
		}
	}
	
	public static Object insert(Object obj) throws SQLException {
		Object copy;
		
		// Insertamos el objeto en todas las conexiones, y nos quedamos
		// con la copia devuelta por la primera conexión
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		copy = null;
		for(IDBConnection conn : connection) {
			try {
				if(copy == null) {
					copy = conn.insert(obj);
				} else {
					conn.insert(obj);
				}
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso al " + conn.getIdentifier() + ".", ex);
			}
		}
		
		return copy;
	}
	
	public static Object merge(Object obj) throws SQLException {
		Object copy;
		
		// Insertamos el objeto en todas las conexiones, y nos quedamos
		// con la copia devuelta por la primera conexión
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		copy = null;
		for(IDBConnection conn : connection) {
			try {
				if(copy == null) {
					copy = conn.merge(obj);
				} else {
					conn.merge(obj);
				}
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso al " + conn.getIdentifier() + ".", ex);
			}
		}
		
		return copy;
	}
	
	public static void update(Object obj) throws SQLException {
		// Actualizamos el objeto en todas las conexiones
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection conn : connection) {
			try {
				conn.update(obj);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso al " + conn.getIdentifier() + ".", ex);
			}
		}
	}
	
	public static void delete(Object obj) throws SQLException {
		// Eliminamos el objeto en todas las conexiones
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection conn : connection) {
			try {
				conn.delete(obj);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso al " + conn.getIdentifier() + ".", ex);
			}
		}
	}

	public static void freeResultset(Object resultset) throws SQLException {
		// Borramos el objeto de la caché de todas las conexiones
		if(connection.size() == 0) {
			throw new SQLException("La lista de conexiones está vacía.");
		}
		for(IDBConnection conn : connection) {
			try {
				// Borramos el objeto
				conn.freeResultset(resultset);
			} catch(Exception ex) {
				throw new SQLException("Error en el acceso al " + conn.getIdentifier() + ".", ex);
			}
		}
	}
	
	public static void freeResultset(List<?> resultset) throws SQLException {
		// Borramos los objetos
		for (Object obj : resultset) {
			ConnectionManager.freeResultset(obj);
		}
	}
	
}
