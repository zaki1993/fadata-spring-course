package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.service.CommentsService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController("comments")
@RequestMapping("/imdb/comments")
public class CommentsResource {

    @Autowired
    private CommentsService commentsService;

    @GetMapping
    public List<Comment> getAllUnapprovedComments() {
        return commentsService.getUnapprovedComments();
    }

    @GetMapping("{commentId}")
    public Comment getMovieComment(@PathVariable Long commentId) {
        return commentsService.getCommentById(commentId);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody Comment comment, HttpServletRequest request) {
        Comment created = commentsService.createComment(comment);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(CommentsResource.class).createComment(comment, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @Valid @RequestBody Comment comment, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, commentId, comment.getId());
        return commentsService.updateComment(comment, false);
    }

    @PutMapping("{commentId}/approve")
    public Comment approveComment(@PathVariable Long commentId, @Valid @RequestBody Comment comment, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, commentId, comment.getId());
        return commentsService.updateComment(comment, true);
    }

    @DeleteMapping("{commentId}")
    public Comment deleteComment(@PathVariable Long commentId) {
        return commentsService.deleteComment(commentId);
    }
}
