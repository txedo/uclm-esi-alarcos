package es.uclm.inf_cr.alarcos.desglosa_web.security;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService extends HibernateDaoSupport implements UserDetailsService {
	
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		List users = getHibernateTemplate().find("from User where username=?", username);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }
//public class MyJdbcDaoImpl extends JdbcDaoImpl implements UserDetailsService {

//	@Override
//    protected List loadUsersByUsername(String username) {
//    	List<UserDetails> result = new ArrayList<UserDetails>();
//    	MyUserDAOHibernate dao = new MyUserDAOHibernate(MyUser.class);
//    	UserDetails user = dao.loadUserByUsername(username);
//    	result.add(user);
//    	
//        return result;
//    }
//    
//	@Override
//    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery, 
//            GrantedAuthority[] combinedAuthorities) {
//        String returnUsername = userFromUserQuery.getUsername();
//
//        if (!isUsernameBasedPrimaryKey()) {
//            returnUsername = username;
//        }
//
//        return new MyUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), 
//                true, true, true, combinedAuthorities, ((MyUser)userFromUserQuery).getId());
//    }
    
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
//		MyUserDAOHibernate dao = new MyUserDAOHibernate(MyUser.class);
//		MyUser myUser = (MyUser) dao.loadUserByUsername(username);
//		if (myUser == null)
//			throw new UsernameNotFoundException(
//                    messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"), username);
//		// myUser contains no GrantedAuthority[]
//
//        Set dbAuthsSet = new HashSet();
//
//        if (getEnableAuthorities()) {
//            dbAuthsSet.addAll(loadUserAuthorities(myUser.getUsername()));
//        }
//
//        if (getEnableGroups()) {
//            dbAuthsSet.addAll(loadGroupAuthorities(myUser.getUsername()));
//        }
//
//        List dbAuths = new ArrayList(dbAuthsSet);
//
//        //addCustomAuthorities(myUser.getUsername(), dbAuths);
//
//        if (dbAuths.size() == 0) {
//            throw new UsernameNotFoundException(
//                    messages.getMessage("JdbcDaoImpl.noAuthority",
//                            new Object[] {username}, "User {0} has no GrantedAuthority"), username);
//        }
//
//        GrantedAuthority[] arrayAuths = (GrantedAuthority[]) dbAuths.toArray(new GrantedAuthority[dbAuths.size()]);
//
//        MyUser grantedUser = new MyUser(myUser.getUsername(), myUser.getPassword(), myUser.isEnabled(), myUser.isAccountNonExpired(), myUser.isCredentialsNonExpired(), myUser.isAccountNonLocked(), arrayAuths, myUser.getId());
//        
//        return grantedUser;
//    }
}
