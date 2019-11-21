package com.schibsted.spain.friends.src.application.usecase.friendShip;

public class FriendShipRequestRequest {

    private String usernameFrom;
    private String userNameTo;

    public FriendShipRequestRequest(String usernameFrom, String userNameTo) {
        this.usernameFrom = usernameFrom;
        this.userNameTo = userNameTo;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public String getUserNameTo() {
        return userNameTo;
    }
}
