package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Category;
import com.zaki.imdb.imdb.model.entity.Movie;

import java.util.List;

public interface CategoriesService extends EntityService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category getCategoryByName(String name);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    Category deleteCategory(Long id);

    @Override
    default String getEntityName() {
        return "Category";
    }

    Movie getCategoryMovie(Category category, Movie movie);
}
