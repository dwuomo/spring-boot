package com.schibsted.spain.friends.src.infrastructure.springData;

import com.schibsted.spain.friends.src.infrastructure.mapping.UserMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends CrudRepository<UserMapping, Integer> {
    UserMapping findByUsername(String username);
    UserMapping findByUsernameAndPassword(String username, String password);
}
