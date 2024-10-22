package com.example.videoClub.unit.infraestructure.adapter;

import com.example.videoClub.domain.model.Film;
import com.example.videoClub.infraestructure.adapts.FilmRepositoryImpl;
import com.example.videoClub.infraestructure.common.mapper.FilmMapper;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import com.example.videoClub.infraestructure.repository.FilmJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmRepositoryImplTest {

    @Mock
    private FilmJpaRepository filmJpaRepository;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private FilmRepositoryImpl filmRepositoryImpl;

    private FilmEntity filmEntity;
    private Film film;

    @BeforeEach
    public void setUp() {
        filmEntity = FilmEntity.builder()
                .id(1L)
                .title("La vida misma")
                .director("Almodovar")
                .duration(122)
                .year(2024)
                .build();
        film = Film.builder()
                .id(1L)
                .title("La vida misma")
                .director("Almodovar")
                .duration(122)
                .year(2024)
                .build();
    }

    @Test
    @Order(1)
    void test01FindFilmByNameSuccess() {
        when(filmJpaRepository.findByTitle("La vida misma")).thenReturn(Optional.of(filmEntity));
        when(filmMapper.toFilm(filmEntity)).thenReturn(film);

        Optional<Film> result = filmRepositoryImpl.findFilmByName("La vida misma");

        assertTrue(result.isPresent());
        assertEquals("La vida misma", result.get().getTitle());
        verify(filmJpaRepository).findByTitle("La vida misma");
        verify(filmMapper).toFilm(filmEntity);
    }

    @Test
    @Order(2)
    void test02FindFilmByNameNotFound() {
        when(filmJpaRepository.findByTitle(anyString())).thenReturn(Optional.empty());

        Optional<Film> result = filmRepositoryImpl.findFilmByName("La vida misma");

        assertFalse(result.isPresent());
        verify(filmJpaRepository).findByTitle(anyString());
        verify(filmMapper, never()).toFilm(any());
    }

    @Test
    @Order(3)
    void test03FindFilmByIdSuccess() {
        when(filmJpaRepository.findById(1L)).thenReturn(Optional.of(filmEntity));
        when(filmMapper.toFilm(filmEntity)).thenReturn(film);

        Optional<Film> result = filmRepositoryImpl.findFilmById("1");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(filmJpaRepository).findById(1L);
        verify(filmMapper).toFilm(filmEntity);
    }

    @Test
    @Order(4)
    void test04SaveFilmSuccess() {
        when(filmMapper.toFilmEntity(any(Film.class))).thenReturn(filmEntity);
        when(filmJpaRepository.save(any(FilmEntity.class))).thenReturn(filmEntity);
        when(filmMapper.toFilm(filmEntity)).thenReturn(film);

        Film result = filmRepositoryImpl.saveFilm(film);

        assertNotNull(result);
        assertEquals(film.getId(), result.getId());
        verify(filmJpaRepository).save(filmEntity);
        verify(filmMapper).toFilmEntity(film);
        verify(filmMapper).toFilm(filmEntity);
    }

    @Test
    @Order(5)
    void test05DeleteFilmByIdSuccess() {
        when(filmJpaRepository.findById(1L)).thenReturn(Optional.of(filmEntity));
        filmRepositoryImpl.deleteFilmById(1L);

        verify(filmJpaRepository).delete(filmEntity);
    }

    @Test
    @Order(6)
    void test06DeleteFilmByNameSuccess() {
        when(filmJpaRepository.findByTitle("La vida misma")).thenReturn(Optional.of(filmEntity));

        filmRepositoryImpl.deleteFilmByName("La vida misma");

        verify(filmJpaRepository).delete(filmEntity);
    }

    @Test
    @Order(7)
    void test07FindAllFilmsSuccess() {
        when(filmJpaRepository.findAll()).thenReturn(Arrays.asList(filmEntity));
        when(filmMapper.toFilm(filmEntity)).thenReturn(film);

        Optional<List<Film>> result = filmRepositoryImpl.findAllFilms();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(film.getId(), result.get().get(0).getId());
        verify(filmJpaRepository).findAll();
        verify(filmMapper).toFilm(filmEntity);
    }

    @Test
    @Order(8)
    void test08FindFilmsByDirectorSuccess() {
        when(filmJpaRepository.findByDirector("Almodovar")).thenReturn(Arrays.asList(filmEntity));
        when(filmMapper.toFilm(filmEntity)).thenReturn(film);

        Optional<List<Film>> result = filmRepositoryImpl.findFilmsByDirector("Almodovar");

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(film.getId(), result.get().get(0).getId());
        verify(filmJpaRepository).findByDirector("Almodovar");
        verify(filmMapper).toFilm(filmEntity);
    }

    @Test
    @Order(9)
    void test09FindFilmsByYearSuccess() {
        when(filmJpaRepository.findByYear(2021)).thenReturn(Arrays.asList(filmEntity));
        when(filmMapper.toFilm(filmEntity)).thenReturn(film);

        Optional<List<Film>> result = filmRepositoryImpl.findFilmsByYear(2021);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(film.getId(), result.get().get(0).getId());
        verify(filmJpaRepository).findByYear(2021);
        verify(filmMapper).toFilm(filmEntity);
    }
}
