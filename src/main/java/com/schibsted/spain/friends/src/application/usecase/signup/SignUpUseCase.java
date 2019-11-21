package com.schibsted.spain.friends.src.application.usecase.signup;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.InvalidParameterException;
import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.UserAlreadyExists;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.signup.UsersRepository;
import com.schibsted.spain.friends.src.domain.model.signup.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpUseCase {

    private UsersRepository usersRespository;

    @Autowired
    public SignUpUseCase(UsersRepository usersRepository) {
        this.usersRespository = usersRepository;
    }

    public void handle(SignUpRequest request) throws UserAlreadyExists, InvalidParameterException {
        User user = new User(request.getUsername(), request.getPassword());

        User maybeUserRegistered = this.usersRespository.findByUsername(user.getUsername());

        if (null != maybeUserRegistered) {
            throw new UserAlreadyExists("User already exists");
        }

        this.usersRespository.createNewUser(user);
    }
}
