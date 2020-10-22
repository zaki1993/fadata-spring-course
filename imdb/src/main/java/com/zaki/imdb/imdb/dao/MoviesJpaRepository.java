package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesJpaRepository extends JpaRepository<Movie, Long> {
}
