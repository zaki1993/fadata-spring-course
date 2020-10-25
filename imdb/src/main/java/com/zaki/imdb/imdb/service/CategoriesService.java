package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Category;
import com.zaki.imdb.imdb.model.entity.Movie;

import java.util.List;

public interface CategoriesService extends EntityService {
    List<Category> getAllCategories();

    Category getCategoryByName(String name);

    Category createCategory(Category category);

    Category updateCategory(Category category);

    Category deleteCategory(String name);

    @Override
    default String getEntityName() {
        return "Category";
    }

    Movie getMovieFromCategory(String categoryName, Long movieId);
}
