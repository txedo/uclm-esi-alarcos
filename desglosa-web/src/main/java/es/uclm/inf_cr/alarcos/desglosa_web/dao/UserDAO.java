package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.User;

public interface UserDAO extends GenericDAO<User, Long> {
    
	public User getUser(int id);
	
	public User getUser(String username);
	
	public List<User> getUsers();
	
	public void saveUser(User user);
	
	public void removeUser(int id);
}