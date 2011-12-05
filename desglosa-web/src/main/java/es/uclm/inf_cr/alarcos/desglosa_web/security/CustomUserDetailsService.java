package es.uclm.inf_cr.alarcos.desglosa_web.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import es.uclm.inf_cr.alarcos.desglosa_web.dao.UserDAO;
import es.uclm.inf_cr.alarcos.desglosa_web.model.User;

public class CustomUserDetailsService implements UserDetailsService {
    private UserDAO userDao;

    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = userDao.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("user '" + username
                    + "' not found...");
        } else {
            return (UserDetails) user;
        }
    }
}
