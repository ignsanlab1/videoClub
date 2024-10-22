package com.example.videoClub.infraestructure.common.exceptions;

public class ReservedNotFoundException extends RuntimeException {
    public ReservedNotFoundException (String message) { super(message); }
}
