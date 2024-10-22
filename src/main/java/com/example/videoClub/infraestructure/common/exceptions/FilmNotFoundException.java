package com.example.videoClub.infraestructure.common.exceptions;

public class FilmNotFoundException extends RuntimeException {
    public FilmNotFoundException (String message) { super(message); }
}
