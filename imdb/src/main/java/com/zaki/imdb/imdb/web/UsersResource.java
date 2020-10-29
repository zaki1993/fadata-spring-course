package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.entity.User;
import com.zaki.imdb.imdb.service.UsersService;
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

@RestController("userResource")
@RequestMapping("/imdb/users")
public class UsersResource {
    @Autowired
    private UsersService usersService;

    @Autowired
    private ModelMapper mapper;

    // TODO user Model mapper

    @GetMapping
    public List<User> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable long id) {
        return usersService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) {
        User created = usersService.createUser(user);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(UsersResource.class).createUser(user, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, user.getId());
        return usersService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable long id) {
        return usersService.deleteUser(id);
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