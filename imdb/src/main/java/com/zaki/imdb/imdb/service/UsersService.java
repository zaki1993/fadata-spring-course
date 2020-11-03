package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.User;

import java.util.List;

public interface UsersService extends EntityService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User createUser(User user);

    User updateUser(User user, boolean modifyRoles);

    User deleteUser(Long id);

    @Override
    default String getEntityName() {
        return "User";
    }
}
