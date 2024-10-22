package com.example.videoClub.domain.port;

import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FilmController {
    ResponseEntity<FilmRequest> getFilmById(@PathVariable(name = "filmId") String id);
    ResponseEntity<FilmRequest> getFilmByName(@PathVariable(name = "filmId") String name);
    ResponseEntity<?> createFilm(@RequestBody @Valid FilmDto filmDto);
    ResponseEntity<?> updateFilm(@PathVariable(name = "filmId") String id, @RequestBody @Valid FilmDto filmDto);
    ResponseEntity<?> deleteFilmById(@PathVariable(name = "filmId") String id);
    ResponseEntity<?> deleteFilmByName(@PathVariable(name = "filmName") String name);
    ResponseEntity<List<FilmRequest>> getAllFilms();
}
