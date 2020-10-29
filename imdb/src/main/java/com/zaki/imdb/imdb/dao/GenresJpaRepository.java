package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenresJpaRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
