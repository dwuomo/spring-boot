package com.schibsted.spain.friends.src.domain.model.signup;

import com.schibsted.spain.friends.src.domain.infrastructure.exceptions.InvalidParameterException;

import java.util.regex.Pattern;

public class User {
    private static final int USERNAME_MIN_LENGTH = 5;
    private static final int USERNAME_MAX_LENGTH = 10;
    private static final int PASSWORD_MIN_LENGTH = 8;
    private static final int PASSWORD_MAX_LENGTH = 12;

    private String username;
    private String password;

    public User(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private void setUsername(String username) throws InvalidParameterException{
        if (username.length() > USERNAME_MAX_LENGTH) {
            throw new InvalidParameterException("user name should have less than "+ USERNAME_MAX_LENGTH +" characters");
        }

        if (username.length() < USERNAME_MIN_LENGTH) {
            throw new InvalidParameterException("user name should have more than "+ USERNAME_MIN_LENGTH +" characters");
        }

        if (username.contains("_")) {
            throw new InvalidParameterException("user name cannot be alphanumeric");
        }

        this.username = username;
    }

    private void setPassword(String password) throws InvalidParameterException {
        if (password.length() > PASSWORD_MAX_LENGTH) {
            throw new InvalidParameterException("user name should have less than "+ PASSWORD_MAX_LENGTH +" characters");
        }

        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidParameterException("user name should have more than "+ PASSWORD_MIN_LENGTH +" characters");
        }

        if (password.contains("-")) {
            throw new InvalidParameterException("illegal - character for password");
        }

        if (!Pattern.matches("^(?=.*\\d)(?=.*[a-zA-Z]).{4,12}$", password)) {
            throw new InvalidParameterException("password should be alphanumeric");
        }

        this.password = password;
    }
}
