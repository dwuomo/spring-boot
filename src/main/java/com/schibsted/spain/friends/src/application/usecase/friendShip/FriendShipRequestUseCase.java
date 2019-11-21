package com.schibsted.spain.friends.src.application.usecase.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;
import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.FriendShipAlreadyExistsException;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendShipRequestUseCase {

    private final UserRepository userRepository;

    private FriendShipRepository friendShipRepository;

    @Autowired
    public FriendShipRequestUseCase(UserRepository userRepository, FriendShipRepository friendShipRepository) {
        this.userRepository = userRepository;
        this.friendShipRepository = friendShipRepository;
    }

    public void handle(FriendShipRequestRequest request) throws FriendShipAlreadyExistsException {
        User requester = this.userRepository.ofUserNameOrFail(request.getUsernameFrom());
        User addressee = this.userRepository.ofUserNameOrFail(request.getUserNameTo());

        if (this.friendShipRepository.existsFriendShip(requester, addressee)) {
            throw new FriendShipAlreadyExistsException("Not available to request friendship again");
        }

        FriendShip friendShip = new FriendShip(requester, addressee, RelationShipStatus.PENDING);
        this.friendShipRepository.save(friendShip);
    }
}
