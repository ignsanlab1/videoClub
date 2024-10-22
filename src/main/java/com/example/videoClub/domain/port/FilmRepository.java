package com.example.videoClub.domain.port;

import com.example.videoClub.domain.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {
    Optional<Film> findFilmByName(String name);

    Optional<Film> findFilmById(String id);

    Film saveFilm(Film film);

    void deleteFilmById(Long id);

    void deleteFilmByName(String name);

    Optional<List<Film>> findAllFilms();

    Optional<List<Film>> findFilmsByDirector(String director);

    Optional<List<Film>> findFilmsByYear(Integer year);
}
