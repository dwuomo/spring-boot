package com.schibsted.spain.friends.src.application.usecase.friendShip;

public class DeclineFriendShipRequest {
    private String usernameFrom;
    private String usernameTo;

    public DeclineFriendShipRequest(String usernameFrom, String usernameTo) {
        this.usernameFrom = usernameFrom;
        this.usernameTo = usernameTo;
    }

    public String getUsernameFrom() {
        return usernameFrom;
    }

    public String getUsernameTo() {
        return usernameTo;
    }
}
