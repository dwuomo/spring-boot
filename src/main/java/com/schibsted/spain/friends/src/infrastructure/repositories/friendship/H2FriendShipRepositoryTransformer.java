package com.schibsted.spain.friends.src.infrastructure.repositories.friendship;

import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.Friends;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import com.schibsted.spain.friends.src.infrastructure.mapping.FriendShipMapping;
import com.schibsted.spain.friends.src.infrastructure.mapping.UserMapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class H2FriendShipRepositoryTransformer {

    public FriendShip fromFriendShipMappingToFriendShipModel(FriendShipMapping mapping) {
        User requesterModel = new User(mapping.getRequester().getId(), mapping.getRequester().getUsername());
        User addreeseeModel = new User(mapping.getAddressee().getId(), mapping.getAddressee().getUsername());

        return new FriendShip(mapping.getId(), requesterModel, addreeseeModel, mapping.getStatus());
    }

    public FriendShipMapping fromFriendShipModelToFriendShipMapping(FriendShip friendShip) {
        UserMapping requester = new UserMapping();
        requester.setUsername(friendShip.getRequester().getUsername());
        requester.setId(friendShip.getRequester().getId());

        UserMapping addressee = new UserMapping();
        addressee.setUsername(friendShip.getAddressee().getUsername());
        addressee.setId(friendShip.getAddressee().getId());

        FriendShipMapping mapping = new FriendShipMapping();
        mapping.setAddressee(addressee);
        mapping.setRequester(requester);
        mapping.setStatus(friendShip.getStatus());

        return mapping;
    }

    public UserMapping fromUserModelToUserMapping(User user) {
        UserMapping userMapping = new UserMapping();
        userMapping.setUsername(user.getUsername());
        userMapping.setId(user.getId());

        return userMapping;
    }

    public Friends fromListFriendShipMappingToFriends(List<FriendShipMapping> mapping, String username) {

        Friends friends = new Friends(username);

        mapping.forEach((item)->{
            if (item.getRequester().getUsername() != username) {
                friends.addFriend(new User(item.getRequester().getId(), item.getRequester().getUsername()));
            } else {
                friends.addFriend(new User(item.getAddressee().getId(), item.getAddressee().getUsername()));
            }
        });

        return friends;
    }
}
