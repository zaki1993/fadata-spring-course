package com.zaki.imdb.imdb.exception.base;

public class BaseIMDBException extends RuntimeException {

    private int statusCode;

    public BaseIMDBException(String msg, int statusCode) {
        super(msg);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
