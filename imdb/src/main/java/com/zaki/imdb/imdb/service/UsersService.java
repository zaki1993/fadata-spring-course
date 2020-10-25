package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.model.entity.User;

import java.util.List;

public interface UsersService extends EntityService {
    List<User> getAllUsers();

    User getUserById(Long id) throws NonExistingEntityException;

    User getUserByUsername(String username) throws NonExistingEntityException;

    User getUserByEmail(String email) throws NonExistingEntityException;

    User createUser(User user) throws InvalidEntityDataException, EntityAlreadyExistsException;

    User updateUser(User user) throws NonExistingEntityException, InvalidEntityDataException;

    User deleteUser(Long id) throws NonExistingEntityException;

    @Override
    default String getEntityName() {
        return "User";
    }
}
