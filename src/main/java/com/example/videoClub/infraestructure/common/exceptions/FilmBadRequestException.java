package com.example.videoClub.infraestructure.common.exceptions;

public class FilmBadRequestException extends RuntimeException {
    public FilmBadRequestException (String message) { super(message); }
}
