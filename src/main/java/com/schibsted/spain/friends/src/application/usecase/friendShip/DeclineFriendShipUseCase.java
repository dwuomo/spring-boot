package com.schibsted.spain.friends.src.application.usecase.friendShip;

import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeclineFriendShipUseCase {

    private UserRepository userRepository;
    private FriendShipRepository friendShipRepository;

    @Autowired
    public DeclineFriendShipUseCase(UserRepository userRepository, FriendShipRepository friendShipRepository) {
        this.userRepository = userRepository;
        this.friendShipRepository = friendShipRepository;
    }

    public void handle(DeclineFriendShipRequest request) {
        User userFrom = this.userRepository.ofUserNameOrFail(request.getUsernameFrom());
        User userTo = this.userRepository.ofUserNameOrFail(request.getUsernameTo());

        FriendShip friendShip = this.friendShipRepository.ofFriendShipOrFail(userFrom, userTo);

        this.friendShipRepository.removeFriendShipRequest(friendShip);
    }
}
