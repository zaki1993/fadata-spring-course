package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.dto.GenreDTO;
import com.zaki.imdb.imdb.model.dto.MovieDTO;
import com.zaki.imdb.imdb.model.entity.Genre;
import com.zaki.imdb.imdb.service.GenresService;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController("genreResource")
@RequestMapping("/imdb/genres")
public class GenresResource {

    @Autowired
    private GenresService genresService;

    @Autowired
    private MoviesService moviesService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public List<GenreDTO> getAllCategories() {
        return genresService.getAllGenres().stream().map(genre -> mapper.map(genre, GenreDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("{name}")
    public GenreDTO getGenreByName(@PathVariable String name) {
        return mapper.map(genresService.getGenreByName(name), GenreDTO.class);
    }

    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@RequestBody Genre genre, HttpServletRequest request) {
        GenreDTO created = mapper.map(genresService.createGenre(genre), GenreDTO.class);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(GenresResource.class).createGenre(genre, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public GenreDTO updateGenre(@PathVariable Long id, @Valid @RequestBody Genre genre, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, genre.getId());
        return mapper.map(genresService.updateGenre(genre), GenreDTO.class);
    }

    @DeleteMapping("{name}")
    public GenreDTO deleteGenre(@PathVariable String name) {
        return mapper.map(genresService.deleteGenre(name), GenreDTO.class);
    }

    @GetMapping("{name}/movies")
    public Set<MovieDTO> getAllMoviesInGenre(@PathVariable String name) {
        return moviesService.getMoviesByGenre(getGenreByName(name).getId()).stream().map(movie -> mapper.map(movie, MovieDTO.class)).collect(Collectors.toSet());
    }
}