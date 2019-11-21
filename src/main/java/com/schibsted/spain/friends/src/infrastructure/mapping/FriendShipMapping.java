package com.schibsted.spain.friends.src.infrastructure.mapping;

import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;

import javax.persistence.*;

@Entity
@Table(name="friendship")
public class FriendShipMapping {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @OneToOne
    private UserMapping requester;
    @OneToOne
    private UserMapping addressee;
    @Enumerated(EnumType.STRING)
    private RelationShipStatus status;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public UserMapping getRequester() {
        return requester;
    }

    public void setRequester(UserMapping requester) {
        this.requester = requester;
    }

    public UserMapping getAddressee() {
        return addressee;
    }

    public void setAddressee(UserMapping addressee) {
        this.addressee = addressee;
    }

    public RelationShipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationShipStatus status) {
        this.status = status;
    }
}
