package com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.UserRegisteredNotFound;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("friendShipUserRepository")
public interface UserRepository {
    User ofUserNameOrFail(String username) throws UserRegisteredNotFound;
}
