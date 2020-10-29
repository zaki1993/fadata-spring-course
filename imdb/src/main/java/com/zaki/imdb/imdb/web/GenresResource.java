package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.entity.Genre;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.service.GenresService;
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

@RestController("genreResource")
@RequestMapping("/imdb/genres")
public class GenresResource {

    @Autowired
    private GenresService genresService;

    @Autowired
    ModelMapper mapper;

    // TODO Genre Model mapper

    @GetMapping
    public List<Genre> getAllCategories() {
        return genresService.getAllGenres();
    }

    @GetMapping("{name}")
    public Genre getGenreByName(@PathVariable String name) {
        return genresService.getGenreByName(name);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre, HttpServletRequest request) {
        Genre created = genresService.createGenre(genre);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(GenresResource.class).createGenre(genre, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public Genre updateGenre(@PathVariable Long id, @Valid @RequestBody Genre genre, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, genre.getId());
        return genresService.updateGenre(genre);
    }

    @DeleteMapping("{name}")
    public Genre deleteGenre(@PathVariable String name) {
        return genresService.deleteGenre(name);
    }

    @GetMapping("{name}/movies")
    public Set<Movie> getAllMoviesInGenre(@PathVariable String name) {
        return getGenreByName(name).getMovies();
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