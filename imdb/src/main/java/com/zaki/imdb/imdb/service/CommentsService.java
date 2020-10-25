package com.zaki.imdb.imdb.service;

public interface CommentsService extends EntityService {

    @Override
    default String getEntityName() {
        return "Comment";
    }
}
