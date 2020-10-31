package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.dto.CommentDTO;
import com.zaki.imdb.imdb.model.dto.MovieDTO;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.model.entity.Rate;
import com.zaki.imdb.imdb.service.CommentsService;
import com.zaki.imdb.imdb.service.MoviesService;
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

@RestController("movieResource")
@RequestMapping("/imdb/movies")
public class MoviesResource {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return moviesService.getAllMovies().stream().map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public MovieDTO getMovieById(@PathVariable long id) {
        return mapper.map(moviesService.getMovieById(id), MovieDTO.class);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody Movie movie, HttpServletRequest request) {
        MovieDTO created = mapper.map(moviesService.createMovie(movie), MovieDTO.class);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(MoviesResource.class).createMovie(movie, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public MovieDTO updateMovie(@PathVariable Long id, @Valid @RequestBody Movie movie, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, movie.getId());
        return mapper.map(moviesService.updateMovie(movie), MovieDTO.class);
    }

    @DeleteMapping("{id}")
    public Movie deleteMovie(@PathVariable Long id) {
        return moviesService.deleteMovie(id);
    }

    @GetMapping("{movieId}/comments")
    public List<CommentDTO> getMovieComments(@PathVariable Long movieId) {
        return commentsService.getMovieComments(movieId).stream().map(comment -> mapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    @PostMapping("{movieId}/rate")
    public Rate rateMovie(@PathVariable Long movieId, @Valid @RequestBody Rate rate) {
        ExceptionUtils.onResourceEntryValidation(null, movieId, rate.getMovie().getId());
        return moviesService.rateMovie(rate);
    }
}
