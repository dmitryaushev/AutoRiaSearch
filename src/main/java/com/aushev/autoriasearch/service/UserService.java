package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;

import java.util.List;

public interface UserService {

    void registerUser(User user);

    boolean userExist(String email);

    List<User> findAllUsers();

    List<User> findNotActiveUsers();

    void activateUsers(NotActiveUsers users);
}
