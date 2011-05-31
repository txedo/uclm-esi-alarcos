package persistence.database;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Clase abstracta que representa una consulta o modificaci�n sobre una
 * base de datos.
 */
public class HibernateQuery implements Serializable {

	private static final long serialVersionUID = 8743373028825629890L;

	protected String sentencia;
	protected Object[] parametros;
	
	public HibernateQuery(String sentencia, Object... parametros) {
		this.sentencia = sentencia;
		this.parametros = parametros;
	}

	public String getSentencia() {
		return sentencia;
	}

	public Object[] getParametros() {
		return parametros;
	}
	
	public Query crearQuery(Session sesion) throws HibernateException {
		Query consulta;
		int i;
		
		// Devolvemos una consulta para la
		// sesi�n de Hibernate especificada
		consulta = sesion.createQuery(sentencia);
		for(i = 0; i < parametros.length; i++) {
			consulta.setParameter(i, parametros[i]);
		}
		return consulta;
	}

}
