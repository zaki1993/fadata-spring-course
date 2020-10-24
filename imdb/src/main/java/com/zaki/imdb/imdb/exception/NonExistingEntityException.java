package com.zaki.imdb.imdb.exception;

public class NonExistingEntityException extends RuntimeException {
    public NonExistingEntityException(String msg) {
        super(msg);
    }
}
