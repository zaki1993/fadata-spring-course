package com.zaki.imdb.imdb.service;

import com.zaki.imdb.imdb.model.entity.Genre;

import java.util.List;

public interface GenresService extends EntityService {
    List<Genre> getAllGenres();

    Genre getGenreByName(String name);

    Genre createGenre(Genre genre);

    Genre updateGenre(Genre genre);

    Genre deleteGenre(String name);

    @Override
    default String getEntityName() {
        return "Genre";
    }
}
