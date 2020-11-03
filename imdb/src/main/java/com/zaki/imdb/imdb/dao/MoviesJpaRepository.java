package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesJpaRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByName(String name);
}
