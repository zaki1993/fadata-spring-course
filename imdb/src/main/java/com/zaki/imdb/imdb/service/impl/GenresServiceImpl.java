package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.GenresJpaRepository;
import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.model.entity.Genre;
import com.zaki.imdb.imdb.service.GenresService;
import com.zaki.imdb.imdb.service.MoviesService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import com.zaki.imdb.imdb.util.IMDBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zaki.imdb.imdb.util.ExceptionUtils.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class GenresServiceImpl implements GenresService {

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private GenresJpaRepository genresJpaRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genresJpaRepository.findAll();
    }

    @Override
    public Genre getGenreByName(String name) throws NonExistingEntityException {
        return genresJpaRepository.findByName(name).orElseThrow(() -> ExceptionUtils.newNonExistingEntityExceptionFromService(this, "name", name));
    }

    @Override
    public Genre createGenre(Genre genre) throws InvalidEntityDataException, EntityAlreadyExistsException {
        Genre existingGenre = null;
        try {
            existingGenre = getGenreByName(genre.getName());
        } catch (NonExistingEntityException e) {
            IMDBUtils.noop();
        }
        if (existingGenre != null) {
            throw newEntityAlreadyExitsExceptionFromService(this, "name", genre.getName());
        }

        genre.setId(null);
        Genre result = null;
        try {
            result = genresJpaRepository.save(genre);
        } catch (RuntimeException e) {
            ExceptionUtils.handleConstraintViolationException(e);
        }

        return result;
    }

    @Override
    public Genre updateGenre(Genre genre) throws NonExistingEntityException, InvalidEntityDataException {
        Genre result = genresJpaRepository.findById(genre.getId()).orElseThrow(
                () -> newNonExistingEntityExceptionFromService(this, "id", genre.getId()));
        if (!result.getName().equals(genre.getName())) {
            throw newInvalidEntityDataExceptionFromService(this, "name", result.getName());
        }
        return genresJpaRepository.save(genre);
    }

    @Override
    public Genre deleteGenre(String name) throws NonExistingEntityException {
        Genre result = getGenreByName(name);
        genresJpaRepository.deleteById(result.getId());
        return result;
    }
}
