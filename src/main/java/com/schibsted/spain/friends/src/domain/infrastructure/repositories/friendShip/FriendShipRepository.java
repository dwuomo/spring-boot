package com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.FriendShipNotFoundException;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.Friends;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("h2FriendShipRepository")
public interface FriendShipRepository {
    FriendShip ofFriendShipOrFail(User requester, User addressee) throws FriendShipNotFoundException;
    boolean existsFriendShip(User requester, User addresee);
    void save(FriendShip friendShip);
    void updateStatus(FriendShip friendShip);
    void removeFriendShipRequest(FriendShip friendShip);
    Friends getFriends(User user);
}
