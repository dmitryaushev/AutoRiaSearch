package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.user.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    void registerUser(User user);

    User userExist(String email);

    void updateUser(User user, Authentication authentication);

    void changePassword(User user, String oldPassword, String newPassword, Authentication authentication);

    User getUser(Authentication authentication);
}
