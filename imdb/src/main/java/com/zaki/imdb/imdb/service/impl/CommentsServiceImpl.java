package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.CommentsJpaRepository;
import com.zaki.imdb.imdb.exception.ResourceEntityDataException;
import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.service.CommentsService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsJpaRepository commentsJpaRepository;

    @Override
    public Comment getCommentById(Long commentId) {
        return commentsJpaRepository.findById(commentId).orElseThrow(() -> ExceptionUtils.newNonExistingEntityExceptionFromService(this, "id", commentId));
    }

    @Override
    public Comment getMovieComment(Movie movie, Long commentId) {
        Comment comment = getCommentById(commentId);
        if (!comment.getMovie().equals(movie)) {
            throw new ResourceEntityDataException(String.format("Comment %d is not present in movie %s", commentId, movie.getName()));
        }
        return comment;
    }
}
