package com.schibsted.spain.friends.src.application.usecase.friendShip;

public class ObtainFriendsRequest {
    private String username;

    public ObtainFriendsRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
