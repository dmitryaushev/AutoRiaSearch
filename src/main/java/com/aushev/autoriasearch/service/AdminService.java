package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    User findUser(String email);

    Page<User> findAllUsers(Pageable pageable);

    List<User> findNotActiveUsers();

    void activateUsers(NotActiveUsers users);

    void activateUser(int id);

    void deactivateUser(int id);

    void switchRole(int id);
}
