package com.schibsted.spain.friends.src.application.usecase.friendShip;

public class AccessCheckerRequest {
    private String username;
    private String password;

    public AccessCheckerRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
