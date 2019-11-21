package com.schibsted.spain.friends.src.domain.infrastructure.repositories.signup;

import com.schibsted.spain.friends.src.domain.model.signup.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("signupUserRepository")
public interface UsersRepository {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
    void createNewUser(User user);
}

