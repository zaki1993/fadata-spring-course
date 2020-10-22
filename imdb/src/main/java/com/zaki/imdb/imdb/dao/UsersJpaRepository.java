package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersJpaRepository extends JpaRepository<User, Long> {
}
