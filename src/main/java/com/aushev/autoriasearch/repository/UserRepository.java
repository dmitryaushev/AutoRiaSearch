package com.aushev.autoriasearch.repository;

import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findAllByUserStatus(UserStatus status);
}
