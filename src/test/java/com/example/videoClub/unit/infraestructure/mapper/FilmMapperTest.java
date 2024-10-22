package com.example.videoClub.unit.infraestructure.mapper;

import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.domain.model.Film;
import com.example.videoClub.infraestructure.common.mapper.FilmMapper;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmMapperTest {
    private FilmMapper filmMapper;
    private FilmEntity filmEntity;
    private FilmEntity filmEntity2;
    private Film film;
    private FilmDto filmDto;

    @BeforeEach
    void setUp() {
        filmMapper = Mappers.getMapper(FilmMapper.class);

        filmEntity  = new FilmEntity();
        filmEntity.setId(1L);
        filmEntity.setDirector("Almodovar");
        filmEntity.setTitle("La vida misma");
        filmEntity.setYear(2023);
        filmEntity.setDuration(122);

        filmEntity2  = new FilmEntity();
        filmEntity2.setId(2L);
        filmEntity2.setDirector("Almodovar");
        filmEntity2.setTitle("La vida misma 2");
        filmEntity2.setYear(202);
        filmEntity2.setDuration(126);

        film = new Film();
        film.setId(1L);
        film.setDirector("Almodovar");
        film.setTitle("La vida misma");
        film.setYear(2023);
        film.setDuration(122);

        filmDto = new FilmDto();
        filmDto.setId(1L);
        filmDto.setDirector("Almodovar");
        filmDto.setTitle("La vida misma");
        filmDto.setYear(2023);
        filmDto.setDuration(122);

    }

    @Test
    void testToFilm() {

        Film film = filmMapper.toFilm(filmEntity);

        assertThat(film).isNotNull();
        assertThat(film.getId()).isEqualTo(filmEntity.getId());
        assertThat(film.getTitle()).isEqualTo(filmEntity.getTitle());
        assertThat(film.getDirector()).isEqualTo(filmEntity.getDirector());
        assertThat(film.getDuration()).isEqualTo(filmEntity.getDuration());
        assertThat(film.getYear()).isEqualTo(filmEntity.getYear());
    }

    @Test
    void testToFilms() {
        Iterable<FilmEntity> filmEntities = Arrays.asList(filmEntity, filmEntity2);

        Iterable<Film> films = filmMapper.toFilms(filmEntities);

        assertThat(films).hasSize(2);
        assertThat(films).extracting("id").containsExactlyInAnyOrder(1L, 2L);
        assertThat(films).extracting("title").containsExactlyInAnyOrder(filmEntity.getTitle(), filmEntity2.getTitle());
    }

    @Test
    void testToFilmEntity() {
        FilmEntity filmEntity = filmMapper.toFilmEntity(film);

        assertThat(filmEntity).isNotNull();
        assertThat(filmEntity.getId()).isEqualTo(film.getId());
        assertThat(filmEntity.getTitle()).isEqualTo(film.getTitle());
        assertThat(filmEntity.getDirector()).isEqualTo(film.getDirector());
        assertThat(filmEntity.getDuration()).isEqualTo(film.getDuration());
        assertThat(filmEntity.getYear()).isEqualTo(film.getYear());
    }

    @Test
    void testToFilmRequest() {
        FilmRequest filmRequest = filmMapper.toFilmRequest(film);

        assertThat(filmRequest).isNotNull();
        assertThat(filmRequest.getTitle()).isEqualTo(film.getTitle());
        assertThat(filmRequest.getDirector()).isEqualTo(film.getDirector());
        assertThat(filmRequest.getYear()).isEqualTo(film.getYear());
        assertThat(filmRequest.getDuration()).isEqualTo(film.getDuration());
    }

    @Test
    void testToFilmDto() {
        FilmDto filmDto = filmMapper.toFilmDto(film);

        assertThat(filmDto).isNotNull();
        assertThat(filmDto.getId()).isEqualTo(film.getId());
        assertThat(filmDto.getTitle()).isEqualTo(film.getTitle());
        assertThat(filmDto.getDirector()).isEqualTo(film.getDirector());
        assertThat(filmDto.getDuration()).isEqualTo(film.getDuration());
        assertThat(filmDto.getYear()).isEqualTo(film.getYear());
    }

    @Test
    void testToFilmFromDto() {
        Film film = filmMapper.toFilmFromDto(filmDto);

        assertThat(film).isNotNull();
        assertThat(film.getId()).isEqualTo(filmDto.getId());
        assertThat(film.getTitle()).isEqualTo(filmDto.getTitle());
        assertThat(film.getDirector()).isEqualTo(filmDto.getDirector());
        assertThat(film.getDuration()).isEqualTo(filmDto.getDuration());
        assertThat(film.getYear()).isEqualTo(filmDto.getYear());
    }

    @Test
    void testMapLongToFilmEntity() {
        Long filmId = 1L;

        FilmEntity filmEntity = filmMapper.map(filmId);

        assertThat(filmEntity).isNotNull();
        assertThat(filmEntity.getId()).isEqualTo(filmId);
    }

    @Test
    void testMapFilmEntityToLong() {
        Long filmId = filmMapper.map(filmEntity);

        assertThat(filmId).isEqualTo(filmEntity.getId());
    }

    @Test
    void testMapNullFilmEntityToLong() {
        Long filmId = filmMapper.map((FilmEntity) null);

        assertThat(filmId).isNull();
    }

}
