package com.example.videoClub.infraestructure.common.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException (String message) { super(message); }
}
