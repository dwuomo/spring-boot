package com.schibsted.spain.friends.src.infrastructure.repositories.signup;

import com.schibsted.spain.friends.src.domain.infrastructure.repositories.signup.UsersRepository;
import com.schibsted.spain.friends.src.domain.model.signup.User;
import com.schibsted.spain.friends.src.infrastructure.springData.JpaUserRepository;
import com.schibsted.spain.friends.src.infrastructure.mapping.UserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("signupUserRepository")
public class H2SignupUserRepository implements UsersRepository  {

    private JpaUserRepository jpaUserRepository;

    @Autowired
    public H2SignupUserRepository(JpaUserRepository userRepository) {
        this.jpaUserRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        UserMapping mapping = this.jpaUserRepository.findByUsername(username);

        if (null == mapping) {
            return null;
        }

        return new User(mapping.getUsername(), mapping.getPassword());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        UserMapping mapping = this.jpaUserRepository.findByUsernameAndPassword(username, password);
        if (null == mapping) {
            return null;
        }

        return new User(mapping.getUsername(), mapping.getPassword());
    }

    @Override
    public void createNewUser(User user) {
        UserMapping mapping = new UserMapping();
        mapping.setUsername(user.getUsername());
        mapping.setPassword(user.getPassword());

        this.jpaUserRepository.save(mapping);
    }
}
