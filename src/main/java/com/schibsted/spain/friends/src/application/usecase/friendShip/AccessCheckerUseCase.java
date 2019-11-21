package com.schibsted.spain.friends.src.application.usecase.friendShip;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.AuthorizationException;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.signup.UsersRepository;
import com.schibsted.spain.friends.src.domain.model.signup.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessCheckerUseCase {

    private UsersRepository usersRepository;

    @Autowired
    public AccessCheckerUseCase(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void handle(AccessCheckerRequest request) throws Exception {
        User maybeUserRegistered = this.usersRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        if (null == maybeUserRegistered) {
            throw new AuthorizationException("Username or password is not correct");
        }
    }
}
