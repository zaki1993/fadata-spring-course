package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.MoviesJpaRepository;
import com.zaki.imdb.imdb.exception.ResourceEntityDataException;
import com.zaki.imdb.imdb.model.entity.Genre;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.service.CommentsService;
import com.zaki.imdb.imdb.service.GenresService;
import com.zaki.imdb.imdb.service.MoviesService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zaki.imdb.imdb.util.ExceptionUtils.newInvalidEntityDataExceptionFromService;
import static com.zaki.imdb.imdb.util.ExceptionUtils.newNonExistingEntityExceptionFromService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class MoviesServiceImpl implements MoviesService {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private MoviesJpaRepository moviesJpaRepository;

    @Autowired
    private GenresService genresService;

    @Override
    public Movie deleteMovie(long id) {
        Movie result = getMovieById(id);
        moviesJpaRepository.deleteById(id);
        return result;
    }

    @Override
    public Movie updateMovie(Movie movie) {
        Movie result = moviesJpaRepository.findById(movie.getId()).orElseThrow(
                () -> newNonExistingEntityExceptionFromService(this, "id", movie.getId()));
        if (!result.getName().equals(movie.getName())) {
            throw newInvalidEntityDataExceptionFromService(this, "name", result.getName());
        }
        if (!result.getCreated().equals(movie.getCreated())) {
            throw newInvalidEntityDataExceptionFromService(this, "created", result.getCreated());
        }
        return moviesJpaRepository.save(movie);
    }

    @Override
    public Movie createMovie(Movie movie) {
        movie.setId(null);
        Movie result = null;
        try {
            result = moviesJpaRepository.save(movie);
        } catch (RuntimeException e) {
            ExceptionUtils.handleConstraintViolationException(e);
        }

        return result;
    }

    @Override
    public Movie getMovieById(long id) {
        return moviesJpaRepository.findById(id).orElseThrow(() -> ExceptionUtils.newNonExistingEntityExceptionFromService(this, "id", id));
    }

    @Override
    public List<Movie> getAllMovies() {
        return moviesJpaRepository.findAll();
    }
}
