package com.schibsted.spain.friends.src.domain.model.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;

public class FriendShip {

    private int id;
    private User requester;
    private User addressee;
    private RelationShipStatus status;

    public FriendShip(User userRequester, User userRequested, RelationShipStatus status) {
        this.requester = userRequester;
        this.addressee = userRequested;
        this.status = status;
    }

    public FriendShip(int id, User userRequester, User userRequested, RelationShipStatus status) {
        this.id = id;
        this.requester = userRequester;
        this.addressee = userRequested;
        this.status = status;
    }

    public void acceptRelationShip() {
        this.status = RelationShipStatus.ACCEPTED;
    }

    public User getRequester() {
        return requester;
    }

    public User getAddressee() {
        return addressee;
    }

    public RelationShipStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }
}
