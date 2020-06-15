package com.aushev.autoriasearch.repository;

import com.aushev.autoriasearch.model.search.Search;
import com.aushev.autoriasearch.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Integer> {
    
    Page<Search> findAllByUser(User user, Pageable pageable);

    List<Search> findTop5ByUserOrderByDateDesc(User user);

    List<Search> findAllByUserAndMailing(User user, boolean mailing);

    @Modifying
    @Transactional
    @Query(value = "update search set mailing = false where user_id = :id", nativeQuery = true)
    void deactivateMailing(@Param("id") int id);
}
