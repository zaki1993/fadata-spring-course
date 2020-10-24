package com.zaki.imdb.imdb.exception;

import com.zaki.imdb.imdb.exception.base.BaseIMDBException;

public class EntityAlreadyExistsException extends BaseIMDBException {
    public EntityAlreadyExistsException(String msg) {
        super(msg, 400);
    }
}
