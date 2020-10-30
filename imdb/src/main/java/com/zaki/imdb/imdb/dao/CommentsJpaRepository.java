package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsJpaRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByApproved(boolean approved);
    List<Comment> findAllByMovieId(Long movieId);
}
