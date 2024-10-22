package com.example.videoClub.infraestructure.common.mapper;

import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.domain.model.Film;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    Film toFilm(FilmEntity filmEntity);

    Iterable<Film> toFilms(Iterable<FilmEntity> filmEntities);

    @InheritInverseConfiguration
    FilmEntity toFilmEntity(Film film);

    FilmRequest toFilmRequest(Film film);

    FilmDto toFilmDto(Film film);

    Film toFilmFromDto(FilmDto filmDto);

    default FilmEntity map(Long filmId) {
        if (filmId == null) {
            return null;
        }
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setId(filmId);
        return filmEntity;
    }

    default Long map(FilmEntity filmEntity) {
        if (filmEntity == null) {
            return null;
        }
        return filmEntity.getId();
    }
}
