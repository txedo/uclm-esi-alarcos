package persistence.database;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;


/**
 * Clase intermedia para acceder a la base de datos principal utilizando
 * Hibernate.
 */
public class DBConnection implements IDBConnection {

	private String identifier;
	private String ip;
	private int port;
	private String schema;
	
	public DBConnection(String identifier, String ip, int port, String schema) {
		this.identifier = identifier;
		this.ip = ip;
		this.port = port;
		this.schema = schema;
		updateURL();
	}
	
	public void setIP(String ip) {
		this.ip = ip;
		updateURL();
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setPort(int port) {
		this.port = port;
		updateURL();
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}

	@SuppressWarnings("deprecation")
	public boolean test() {
		Session session;
		boolean ok;
		
		try {
			// Creamos una sesión de Hibernate y vemos si la
			// conexión JDBC creada internamente es válida
			session = HibernateSessionFactory.getSession();
			ok = session.connection().isValid(1000);
		} catch(SQLException ex) {
			ok = false;
		} catch(HibernateException ex) {
			ok = false;
		}
		return ok;
	}
	
	private void updateURL() {
		HibernateSessionFactory.setDatabaseURL("jdbc:mysql://" + ip + ":" + String.valueOf(port) + "/" + this.schema);
	}
	
	// Métodos de acceso a la base de datos
	
	public List<?> select(HibernateQuery hquery) throws SQLException {
		List<?> resultset;
		
		try {
			HibernateSessionFactory.getSession().beginTransaction();
			resultset = hquery.crearQuery(HibernateSessionFactory.getSession()).list();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}

		return resultset;
	}

	public void beginTransaction() throws SQLException {
		try {
			HibernateSessionFactory.getSession().beginTransaction();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public Object insert(Object obj) throws SQLException {
		try {
			HibernateSessionFactory.getSession().save(obj);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		return obj;
	}
	
	public Object merge(Object obj) throws SQLException {
		try {
			obj = HibernateSessionFactory.getSession().merge(obj);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
		return obj;
	}

	public void update(Object obj) throws SQLException {
		try {
			HibernateSessionFactory.getSession().update(obj);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void delete(Object obj) throws SQLException {
		try {
			HibernateSessionFactory.getSession().delete(obj);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void freeResultset(Object obj) throws SQLException {
		try {
			HibernateSessionFactory.getSession().evict(obj);
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void commit() throws SQLException {
		try {
			HibernateSessionFactory.getSession().flush();
			HibernateSessionFactory.getSession().getTransaction().commit();
			HibernateSessionFactory.closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

	public void rollback() throws SQLException {
		try {
			HibernateSessionFactory.getSession().flush();
			HibernateSessionFactory.getSession().getTransaction().rollback();
			HibernateSessionFactory.closeSession();
		} catch(HibernateException ex) {
			throw new SQLException(ex.getLocalizedMessage(), ex);
		}
	}

}
