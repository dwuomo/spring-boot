package com.schibsted.spain.friends.src.application.usecase.friendship;

import com.schibsted.spain.friends.src.application.usecase.friendShip.DeclineFriendShipRequest;
import com.schibsted.spain.friends.src.application.usecase.friendShip.DeclineFriendShipUseCase;
import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeclineFriendShipUseCaseTest {

    @Test
    void test_should_decline_friendship() {
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        FriendShipRepository friendShipRepositoryMock = Mockito.mock(FriendShipRepository.class);
        DeclineFriendShipUseCase useCase = new DeclineFriendShipUseCase(userRepositoryMock, friendShipRepositoryMock);
        User requesterStub = new User(1, "paco");
        User addresseeStub = new User(1, "pepe");
        FriendShip friendShipStub = new FriendShip(requesterStub, addresseeStub, RelationShipStatus.PENDING);

        when(userRepositoryMock.ofUserNameOrFail(any())).thenReturn(requesterStub, addresseeStub);
        when(friendShipRepositoryMock.ofFriendShipOrFail(any(), any())).thenReturn(friendShipStub);

        DeclineFriendShipRequest request = new DeclineFriendShipRequest(requesterStub.getUsername(), addresseeStub.getUsername());
        useCase.handle(request);

        verify(userRepositoryMock, times(2)).ofUserNameOrFail(any());
        verify(friendShipRepositoryMock, times(1)).ofFriendShipOrFail(any(), any());
        verify(friendShipRepositoryMock, times(1)).removeFriendShipRequest(any());
    }

}