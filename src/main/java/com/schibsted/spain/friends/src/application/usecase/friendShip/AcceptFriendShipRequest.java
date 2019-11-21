package com.schibsted.spain.friends.src.application.usecase.friendShip;

public class AcceptFriendShipRequest {
    private String requester;
    private String addressee;

    public AcceptFriendShipRequest(String requester, String addressee) {
        this.requester = requester;
        this.addressee = addressee;
    }

    public String getRequester() {
        return requester;
    }

    public String getAddressee() {
        return addressee;
    }
}
