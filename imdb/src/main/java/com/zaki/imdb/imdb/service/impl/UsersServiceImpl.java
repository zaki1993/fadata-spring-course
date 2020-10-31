package com.zaki.imdb.imdb.service.impl;

import com.zaki.imdb.imdb.dao.UsersJpaRepository;
import com.zaki.imdb.imdb.exception.EntityAlreadyExistsException;
import com.zaki.imdb.imdb.exception.InvalidEntityDataException;
import com.zaki.imdb.imdb.exception.NonExistingEntityException;
import com.zaki.imdb.imdb.model.UserRole;
import com.zaki.imdb.imdb.model.entity.User;
import com.zaki.imdb.imdb.service.UsersService;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import com.zaki.imdb.imdb.util.IMDBUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static com.zaki.imdb.imdb.util.ExceptionUtils.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersJpaRepository usersJpaRepository;

    @Override
    public List<User> getAllUsers() {
        return usersJpaRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws NonExistingEntityException {
        return usersJpaRepository.findById(id)
                .orElseThrow(() -> newNonExistingEntityExceptionFromService(this, "id", id));
    }

    @Override
    public User getUserByUsername(String username) throws NonExistingEntityException {
        return usersJpaRepository.findByUsername(username)
                .orElseThrow(() -> newNonExistingEntityExceptionFromService(this, "username", username));
    }

    @Override
    public User getUserByEmail(String email) throws NonExistingEntityException {
        return usersJpaRepository.findUserByEmail(email)
                .orElseThrow(() -> newNonExistingEntityExceptionFromService(this, "email", email));
    }

    @Override
    public User createUser(User user) throws InvalidEntityDataException, EntityAlreadyExistsException {
        // Check whether user with that email already exists
        User existingUser = null;
        try {
            existingUser = getUserByEmail(user.getEmail());
        } catch (NonExistingEntityException e) {
            IMDBUtils.noop();
        }
        // If user with that email already exists then we must throw exception
        if (existingUser != null) {
            throw newEntityAlreadyExitsExceptionFromService(this, "email", user.getEmail());
        }

        // Check whether user with that username already exists
        try {
            existingUser = getUserByUsername(user.getUsername());
        } catch (NonExistingEntityException e) {
            IMDBUtils.noop();
        }
        // If user with that username already exists then we must throw exception
        if (existingUser != null) {
            throw newEntityAlreadyExitsExceptionFromService(this, "username", user.getUsername());
        }

        user.setId(null);
        // Reset any user role. Administrators can only be added from db.
        user.setRoles(Set.of(UserRole.REGISTERED));
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = null;
        try {
            result = usersJpaRepository.save(user);
        } catch (RuntimeException e) {
            ExceptionUtils.handleConstraintViolationException(e);
        }

        return result;
    }

    @Override
    public User updateUser(User user) throws NonExistingEntityException, InvalidEntityDataException {
        User result = usersJpaRepository.findById(user.getId()).orElseThrow(
                () -> newNonExistingEntityExceptionFromService(this, "id", user.getId()));
        if (!result.getEmail().equals(user.getEmail())) {
            throw newInvalidEntityDataExceptionFromService(this, "email", result.getEmail());
        }
        if (!result.getUsername().equals(user.getUsername())) {
            throw newInvalidEntityDataExceptionFromService(this, "username", result.getUsername());
        }
        if (!result.getCreated().equals(user.getCreated())) {
            throw newInvalidEntityDataExceptionFromService(this, "creation date", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(result.getCreated()));
        }
        user.setModified(LocalDateTime.now());
        return usersJpaRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) throws NonExistingEntityException {
        User result = getUserById(id);
        usersJpaRepository.deleteById(id);
        return result;
    }
}
