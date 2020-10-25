package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.CategoriesJpaRepository;
import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.exception.ResourceEntityDataException;
import com.zaki.imdb.imdb.model.entity.Category;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.service.CategoriesService;
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
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private CategoriesJpaRepository categoriesJpaRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoriesJpaRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name) throws NonExistingEntityException {
        return categoriesJpaRepository.findByName(name).orElseThrow(() -> ExceptionUtils.newNonExistingEntityExceptionFromService(this, "name", name));
    }

    @Override
    public Category createCategory(Category category) throws InvalidEntityDataException, EntityAlreadyExistsException {
        Category existingCategory = null;
        try {
            existingCategory = getCategoryByName(category.getName());
        } catch (NonExistingEntityException e) {
            IMDBUtils.noop();
        }
        if (existingCategory != null) {
            throw newEntityAlreadyExitsExceptionFromService(this, "name", category.getName());
        }

        category.setId(null);
        Category result = null;
        try {
            result = categoriesJpaRepository.save(category);
        } catch (RuntimeException e) {
            ExceptionUtils.handleConstraintViolationException(e);
        }

        return result;
    }

    @Override
    public Category updateCategory(Category category) throws NonExistingEntityException, InvalidEntityDataException {
        Category result = categoriesJpaRepository.findById(category.getId()).orElseThrow(
                () -> newNonExistingEntityExceptionFromService(this, "id", category.getId()));
        if (!result.getName().equals(category.getName())) {
            throw newInvalidEntityDataExceptionFromService(this, "name", result.getName());
        }
        return categoriesJpaRepository.save(category);
    }

    @Override
    public Category deleteCategory(String name) throws NonExistingEntityException {
        Category result = getCategoryByName(name);
        categoriesJpaRepository.deleteById(result.getId());
        return result;
    }

    @Override
    public Movie getMovieFromCategory(String categoryName, Long movieId) {
        Category category = getCategoryByName(categoryName);
        Movie movie = moviesService.getMovieById(movieId);
        if (!movie.getCategory().equals(category)) {
            throw new ResourceEntityDataException(String.format("Movie %s is not in category %s", movie.getName(), category.getName()));
        }
        return movie;
    }
}
