package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesJpaRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
