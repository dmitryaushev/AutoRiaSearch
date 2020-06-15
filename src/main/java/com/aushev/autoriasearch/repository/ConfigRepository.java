package com.aushev.autoriasearch.repository;

import com.aushev.autoriasearch.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Integer> {

    Optional<Config> findByTitle(String title);

}
