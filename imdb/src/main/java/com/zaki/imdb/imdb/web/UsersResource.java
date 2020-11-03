package com.zaki.imdb.imdb.web;

import com.zaki.imdb.imdb.model.dto.UserDTO;
import com.zaki.imdb.imdb.model.entity.User;
import com.zaki.imdb.imdb.service.UsersService;
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

@RestController("userResource")
@RequestMapping("/imdb/users")
public class UsersResource {
    @Autowired
    private UsersService usersService;

    @Autowired
    private ModelMapper mapper;

    // TODO user Model mapper

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return usersService.getAllUsers().stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return mapper.map(usersService.getUserById(id), UserDTO.class);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user, HttpServletRequest request) {
        UserDTO created = mapper.map(usersService.createUser(user), UserDTO.class);
        return ResponseEntity.created(
                MvcUriComponentsBuilder.fromMethodCall(on(UsersResource.class).createUser(user, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody User user, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, user.getId());
        return mapper.map(usersService.updateUser(user, false), UserDTO.class);
    }

    @PutMapping("{id}/promote")
    public UserDTO promoteUser(@PathVariable Long id, @Valid @RequestBody User user, Errors errors) {
        ExceptionUtils.onResourceEntryValidation(errors, id, user.getId());
        return mapper.map(usersService.updateUser(user, true), UserDTO.class);
    }

    @DeleteMapping("{id}")
    public UserDTO deleteUser(@PathVariable long id) {
        return mapper.map(usersService.deleteUser(id), UserDTO.class);
    }
}