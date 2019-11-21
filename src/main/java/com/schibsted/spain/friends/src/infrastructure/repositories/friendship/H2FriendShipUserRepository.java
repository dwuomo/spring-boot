package com.schibsted.spain.friends.src.infrastructure.repositories.friendship;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.UserRegisteredNotFound;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import com.schibsted.spain.friends.src.infrastructure.springData.JpaUserRepository;
import com.schibsted.spain.friends.src.infrastructure.mapping.UserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("friendShipUserRepository")
public class H2FriendShipUserRepository implements UserRepository {

    private JpaUserRepository jpaUserRepository;

    @Autowired
    public H2FriendShipUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User ofUserNameOrFail(String username) throws UserRegisteredNotFound {
        UserMapping mapping = this.jpaUserRepository.findByUsername(username);

        if (null == mapping) {
            throw new UserRegisteredNotFound("User not found");
        }

        return new User(mapping.getId(), mapping.getUsername());
    }
}
