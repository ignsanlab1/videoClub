package com.example.videoClub.infraestructure.adapts;

import com.example.videoClub.domain.model.Film;
import com.example.videoClub.domain.port.FilmRepository;
import com.example.videoClub.infraestructure.common.mapper.FilmMapper;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import com.example.videoClub.infraestructure.repository.FilmJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FilmRepositoryImpl implements FilmRepository {
    private final FilmJpaRepository filmJpaRepository;
    private final FilmMapper filmMapper;

    public FilmRepositoryImpl(FilmJpaRepository filmJpaRepository, FilmMapper filmMapper) {
        this.filmJpaRepository = filmJpaRepository;
        this.filmMapper = filmMapper;
    }

    @Override
    public Optional<Film> findFilmByName(String name) {
        return filmJpaRepository.findByTitle(name)
                .map(filmMapper::toFilm);
    }

    @Override
    public Optional<Film> findFilmById(String id) {
        return filmJpaRepository.findById(Long.parseLong(id))
                .map(filmMapper::toFilm);
    }

    @Override
    public Film saveFilm(Film film) {
        FilmEntity filmEntity = filmMapper.toFilmEntity(film);
        FilmEntity savedEntity = filmJpaRepository.save(filmEntity);
        return filmMapper.toFilm(savedEntity);
    }

    @Override
    public void deleteFilmById(Long id) {
        filmJpaRepository.findById(id)
                .ifPresent(filmJpaRepository::delete);
    }

    @Override
    public void deleteFilmByName(String name) {
        filmJpaRepository.findByTitle(name)
                .ifPresent(filmJpaRepository::delete);
    }

    @Override
    public Optional<List<Film>> findAllFilms() {
        List<FilmEntity> filmEntities = filmJpaRepository.findAll();
        List<Film> films = filmEntities.stream()
                .map(filmMapper::toFilm)
                .toList();
        return films.isEmpty() ? Optional.empty() : Optional.of(films);
    }

    @Override
    public Optional<List<Film>> findFilmsByDirector(String director) {
        List<FilmEntity> filmEntities = filmJpaRepository.findByDirector(director);
        List<Film> films = filmEntities.stream()
                .map(filmMapper::toFilm)
                .toList();
        return films.isEmpty() ? Optional.empty() : Optional.of(films);
    }

    @Override
    public Optional<List<Film>> findFilmsByYear(Integer year) {
        List<FilmEntity> filmEntities = filmJpaRepository.findByYear(year);
        List<Film> films = filmEntities.stream()
                .map(filmMapper::toFilm)
                .toList();
        return films.isEmpty() ? Optional.empty() : Optional.of(films);
    }
}
