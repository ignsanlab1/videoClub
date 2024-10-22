package com.example.videoClub.unit.infraestructure.rest;

import com.example.videoClub.application.service.FilmService;
import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.infraestructure.rest.FilmControllerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FilmControllerImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class FilmControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService filmService;

    private FilmRequest mockFilmRequest;
    private FilmDto mockFilmDto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockFilmRequest = FilmRequest.builder()
                .title("La vida misma")
                .director("Almodovar")
                .duration(125)
                .year(2023)
                .build();

        mockFilmDto = FilmDto.builder()
                .title("La vida misma")
                .director("Almodovar")
                .duration(125)
                .year(2023)
                .build();
    }

    @Test
    @Order(1)
    void test01GetFilmById() throws Exception {
        when(filmService.getFilmById("1")).thenReturn(mockFilmRequest);

        mockMvc.perform(get("/v1/films/id?id={id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockFilmRequest.getTitle()))
                .andExpect(jsonPath("$.director").value(mockFilmRequest.getDirector()))
                .andExpect(jsonPath("$.duration").value(mockFilmRequest.getDuration()))
                .andExpect(jsonPath("$.year").value(mockFilmRequest.getYear()));

        verify(filmService).getFilmById("1");
    }

    @Test
    @Order(2)
    void test02GetFilmByName() throws Exception {
        when(filmService.getFilmByName("La vida misma")).thenReturn((mockFilmRequest));

        mockMvc.perform(get("/v1/films/name?name={name}", "La vida misma"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockFilmRequest.getTitle()));

        verify(filmService).getFilmByName("La vida misma");
    }

    @Test
    @Order(3)
    void test03CreateFilm() throws Exception {
        when(filmService.createFilm(any(FilmDto.class))).thenReturn(mockFilmDto);

        mockMvc.perform(post("/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"La vida misma\", \"director\":\"Almodovar\", \"duration\":125, \"year\":2023}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockFilmDto.getTitle()))
                .andExpect(jsonPath("$.id").value(mockFilmDto.getId()));

        verify(filmService).createFilm(any(FilmDto.class));
    }

    @Test
    @Order(4)
    void test04UpdateFilm() throws Exception {
        when(filmService.updateFilm(eq("1"), any(FilmDto.class))).thenReturn(mockFilmDto);

        mockMvc.perform(put("/v1/films/id?id={id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockFilmDto)))
                .andExpect(status().isNoContent());

        verify(filmService).updateFilm(eq("1"), any(FilmDto.class));
    }

    @Test
    @Order(5)
    void test05DeleteFilmById() throws Exception {
        doNothing().when(filmService).deleteFilmById("1");

        mockMvc.perform(delete("/v1/films/id?id={id}", "1"))
                .andExpect(status().isNoContent());

        verify(filmService).deleteFilmById("1");
    }

    @Test
    @Order(6)
    void test06DeleteFilmByName() throws Exception {
        doNothing().when(filmService).deleteFilmByName("La vida misma");

        mockMvc.perform(delete("/v1/films/name")
                        .param("name", "La vida misma"))
                .andExpect(status().isNoContent());

        verify(filmService).deleteFilmByName("La vida misma");
    }

    @Test
    @Order(7)
    void test07GetAllFilms() throws Exception {
        List<FilmRequest> films = Arrays.asList(mockFilmRequest);
        when(filmService.getAllFilms()).thenReturn(films);

        mockMvc.perform(get("/v1/films"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(mockFilmRequest.getTitle()));

        verify(filmService).getAllFilms();
    }

}
