package es.uclm.inf_cr.alarcos.desglosa_web.dao.hibernate;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.UserDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.User;

public class UserDAOHibernate extends GenericDAOHibernate<User, Long> implements UserDAO {

	public UserDAOHibernate(Class<User> persistentClass) {
		super(persistentClass);
	}

	public User getUser(int id) {
		return (User) getHibernateTemplate().get(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return getHibernateTemplate().find("from User u order by upper(u.username)");
	}

	public void saveUser(User user) {
		getHibernateTemplate().saveOrUpdate(user);
		getHibernateTemplate().flush();
	}

	public void removeUser(int id) {
		Object user = getHibernateTemplate().load(User.class, id);
		getHibernateTemplate().delete(user);
	}


	
}
