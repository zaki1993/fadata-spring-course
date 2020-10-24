package com.zaki.imdb.imdb.exception;

import com.zaki.imdb.imdb.exception.base.BaseIMDBException;

public class InvalidEntityDataException extends BaseIMDBException {
    public InvalidEntityDataException(String msg) {
        super(msg, 400);
    }
}
