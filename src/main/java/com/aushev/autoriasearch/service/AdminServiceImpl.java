package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserStatus;
import com.aushev.autoriasearch.repository.SearchRepository;
import com.aushev.autoriasearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private SearchRepository searchRepository;
    private UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(SearchRepository searchRepository, UserRepository userRepository) {
        this.searchRepository = searchRepository;
        this.userRepository = userRepository;
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

    @Override
    public void activateUser(int id) {
        User user = userRepository.findById(id).get();
        user.setUserStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(int id) {
        User user = userRepository.findById(id).get();
        user.setUserStatus(UserStatus.NOT_ACTIVE);
        searchRepository.deactivateMailing(id);
        userRepository.save(user);
    }
}

