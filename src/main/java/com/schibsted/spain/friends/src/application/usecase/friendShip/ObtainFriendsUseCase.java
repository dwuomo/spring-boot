package com.schibsted.spain.friends.src.application.usecase.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.Friends;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtainFriendsUseCase {
    private FriendShipRepository repository;
    private UserRepository userRepository;

    @Autowired
    public ObtainFriendsUseCase(FriendShipRepository friendShipRepository, UserRepository userRepository) {
        this.repository = friendShipRepository;
        this.userRepository = userRepository;
    }

    public ObtainFriendsResponse handle(ObtainFriendsRequest request) {
        User user = this.userRepository.ofUserNameOrFail(request.getUsername());
        Friends friendList = this.repository.getFriends(user);
        ObtainFriendsResponse response = new ObtainFriendsResponse();
        for (User item: friendList.getFriend()) {
            response.addFriend(item.getUsername());
        }

        return response;
    }
}
