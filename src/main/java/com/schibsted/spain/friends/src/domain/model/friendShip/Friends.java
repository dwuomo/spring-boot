package com.schibsted.spain.friends.src.domain.model.friendShip;


import java.util.ArrayList;
import java.util.List;

public class Friends {
    private String username;
    private List<User> friend;

    public Friends(String username) {
        this.username = username;
        this.friend = new ArrayList<User>();
    }

    public void addFriend(User user) {
        this.friend.add(user);
    }

    public List<User> getFriend() {
        return friend;
    }

    public String getUsername() {
        return username;
    }
}

