package com.example.videoClub.infraestructure.common.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException (String message) { super(message); }
}
