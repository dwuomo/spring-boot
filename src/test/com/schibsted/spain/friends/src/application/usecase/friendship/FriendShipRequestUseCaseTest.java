package com.schibsted.spain.friends.src.application.usecase.friendship;

import com.schibsted.spain.friends.src.application.usecase.friendShip.FriendShipRequestRequest;
import com.schibsted.spain.friends.src.application.usecase.friendShip.FriendShipRequestUseCase;
import com.schibsted.spain.friends.src.domain.infrastructure.enums.RelationShipStatus;
import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.FriendShipAlreadyExistsException;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.FriendShipRepository;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.friendShip.UserRepository;
import com.schibsted.spain.friends.src.domain.model.friendShip.FriendShip;
import com.schibsted.spain.friends.src.domain.model.friendShip.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FriendShipRequestUseCaseTest {

    @Test
    void test_should_create_friendship_request() throws Exception {
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        FriendShipRepository friendShipRepositoryMock = Mockito.mock(FriendShipRepository.class);
        ArgumentCaptor<User> userCaptured = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<FriendShip> friendShipCaptured = ArgumentCaptor.forClass(FriendShip.class);

        FriendShipRequestUseCase useCase = new FriendShipRequestUseCase (userRepositoryMock, friendShipRepositoryMock);

        User requesterMock = new User(1, "paco");
        User addresseeMock = new User(2, "pepe");

        when(userRepositoryMock.ofUserNameOrFail(any())).thenReturn(requesterMock, addresseeMock);
        when(friendShipRepositoryMock.existsFriendShip(userCaptured.capture(), userCaptured.capture())).thenReturn(false);

        FriendShipRequestRequest request = new FriendShipRequestRequest("paco", "pepe");
        useCase.handle(request);

        List<User> allArguments = userCaptured.getAllValues();
        verify(userRepositoryMock, times(2)).ofUserNameOrFail(any());
        verify(friendShipRepositoryMock, times(1)).save(friendShipCaptured.capture());
        assertEquals("paco", allArguments.get(0).getUsername());
        assertEquals("pepe", allArguments.get(1).getUsername());
        assertEquals(friendShipCaptured.getValue().getStatus(), RelationShipStatus.PENDING);
        assertEquals(friendShipCaptured.getValue().getRequester().getUsername(), requesterMock.getUsername());
        assertEquals(friendShipCaptured.getValue().getAddressee().getUsername(), addresseeMock.getUsername());
    }

    @Test
    void test_should_throught_exception_if_friendship_exists() {
        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
        FriendShipRepository friendShipRepositoryMock = Mockito.mock(FriendShipRepository.class);
        FriendShipRequestUseCase useCase = new FriendShipRequestUseCase (userRepositoryMock, friendShipRepositoryMock);

        FriendShipRequestRequest request = new FriendShipRequestRequest("paco", "pepe");

        when(friendShipRepositoryMock.existsFriendShip(any(), any())).thenReturn(true);

        try {
            useCase.handle(request);
        } catch (FriendShipAlreadyExistsException e) {
            assertEquals("Not available to request friendship again", e.getMessage());
        }
    }
}
