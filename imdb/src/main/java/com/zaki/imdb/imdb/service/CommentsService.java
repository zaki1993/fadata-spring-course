package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Comment;

import java.util.List;

public interface CommentsService extends EntityService {
    Comment getCommentById(Long commentId);

    @Override
    default String getEntityName() {
        return "Comment";
    }

    Comment updateComment(Comment comment, boolean canBeApproved);

    Comment deleteComment(Long commentId);

    List<Comment> getUnapprovedComments();

    Comment createComment(Comment comment);

    List<Comment> getMovieComments(Long movieId);
}
