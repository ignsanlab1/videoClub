package com.example.videoClub.application.service.impl;

import com.example.videoClub.application.service.FilmService;
import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.infraestructure.adapts.FilmRepositoryImpl;
import com.example.videoClub.infraestructure.common.exceptions.FilmBadRequestException;
import com.example.videoClub.infraestructure.common.exceptions.FilmNotFoundException;
import com.example.videoClub.infraestructure.common.mapper.FilmMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.videoClub.infraestructure.common.constants.ExceptionMessages.FILM_NOT_FOUND;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepositoryImpl filmRepository;
    private final FilmMapper filmMapper;

    public FilmServiceImpl(FilmRepositoryImpl filmRepository, FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
    }

    @Override
    public FilmRequest getFilmById(String id) {
        return fetchFilmById(id)
                .orElseThrow(() -> new FilmNotFoundException(FILM_NOT_FOUND + " with ID: " + id));
    }

    @Override
    public FilmRequest getFilmByName(String name) {
        return fetchFilmByName(name)
                .orElseThrow(() -> new FilmNotFoundException(FILM_NOT_FOUND + " with name: " + name));
    }

    /**
     * Retrieves the film from the repository based on the given parameters.
     *
     * @param name The name .
     * @return An {@link Optional} containing a {@link FilmRequest} if a film is found, or empty if not.
     */
    private Optional<FilmRequest> fetchFilmByName(String name) {
        return filmRepository.findFilmByName(name)
                .map(filmMapper::toFilmRequest);
    }

    /**
     * Retrieves the film from the repository based on the given parameters.
     *
     * @param id The ID .
     * @return An {@link Optional} containing a {@link FilmRequest} if a film is found, or empty if not.
     */
    private Optional<FilmRequest> fetchFilmById(String id) {
        return filmRepository.findFilmById(id)
                .map(filmMapper::toFilmRequest);
    }

    @Override
    @Transactional
    public FilmDto createFilm(FilmDto filmDto) {
        return Optional.of(filmDto)
                .map(filmMapper::toFilmFromDto)
                .map(filmRepository::saveFilm)
                .map(filmMapper::toFilmDto)
                .orElseThrow(() -> new FilmBadRequestException("FilmDto cannot be null"));
    }

    @Override
    @Transactional
    public FilmDto updateFilm(String id, FilmDto filmDto) {
        if (!id.equals(filmDto.getId().toString())) {
            throw new IllegalArgumentException("It is not possible to change the film ID. The ID provided is: " + filmDto.getId());
        }
        return filmRepository.findFilmById(id)
                .map(existingFilm -> {
                    existingFilm.setTitle(filmDto.getTitle());
                    existingFilm.setDirector(filmDto.getDirector());
                    existingFilm.setYear(filmDto.getYear());
                    existingFilm.setDuration(filmDto.getDuration());
                    return filmRepository.saveFilm(existingFilm);
                })
                .map(filmMapper::toFilmDto)
                .orElseThrow(() -> new FilmNotFoundException("Error updating Film with ID " + id + FILM_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteFilmById(String id) {
        fetchFilmById(id)
                .map(existingFilm -> {
                    filmRepository.deleteFilmById(Long.parseLong(id));
                    return existingFilm;
                })
                .orElseThrow(() -> new FilmNotFoundException("Error deleting Film with ID " + id + FILM_NOT_FOUND));
    }

    @Override
    @Transactional
    public void deleteFilmByName(String name) {
        fetchFilmByName(name)
                .map(existingFilm -> {
                    filmRepository.deleteFilmByName(name);
                    return existingFilm;
                })
                .orElseThrow(() -> new FilmNotFoundException("Error deleting Film with name " + name + FILM_NOT_FOUND));
    }

    @Override
    public List<FilmRequest> getAllFilms() {
        return filmRepository.findAllFilms()
                .orElse(Collections.emptyList())
                .stream()
                .map(filmMapper::toFilmRequest)
                .toList();
    }

    @Override
    public List<FilmRequest> getFilmsByDirector(String director) {
        return filmRepository.findFilmsByDirector(director)
                .orElse(Collections.emptyList())
                .stream()
                .map(filmMapper::toFilmRequest)
                .toList();
    }

    @Override
    public List<FilmRequest> getFilmsByYear(Integer year) {
        return filmRepository.findFilmsByYear(year)
                .orElse(Collections.emptyList())
                .stream()
                .map(filmMapper::toFilmRequest)
                .toList();
    }
}
