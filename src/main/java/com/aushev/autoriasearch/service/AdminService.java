package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.Config;
import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;

import java.util.List;

public interface AdminService {

    List<User> findAllUsers();

    List<User> findNotActiveUsers();

    void activateUsers(NotActiveUsers users);

    void activateUser(int id);

    void deactivateUser(int id);
}
