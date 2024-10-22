package com.example.videoClub.unit.application.service;

import com.example.videoClub.application.service.impl.FilmServiceImpl;
import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.domain.model.Film;
import com.example.videoClub.infraestructure.adapts.FilmRepositoryImpl;
import com.example.videoClub.infraestructure.common.exceptions.FilmNotFoundException;
import com.example.videoClub.infraestructure.common.mapper.FilmMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    @InjectMocks
    private FilmServiceImpl filmService;

    @Mock
    private FilmRepositoryImpl filmRepository;

    @Mock
    private FilmMapper filmMapper;

    private FilmRequest mockFilmRequest;
    private FilmDto mockFilmDto;
    private Film mockFilm;

    @BeforeEach
    void setUp() {
        mockFilmRequest = FilmRequest.builder()
                .duration(128)
                .director("Almodovar")
                .title("la vida misma")
                .year(2024)
                .build();

        mockFilmDto = FilmDto.builder()
                .duration(128)
                .director("Almodovar")
                .title("la vida misma")
                .year(2024)
                .id(1L)
                .build();

        mockFilm = Film.builder()
                .duration(128)
                .director("Almodovar")
                .title("la vida misma")
                .year(2024)
                .id(1L)
                .build();
    }
    @Test
    @Order(1)
    void test01GetFilmById_Success() {
        when(filmRepository.findFilmById("1")).thenReturn(Optional.of(mockFilm));
        when(filmMapper.toFilmRequest(mockFilm)).thenReturn(mockFilmRequest);

        FilmRequest result = filmService.getFilmById("1");

        assertThat(result).isEqualTo(mockFilmRequest);
        verify(filmRepository).findFilmById("1");
        verify(filmMapper).toFilmRequest(mockFilm);
    }

    @Test
    @Order(2)
    void test02GetFilmById_NotFound() {
        when(filmRepository.findFilmById("1")).thenReturn(Optional.empty());

        assertThatExceptionOfType(FilmNotFoundException.class)
                .isThrownBy(() -> filmService.getFilmById("1"))
                .withMessageContaining("with ID: 1");

        verify(filmRepository).findFilmById("1");
    }

    @Test
    @Order(3)
    void test03GetFilmByName_Success() {
        when(filmRepository.findFilmByName("la vida misma")).thenReturn(Optional.of(mockFilm));
        when(filmMapper.toFilmRequest(mockFilm)).thenReturn(mockFilmRequest);

        FilmRequest result = filmService.getFilmByName("la vida misma");

        assertThat(result).isEqualTo(mockFilmRequest);
        verify(filmRepository).findFilmByName("la vida misma");
        verify(filmMapper).toFilmRequest(mockFilm);
    }

    @Test
    @Order(4)
    void test04GetFilmByName_NotFound() {
        when(filmRepository.findFilmByName("la vida misma")).thenReturn(Optional.empty());

        assertThatExceptionOfType(FilmNotFoundException.class)
                .isThrownBy(() -> filmService.getFilmByName("la vida misma"))
                .withMessageContaining("with name: la vida misma");

        verify(filmRepository).findFilmByName("la vida misma");
    }
    @Test
    @Order(5)
    void test05CreateFilm() {
        when(filmMapper.toFilmFromDto(mockFilmDto)).thenReturn(mockFilm);
        when(filmRepository.saveFilm(any(Film.class))).thenReturn(mockFilm);
        when(filmMapper.toFilmDto(mockFilm)).thenReturn(mockFilmDto);

        FilmDto result = filmService.createFilm(mockFilmDto);

        assertThat(result).isEqualTo(mockFilmDto);
        verify(filmMapper).toFilmFromDto(mockFilmDto);
        verify(filmRepository).saveFilm(mockFilm);
    }

    @Test
    @Order(6)
    void test06UpdateFilm_Success() {
        when(filmRepository.findFilmById("1")).thenReturn(Optional.of(mockFilm));
        when(filmMapper.toFilmDto(mockFilm)).thenReturn(mockFilmDto);
        when(filmRepository.saveFilm(any(Film.class))).thenReturn(mockFilm);

        FilmDto result = filmService.updateFilm("1", mockFilmDto);

        assertThat(result).isEqualTo(mockFilmDto);
        verify(filmRepository).findFilmById("1");
        verify(filmRepository).saveFilm(mockFilm);
    }

    @Test
    @Order(7)
    void test07UpdateFilm_NotFound() {
        when(filmRepository.findFilmById("1")).thenReturn(Optional.empty());

        assertThatExceptionOfType(FilmNotFoundException.class)
                .isThrownBy(() -> filmService.updateFilm("1", mockFilmDto))
                .withMessageContaining("Error updating Film with ID 1");

        verify(filmRepository).findFilmById("1");
    }

    @Test
    @Order(8)
    void test08DeleteFilmById_Success() {
        when(filmRepository.findFilmById("1")).thenReturn(Optional.of(mockFilm));
        when(filmMapper.toFilmRequest(mockFilm)).thenReturn(new FilmRequest());

        filmService.deleteFilmById("1");

        verify(filmRepository).deleteFilmById(anyLong());
    }

    @Test
    @Order(9)
    void test09DeleteFilmById_NotFound() {
        when(filmRepository.findFilmById("1")).thenReturn(Optional.empty());

        assertThatExceptionOfType(FilmNotFoundException.class)
                .isThrownBy(() -> filmService.deleteFilmById("1"))
                .withMessageContaining("Error deleting Film with ID 1");

        verify(filmRepository).findFilmById("1");
    }

    @Test
    @Order(10)
    void test10DeleteFilmByName_Success() {
        when(filmRepository.findFilmByName("la vida misma")).thenReturn(Optional.of(mockFilm));
        when(filmMapper.toFilmRequest(mockFilm)).thenReturn(new FilmRequest());

        filmService.deleteFilmByName("la vida misma");

        verify(filmRepository).deleteFilmByName("la vida misma");
    }

    @Test
    @Order(11)
    void test11DeleteFilmByName_NotFound() {
        when(filmRepository.findFilmByName("la vida misma")).thenReturn(Optional.empty());

        assertThatExceptionOfType(FilmNotFoundException.class)
                .isThrownBy(() -> filmService.deleteFilmByName("la vida misma"))
                .withMessageContaining("Error deleting Film with name la vida misma");

        verify(filmRepository).findFilmByName("la vida misma");
    }
}
