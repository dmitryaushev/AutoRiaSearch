package com.aushev.autoriasearch.service;

import com.aushev.autoriasearch.exception.DeactivateAdminException;
import com.aushev.autoriasearch.exception.UserNotExistException;
import com.aushev.autoriasearch.model.user.NotActiveUsers;
import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserRole;
import com.aushev.autoriasearch.model.user.UserStatus;
import com.aushev.autoriasearch.repository.SearchRepository;
import com.aushev.autoriasearch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotExistException(String.format("User with email %s not exist", email)));
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
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
        if (user.getUserRole().equals(UserRole.ROLE_ADMIN)) {
            checkAdmin();
            user.setUserRole(UserRole.ROLE_USER);
        }
        user.setUserStatus(UserStatus.NOT_ACTIVE);
        searchRepository.deactivateMailing(id);
        userRepository.save(user);
    }

    @Override
    public void switchRole(int id) {
        User user = userRepository.findById(id).get();
        if (user.getUserRole().equals(UserRole.ROLE_ADMIN)) {
            checkAdmin();
            user.setUserRole(UserRole.ROLE_USER);
        } else {
            if (user.getUserStatus().equals(UserStatus.NOT_ACTIVE)) {
                user.setUserStatus(UserStatus.ACTIVE);
            }
            user.setUserRole(UserRole.ROLE_ADMIN);
        }
        userRepository.save(user);
    }

    private void checkAdmin() {
        if (userRepository.countByUserRole(UserRole.ROLE_ADMIN) < 2) {
            throw new DeactivateAdminException("Остепенись, ты тут последий админ, без тебя будет сложно.");
        }
    }
}

