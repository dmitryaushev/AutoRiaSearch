package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.user.User;

public interface UserService {

    void registerUser(User user);
    boolean userExist(String email);
}
