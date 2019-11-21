package com.schibsted.spain.friends.src.application.usecase.signup;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.AuthorizationException;
import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.InvalidParameterException;
import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.UserAlreadyExists;
import com.schibsted.spain.friends.src.domain.infrastructure.repositories.signup.UsersRepository;
import com.schibsted.spain.friends.src.domain.model.signup.User;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest
@ComponentScan
@RunWith(MockitoJUnitRunner.class)
public class SignUpUseCaseTest {

    @Test
    void test_should_signup() {
        UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
        SignUpUseCase usecase = new SignUpUseCase(usersRepository);


        SignUpRequest request = new SignUpRequest("dwuomodwu", "j234567890");
        usecase.handle(request);

        ArgumentCaptor<String> usernameExpected = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> userCaptured = ArgumentCaptor.forClass(User.class);

        verify(usersRepository, times(1)).findByUsername(usernameExpected.capture());
        verify(usersRepository, times(1)).createNewUser(userCaptured.capture());
        assertEquals(usernameExpected.getValue(), request.getUsername());
        assertEquals(userCaptured.getValue().getUsername(), request.getUsername());
        assertEquals(userCaptured.getValue().getPassword(), request.getPassword());
    }

    @Test
    void test_through_exception_if_user_already_exists() {
        UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
        SignUpUseCase usecase = new SignUpUseCase(usersRepository);


        SignUpRequest request = new SignUpRequest("dwuomodwu", "j234567890");
        when(usersRepository.findByUsername(any())).thenReturn(new User("dwuomodwu", "j234567890"));

        try {
            usecase.handle(request);
        } catch (UserAlreadyExists e) {
            assertEquals("User already exists", e.getMessage());
        }
    }

    @Test
    void test_throught_exception_if_user_not_properly_length() {
        UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
        SignUpUseCase usecase = new SignUpUseCase(usersRepository);


        SignUpRequest requestLenghMin = new SignUpRequest("dw", "j234567890");

        try {
            usecase.handle(requestLenghMin);
        } catch (InvalidParameterException e) {
            assertEquals("user name should have more than 5 characters", e.getMessage());
        }

        SignUpRequest requestLengthMax = new SignUpRequest("dwuomodwuomo", "j234567890");

        try {
            usecase.handle(requestLengthMax);
        } catch (InvalidParameterException e) {
            assertEquals("user name should have less than 10 characters", e.getMessage());
        }
    }

    @Test
    void test_throuht_exception_if_password_havent_properly_length_or_format() {
        UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
        SignUpUseCase usecase = new SignUpUseCase(usersRepository);


        SignUpRequest passwordMinLength = new SignUpRequest("dwuomo", "j23456");

        try {
            usecase.handle(passwordMinLength);
        } catch (InvalidParameterException e) {
            assertEquals("user name should have more than 8 characters", e.getMessage());
        }

        SignUpRequest passwordMaxLength = new SignUpRequest("dwuomo", "j23456986986976967896");

        try {
            usecase.handle(passwordMaxLength);
        } catch (InvalidParameterException e) {
            assertEquals("user name should have less than 12 characters", e.getMessage());
        }

        SignUpRequest passwordWrongFormat = new SignUpRequest("dwuomo", "12345678");

        try {
            usecase.handle(passwordWrongFormat);
        } catch (InvalidParameterException e) {
            assertEquals("password should be alphanumeric", e.getMessage());
        }
    }
}
