package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.dto.CommentDTO;
import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.service.CommentsService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController("comments")
@RequestMapping("/imdb/comments")
public class CommentsResource {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<CommentDTO> getAllUnapprovedComments() {
        return commentsService.getUnapprovedComments().stream().map(comment -> mapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("{commentId}")
    public CommentDTO getCommentById(@PathVariable Long commentId) {
        return mapper.map(commentsService.getCommentById(commentId), CommentDTO.class);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody Comment comment, HttpServletRequest request) {
        CommentDTO created = mapper.map(commentsService.createComment(comment), CommentDTO.class);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(CommentsResource.class).createComment(comment, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{commentId}")
    public CommentDTO updateComment(@PathVariable Long commentId, @Valid @RequestBody Comment comment, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, commentId, comment.getId());
        return mapper.map(commentsService.updateComment(comment, false), CommentDTO.class);
    }

    @PutMapping("{commentId}/approve")
    public CommentDTO approveComment(@PathVariable Long commentId, @Valid @RequestBody Comment comment, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, commentId, comment.getId());
        comment.setApproved(true);
        return mapper.map(commentsService.updateComment(comment, true), CommentDTO.class);
    }

    @PutMapping("{commentId}/disapprove")
    public CommentDTO unapproveComment(@PathVariable Long commentId, @Valid @RequestBody Comment comment, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, commentId, comment.getId());
        comment.setApproved(false);
        return mapper.map(commentsService.updateComment(comment, true), CommentDTO.class);
    }

    @DeleteMapping("{commentId}")
    public CommentDTO deleteComment(@PathVariable Long commentId) {
        return mapper.map(commentsService.deleteComment(commentId), CommentDTO.class);
    }
}
