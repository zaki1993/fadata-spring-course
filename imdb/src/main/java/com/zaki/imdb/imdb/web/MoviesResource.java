package com.zaki.imdb.imdb.web;

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

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/imdb/movie")
public class MoviesResource {

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

    @GetMapping("{name}")
    public List<Movie> getMoviesByName(@PathVariable String name) {
        return moviesService.getMovieByName(name);
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
    public Movie deleteMovie(@PathVariable long id) {
        return moviesService.deleteMovie(id);
    }

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
