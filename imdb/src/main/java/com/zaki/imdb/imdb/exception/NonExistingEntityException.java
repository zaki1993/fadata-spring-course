package com.zaki.imdb.imdb.exception;

import com.zaki.imdb.imdb.exception.base.BaseIMDBException;

public class NonExistingEntityException extends BaseIMDBException {
    public NonExistingEntityException(String msg) {
        super(msg, 404);
    }
}
