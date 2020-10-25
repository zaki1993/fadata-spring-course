package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.model.entity.Movie;

public interface CommentsService extends EntityService {
    Comment getCommentById(Long commentId);

    @Override
    default String getEntityName() {
        return "Comment";
    }

    Comment getMovieComment(Movie movie, Long commentId);
}
