package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Category;
import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.model.entity.Movie;

import java.util.List;

public interface MoviesService extends EntityService{

    @Override
    default String getEntityName() {
        return "Movie";
    }

    Movie deleteMovie(long id);

    Movie updateMovie(Movie movie);

    Movie createMovie(Movie movie);

    List<Movie> getMovieByName(String name);

    Movie getMovieById(long id);

    List<Movie> getAllMovies();

    Movie getMovieFromCategory(Category category, Long id);
}
