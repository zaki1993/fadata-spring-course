package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsJpaRepository extends JpaRepository<Comment, Long> {
}
