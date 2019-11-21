package com.schibsted.spain.friends.src.application.usecase.friendship;

import com.schibsted.spain.friends.src.application.usecase.friendShip.AcceptFriendShipRequest;
import com.schibsted.spain.friends.src.application.usecase.friendShip.AcceptFriendShipUseCase;
import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AcceptFriendShipUseCaseTest {

    @Test
    void test_should_accept_friendship() {
        FriendShipRepository friendShipRepositoryMock = Mockito.mock(FriendShipRepository.class);
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        User requesterStub = new User(1, "paco");
        User addresseeStub = new User(2, "pepe");
        FriendShip friendShipStub = new FriendShip(requesterStub, addresseeStub, RelationShipStatus.PENDING);

        AcceptFriendShipUseCase useCase = new AcceptFriendShipUseCase(
                friendShipRepositoryMock,
                userRepositoryMock
        );

        ArgumentCaptor<String> userRepositoryArguments = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> friendShipRepositoryArguments = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<FriendShip> updateArguments = ArgumentCaptor.forClass(FriendShip.class);

        when(userRepositoryMock.ofUserNameOrFail(userRepositoryArguments.capture()))
                .thenReturn(requesterStub, addresseeStub);

        when(friendShipRepositoryMock.ofFriendShipOrFail(
                friendShipRepositoryArguments.capture(),
                friendShipRepositoryArguments.capture())
        ).thenReturn(friendShipStub);

        AcceptFriendShipRequest request = new AcceptFriendShipRequest(requesterStub.getUsername(), addresseeStub.getUsername());
        useCase.handle(request);

        verify(friendShipRepositoryMock, times(1)).updateStatus(updateArguments.capture());

        assertEquals(userRepositoryArguments.getAllValues().get(0), requesterStub.getUsername());
        assertEquals(userRepositoryArguments.getAllValues().get(1), addresseeStub.getUsername());
        assertEquals(friendShipRepositoryArguments.getAllValues().get(0), requesterStub);
        assertEquals(friendShipRepositoryArguments.getAllValues().get(1), addresseeStub);
        assertEquals(updateArguments.getValue().getStatus(), RelationShipStatus.ACCEPTED);
    }
}
