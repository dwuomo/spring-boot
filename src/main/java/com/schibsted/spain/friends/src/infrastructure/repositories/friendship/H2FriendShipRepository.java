package com.schibsted.spain.friends.src.infrastructure.repositories.friendship;

import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;
import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.FriendShipNotFoundException;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.Friends;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import com.schibsted.spain.friends.src.infrastructure.springData.JpaFriendShipRepository;
import com.schibsted.spain.friends.src.infrastructure.mapping.FriendShipMapping;
import com.schibsted.spain.friends.src.infrastructure.mapping.UserMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Component
@Qualifier("h2FriendShipRepository")
public class H2FriendShipRepository implements FriendShipRepository {

    private JpaFriendShipRepository jpaFriendShipRepository;
    private EntityManager em;
    private H2FriendShipRepositoryTransformer transformer;
    private FriendShip friendShip;

    @Autowired
    public H2FriendShipRepository(JpaFriendShipRepository jpaFriendShipRepository, EntityManager em, H2FriendShipRepositoryTransformer transformer) {
        this.jpaFriendShipRepository = jpaFriendShipRepository;
        this.em = em;
        this.transformer = transformer;
    }

    @Override
    public FriendShip ofFriendShipOrFail(User requester, User addressee) throws FriendShipNotFoundException {
        try {
            FriendShipMapping mapping = this.findFriendShip(requester, addressee);

            return this.transformer.fromFriendShipMappingToFriendShipModel(mapping);
        } catch (NoResultException e) {
            throw new FriendShipNotFoundException("Doesn't exists pending friendship");
        }
    }

    @Override
    public boolean existsFriendShip(User requester, User addresee) throws FriendShipNotFoundException {
        try {
            this.findFriendShip(requester, addresee);

            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public void save(FriendShip friendShip) {
        FriendShipMapping mapping = this.transformer.fromFriendShipModelToFriendShipMapping(friendShip);

        this.jpaFriendShipRepository.save(mapping);
    }

    @Override
    public void updateStatus(FriendShip friendShip) {
        FriendShipMapping mapping = this.findFriendShip(friendShip.getRequester(), friendShip.getAddressee());
        mapping.setStatus(friendShip.getStatus());

        this.jpaFriendShipRepository.save(mapping);
    }

    @Override
    public void removeFriendShipRequest(FriendShip friendShip) {
        this.jpaFriendShipRepository.deleteById(friendShip.getId());
    }

    @Override
    public Friends getFriends(User user) {
        String query = "Select f from FriendShipMapping f " +
                "where (f.requester = :username OR f.addressee = :username) " +
                "AND status = :status";

        UserMapping userMapping = this.transformer.fromUserModelToUserMapping(user);

        try {
            List<FriendShipMapping> mapping = (List<FriendShipMapping>) this.em.createQuery(query)
                    .setParameter("username", userMapping)
                    .setParameter("status", RelationShipStatus.ACCEPTED)
                    .getResultList();

            return this.transformer.fromListFriendShipMappingToFriends(mapping, user.getUsername());
        } catch (NoResultException e ) {
            return new Friends(user.getUsername());
        }
    }

    private FriendShipMapping findFriendShip(User requester, User addressee) throws FriendShipNotFoundException {
        String query = "Select f from FriendShipMapping f " +
                "where (f.requester = :requester AND f.addressee = :addressee) " +
                "OR (f.requester = :addressee AND f.addressee = :requester) " +
                "AND status = :status";

        UserMapping requesterMapping = this.transformer.fromUserModelToUserMapping(requester);
        UserMapping addresseeMapping = this.transformer.fromUserModelToUserMapping(addressee);

        return (FriendShipMapping) this.em.createQuery(query)
                .setParameter("requester", requesterMapping)
                .setParameter("addressee", addresseeMapping)
                .setParameter("status", RelationShipStatus.PENDING)
                .getSingleResult();
    }
}
