package com.example.videoClub.infraestructure.common.exceptions;

public class ReservedBadRequestException extends RuntimeException {
    public ReservedBadRequestException (String message) { super(message); }
}
