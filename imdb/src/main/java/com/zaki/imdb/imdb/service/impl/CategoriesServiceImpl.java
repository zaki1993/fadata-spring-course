package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.CategoriesJpaRepository;
import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.model.entity.Category;
import com.zaki.imdb.imdb.service.CategoriesService;
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
    private CategoriesJpaRepository categoriesJpaRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoriesJpaRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) throws NonExistingEntityException {
        return categoriesJpaRepository.findById(id).orElseThrow(() -> ExceptionUtils.newNonExistingEntityExceptionFromService(this, "id", id));
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
    public Category deleteCategory(Long id) throws NonExistingEntityException {
        Category result = getCategoryById(id);
        categoriesJpaRepository.deleteById(id);
        return result;
    }
}
