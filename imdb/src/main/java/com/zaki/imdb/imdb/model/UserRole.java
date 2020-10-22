package com.zaki.imdb.imdb.model;

public enum UserRole {
    ANONYMOUS, REGISTERED, MODERATOR, ADMINISTRATOR;

    public String getAsString() {
        return name();
    }
}
