package com.example.videoClub.application.service;

import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;

import java.util.List;

public interface FilmService {
    FilmRequest getFilmById(String id);

    FilmRequest getFilmByName(String name);

    FilmDto createFilm(FilmDto filmDto);

    FilmDto updateFilm(String id, FilmDto filmDto);

    void deleteFilmById(String id);

    void deleteFilmByName(String name);

    List<FilmRequest> getAllFilms();

    List<FilmRequest> getFilmsByDirector(String director);

    List<FilmRequest> getFilmsByYear(Integer year);
}
