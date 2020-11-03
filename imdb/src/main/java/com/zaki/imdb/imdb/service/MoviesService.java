package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.model.entity.Rate;

import java.util.List;
import java.util.Set;

public interface MoviesService extends EntityService {
    @Override
    default String getEntityName() {
        return "Movie";
    }

    Movie deleteMovie(long id);

    Movie updateMovie(Movie movie);

    Movie createMovie(Movie movie);

    Movie getMovieById(long id);

    List<Movie> getAllMovies();

    Set<Movie> getMoviesByGenre(Long id);

    Rate rateMovie(Rate rate);
}
