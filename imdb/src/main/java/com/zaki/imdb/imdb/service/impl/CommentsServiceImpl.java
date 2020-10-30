package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.CommentsJpaRepository;
import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.service.CommentsService;
import com.zaki.imdb.imdb.service.MoviesService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.zaki.imdb.imdb.util.ExceptionUtils.newInvalidEntityDataExceptionFromService;
import static com.zaki.imdb.imdb.util.ExceptionUtils.newNonExistingEntityExceptionFromService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsJpaRepository commentsJpaRepository;

    @Autowired
    private MoviesService moviesService;

    @Override
    public Comment getCommentById(Long commentId) {
        return commentsJpaRepository.findById(commentId).orElseThrow(() -> ExceptionUtils.newNonExistingEntityExceptionFromService(this, "id", commentId));
    }

    @Override
    public Comment updateComment(Comment comment, boolean canBeApproved) {
        Comment result = commentsJpaRepository.findById(comment.getId()).orElseThrow(
                () -> newNonExistingEntityExceptionFromService(this, "id", comment.getId()));
        if (!result.getCreated().equals(comment.getCreated())) {
            throw newInvalidEntityDataExceptionFromService(this, "created", result.getCreated());
        }
        if (!result.getAuthor().getId().equals(comment.getAuthor().getId())) {
            throw newInvalidEntityDataExceptionFromService(this, "author", result.getAuthor().getId());
        }
        if (!canBeApproved) {
            if (result.isApproved() != comment.isApproved()) {
                throw newInvalidEntityDataExceptionFromService(this, "approved", result.isApproved());
            }
        }
        return commentsJpaRepository.save(comment);
    }

    @Override
    public Comment deleteComment(Long commentId) {
        Comment result = getCommentById(commentId);
        commentsJpaRepository.deleteById(commentId);
        return result;
    }

    @Override
    public List<Comment> getUnapprovedComments() {
        return commentsJpaRepository.findAllByApproved(false);
    }

    @Override
    public Comment createComment(Comment comment) {
        comment.setId(null);
        Comment result = null;
        try {
            result = commentsJpaRepository.save(comment);
        } catch (RuntimeException e) {
            ExceptionUtils.handleConstraintViolationException(e);
        }

        return result;
    }

    @Override
    public List<Comment> getMovieComments(Long movieId) {
        return commentsJpaRepository.findAllByMovieId(movieId);
    }
}
