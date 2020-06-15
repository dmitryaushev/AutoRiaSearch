package com.aushev.autoriasearch.repository;

import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer> {
    List<Search> findAllByUser(User user);

    List<Search> findTop5ByUserOrderByDateDesc(User user);

    List<Search> findAllByUserAndMailing(User user, boolean mailing);
}
