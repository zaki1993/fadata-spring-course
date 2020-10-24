package com.zaki.imdb.imdb.exception;

import com.zaki.imdb.imdb.exception.base.BaseIMDBException;

public class ResourceEntityDataException extends BaseIMDBException {

    public ResourceEntityDataException(String msg) {
        super(msg, 400);
    }
}
