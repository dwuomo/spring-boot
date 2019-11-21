package com.schibsted.spain.friends.src.domain.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserRegisteredNotFound extends RuntimeException  {
    public UserRegisteredNotFound(String message) {
        super(message);
    }
}
