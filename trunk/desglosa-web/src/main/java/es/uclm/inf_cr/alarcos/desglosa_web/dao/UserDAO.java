package es.uclm.inf_cr.alarcos.desglosa_web.dao;

import java.util.List;

import es.uclm.inf_cr.alarcos.desglosa_web.model.User;

public interface UserDAO extends GenericDAO<User, Long> {
    User getUser(int id);

    User getUser(String username);

    List<User> getUsers();

    void saveUser(User user);

    void removeUser(int id);
}