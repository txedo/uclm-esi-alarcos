package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import es.uclm.inf_cr.alarcos.desglosa_web.model.User;

public interface UserDAO extends GenericDAO<User, Long> {
	
    //@Transactional
    //UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    
	public User getUser(int id);
	
	public List<User> getUsers();
	
	public void saveUser(User user);
	
	public void removeUser(int id);
}