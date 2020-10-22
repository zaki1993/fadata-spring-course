package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesJpaRepository extends JpaRepository<Category, Long> {
}
