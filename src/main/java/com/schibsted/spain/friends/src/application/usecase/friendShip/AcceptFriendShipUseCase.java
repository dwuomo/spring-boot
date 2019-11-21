package com.schibsted.spain.friends.src.application.usecase.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.FriendShipNotFoundException;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcceptFriendShipUseCase {

    private FriendShipRepository friendShipRepository;
    private UserRepository userRepository;

    @Autowired
    public AcceptFriendShipUseCase(FriendShipRepository friendShipRepository, UserRepository userRepository) {
        this.friendShipRepository = friendShipRepository;
        this.userRepository = userRepository;
    }

    public void handle(AcceptFriendShipRequest request) throws FriendShipNotFoundException  {

        User requester = this.userRepository.ofUserNameOrFail(request.getRequester());
        User addressee = this.userRepository.ofUserNameOrFail(request.getAddressee());

        FriendShip friendShip = this.friendShipRepository.ofFriendShipOrFail(requester, addressee);

        friendShip.acceptRelationShip();

        this.friendShipRepository.updateStatus(friendShip);
    }
}
