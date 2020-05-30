package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserRole;
import com.aushev.autoriasearch.model.user.UserStatus;
import com.aushev.autoriasearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public boolean userExist(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findNotActiveUsers() {
        return userRepository.findAllByUserStatus(UserStatus.NOT_ACTIVE);
    }

    @Override
    public void activateUsers(NotActiveUsers users) {

        users.getNotActiveUsers().forEach(user -> {
            user.setUserStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        });
    }
}
