package com.schibsted.spain.friends.src.application.usecase.friendShip;

import java.util.ArrayList;
import java.util.List;

public class ObtainFriendsResponse {
    List<String> friends;

    public ObtainFriendsResponse() {
        this.friends = new ArrayList<String>();
    }

    public void addFriend(String username) {
        this.friends.add(username);
    }

    public List<String> getFriends() {
        return friends;
    }
}
