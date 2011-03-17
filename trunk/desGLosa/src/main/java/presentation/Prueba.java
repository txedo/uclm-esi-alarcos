package presentation;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import persistence.ResourceRetriever;
import persistence.database.ConnectionManager;
import persistence.database.DBConnection;
import persistence.database.HibernateUtil;
import persistence.database.IDBConnection;

import model.business.knowledge.Address;
import model.business.knowledge.Company;
import model.business.knowledge.Director;
import model.business.knowledge.Factory;
import model.business.knowledge.Image;
import model.business.knowledge.Location;
import model.business.knowledge.Map;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		// Company
//		session.beginTransaction();
//		List<?> list = session.createQuery("FROM Company WHERE id=1").list();
//		Company c = (Company) ((Company)list.get(0)).clone();
//		session.flush();
//		session.getTransaction().commit();
//		// Factory
//		Address a = new Address("street", "city", "state", "country", "zip");
//		Director d = new Director("mario", "piattini");
//		Factory f = new Factory("uclm9999", "uclm info", d, "piattini@imei.com", 1, a);
//		f.setCompany(c);
//		session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		//session.save(a);
//		//session.save(d);
//		System.out.println(f.getDirector().toString());
//		session.save(f);
//		session.getTransaction().commit();
		
		
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		// Company
//		Company c = new Company("Indra", "information about Indra");
//		Address a = new Address("street", "city", "state", "country", "zip");
//		Director d = new Director("mario", "piattini");
//		session.beginTransaction();
//		// Factory
//		Factory f = new Factory("uclm9999", "uclm info", d, "piattini@imei.com", 1, a);
//		f.setCompany(c);
//		session.save(f);
//		session.getTransaction().commit();
		
		
//		Company c = new Company("mega", "mega info");
//		Address a = new Address("street", "city", "state", "country", "zip");
//		Director d = new Director("mario", "piattini");
//		Factory f = new Factory("uclm9999", "uclm info", d, "piattini@imei.com", 1, a);
//		f.setCompany(c);
//		Map m1 = new Map(1, "prueba9999", "hashcode");
//		Location l1 = new Location(f, m1, 0.9999f, 0.9999f);
//		Map m2 = new Map(1, "prueba333333333", "hashcode");
//		Location l2 = new Location(f, m2, 9.0f, 9.0f);
//		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		session.save(c);
//		session.save(a);
//		session.save(d);
//		session.save(m1);
//		session.save(m2);
//		session.save(f);
//		session.save(l1);
//		session.save(l2);
//		session.getTransaction().commit();
//		
//		session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		List<?> list = session.createQuery("FROM Factory WHERE name='uclm9999'").list();
//		System.out.println(list.get(0));
//		session.getTransaction().commit();
//		
//		session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		List<?> list = session.createQuery("FROM Factory WHERE company.name='Indra'").list();
//		for (int i = 0; i < list.size(); i++) {
//			System.out.print(list.get(i));
//		}
//		session.getTransaction().commit();
		
		// Create a new database connection
		IDBConnection idb = new DBConnection("mysql server", "127.0.0.1", 3306, "desglosadb");
		if (!((DBConnection)idb).test()) {
			System.err.println("Cannot connect to the database server.");
		}
		// Add it to the ConnectionManager
		ConnectionManager.addConnection(idb);
		
		Image i = new Image();
		i.setFilename("foo");
		i.setContentType("image/jpg");
		try {
			i.setData(ResourceRetriever.getResourceAsByteArray("foo"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(i.getData().toString());
//		session.beginTransaction();
//		session.save(i);
//		session.getTransaction().commit();
	}

}
