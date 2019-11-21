package com.schibsted.spain.friends.src.infrastructure.springData;

import com.schibsted.spain.friends.src.infrastructure.mapping.FriendShipMapping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFriendShipRepository extends CrudRepository<FriendShipMapping, Integer> {
    void deleteById(int id);
}
