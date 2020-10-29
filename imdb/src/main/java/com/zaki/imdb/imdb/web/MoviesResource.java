package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.entity.Comment;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.service.MoviesService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController("movieResource")
@RequestMapping("/imdb/movies")
public class MoviesResource {
/*

    @Autowired
    private CommentsService commentsService;
*/

    @Autowired
    private MoviesService moviesService;

    @Autowired
    ModelMapper mapper;

    // TODO movie Model mapper

    @GetMapping
    public List<Movie> getAllMovies() {
        return moviesService.getAllMovies();
    }

    @GetMapping("{id}")
    public Movie getMovieById(@PathVariable long id) {
        return moviesService.getMovieById(id);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie, HttpServletRequest request) {
        Movie created = moviesService.createMovie(movie);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(MoviesResource.class).createMovie(movie, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public Movie updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movie, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, movie.getId());
        return moviesService.updateMovie(movie);
    }

    @DeleteMapping("{id}")
    public Movie deleteMovie(@PathVariable Long id) {
        return moviesService.deleteMovie(id);
    }

    @GetMapping("{movieId}/comments")
    public List<Comment> getMovieComments(@PathVariable Long movieId) {
        return getMovieById(movieId).getComments();
    }

/*
    @GetMapping("{movieId}/comments/{commentId}")
    public Comment getMovieComment(@PathVariable Long movieId, @PathVariable Long commentId) {
        Movie movie = getMovieById(movieId);
        Comment comment = commentsService.getCommentById(commentId);
        ExceptionUtils.onResourceEntryMovieCommentValidation(null, movie, comment);
        return comment;
    }

    @PutMapping("{movieId}/comments/{commentId}")
    public Comment updateMovieComment(@PathVariable Long movieId, @PathVariable Long commentId, @Valid @RequestBody Comment comment) {
        Movie movie = getMovieById(movieId);
        Comment oldComment = commentsService.getCommentById(commentId);
        ExceptionUtils.onResourceEntryMovieCommentValidation(null, movie, oldComment);
        return commentsService.updateComment(comment);
    }

    @DeleteMapping("{movieId}/comments/{commentId}")
    public Comment deleteMovieComment(@PathVariable Long movieId, @PathVariable Long commentId) {
        Movie movie = getMovieById(movieId);
        return commentsService.deleteComment(commentId);
    }
*/

    /*
    private UserDTO convertToDto(User user) {
        UserDTO userDto = mapper.map(user, UserDTO.class);
        //... setOtherProps
        return userDto;
    }

    private User convertToEntity(UserDTO userDto) {
        User user = mapper.map(userDto, User.class);
        //...
        return user;
    }*/
}
