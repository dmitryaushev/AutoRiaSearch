package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.config.UserPrincipal;
import com.aushev.autoriasearch.exception.PasswordMatchException;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserRole;
import com.aushev.autoriasearch.model.user.UserStatus;
import com.aushev.autoriasearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void registerUser(User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        user.setUserRole(UserRole.ROLE_USER);
        user.setUserStatus(UserStatus.NOT_ACTIVE);
        userRepository.save(user);
    }

    @Override
    public User userExist(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void updateUser(User user, Authentication authentication) {
        userRepository.save(user);
        saveUserPrincipal(user, authentication);
    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword, Authentication authentication) {
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordMatchException("Wrong password");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        saveUserPrincipal(user, authentication);
    }

    @Override
    public User getUser(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return userPrincipal.getUser();
    }

    private void saveUserPrincipal(User user, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        userPrincipal.setUser(user);
    }
}
