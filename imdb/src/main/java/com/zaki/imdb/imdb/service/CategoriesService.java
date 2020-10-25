package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.model.entity.Category;

import java.util.List;

public interface CategoriesService extends EntityService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id) throws NonExistingEntityException;

    Category getCategoryByName(String name) throws NonExistingEntityException;

    Category createCategory(Category category) throws InvalidEntityDataException, EntityAlreadyExistsException;

    Category updateCategory(Category category) throws NonExistingEntityException, InvalidEntityDataException;

    Category deleteCategory(Long id) throws NonExistingEntityException;

    @Override
    default String getEntityName() {
        return "Category";
    }
}
