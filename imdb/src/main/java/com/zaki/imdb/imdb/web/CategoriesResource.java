package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.entity.Category;
import com.zaki.imdb.imdb.model.entity.Movie;
import com.zaki.imdb.imdb.service.CategoriesService;
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
@RequestMapping("/imdb/category")
public class CategoriesResource {

    @Autowired
    private  MoviesResource moviesResource;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    ModelMapper mapper;

    // TODO category Model mapper

    @GetMapping
    public List<Category> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    @GetMapping("{categoryId}")
    public Category getCategoryById(@PathVariable long categoryId) {
        return categoriesService.getCategoryById(categoryId);
    }

    @GetMapping("{categoryName}")
    public Category getCategoryByName(@PathVariable String categoryName) {
        return categoriesService.getCategoryByName(categoryName);
    }

    @GetMapping("{categoryName}/movie")
    public List<Movie> getAllMoviesInCategory(@PathVariable String categoryName) {
        return getCategoryByName(categoryName).getMovies();
    }

    @GetMapping("{categoryName}/movie/{movieId}")
    public Movie getMovieInCategory(@PathVariable String categoryName, @PathVariable Long movieId) {
        return categoriesService.getCategoryMovie(getCategoryByName(categoryName), moviesResource.getMovieById(movieId));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category, HttpServletRequest request) {
        Category created = categoriesService.createCategory(category);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(CategoriesResource.class).createCategory(category, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public Category updateCategory(@PathVariable Long id, @Valid @RequestBody Category category, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, category.getId());
        return categoriesService.updateCategory(category);
    }

    @DeleteMapping("{id}")
    public Category deleteCategory(@PathVariable long id) {
        return categoriesService.deleteCategory(id);
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