package com.aushev.autoriasearch.repository;

import com.aushev.autoriasearch.model.user.User;
import com.aushev.autoriasearch.model.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    List<User> findAllByUserStatus(UserStatus status);

    @Query("select u from User u join Search s on u = s.user where s.mailing = true group by u")
    List<User> findAllByMailing();
}
