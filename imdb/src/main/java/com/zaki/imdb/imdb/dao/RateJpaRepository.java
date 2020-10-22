package com.zaki.imdb.imdb.dao;

import com.zaki.imdb.imdb.model.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateJpaRepository extends JpaRepository<Rate, Long> {
}
