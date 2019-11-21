package com.schibsted.spain.friends.src.domain.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FriendShipNotFoundException extends RuntimeException {
    public FriendShipNotFoundException(String message) {
        super(message);
    }
}
