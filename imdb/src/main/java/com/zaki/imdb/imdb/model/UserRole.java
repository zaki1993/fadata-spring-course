package com.zaki.imdb.imdb.model;

public enum UserRole {
    ANONYMOUS("ANONYMOUS"), REGISTERED("REGISTERED"), MODERATOR("MODERATOR"), ADMINISTRATOR("ADMINISTRATOR");

    UserRole(String role) {
        this.role = role;
    }

    private String role;

    public String getAsString() {
        return "ROLE_" + role;
    }
}
