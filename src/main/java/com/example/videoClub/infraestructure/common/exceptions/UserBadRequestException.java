package com.example.videoClub.infraestructure.common.exceptions;

public class UserBadRequestException extends RuntimeException {
    public UserBadRequestException (String message) { super(message); }
}
