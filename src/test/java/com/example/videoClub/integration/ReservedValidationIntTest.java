package com.example.videoClub.integration;

import com.example.videoClub.domain.model.Film;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.adapts.FilmRepositoryImpl;
import com.example.videoClub.infraestructure.adapts.UserRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedValidationIntTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private FilmRepositoryImpl filmRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private Film film;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        user = User.builder()
                .id(1L)
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .email("javier@gmail.com")
                .password("ashdjJRFIA823@")
                .build();

        userRepository.saveUser(user);

        film = Film.builder()
                .title("La vida misma")
                .director("Almodovar")
                .duration(125)
                .year(2023)
                .id(1L)
                .build();

        filmRepository.saveFilm(film);

    }

    @Test
    @Order(1)
    void test01CreateReservation_Exceeds7Days() throws Exception {
        String reservedJson = "{ \"user\": 1, \"film\": 1, \"startReserved\": \"2024-10-22T10:00:00\", \"endReserved\": \"2024-11-01T10:00:00\" }";

        mockMvc.perform(post("/v1/reserved")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservedJson))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(2)
    void test02CreateReservation() throws Exception {
        String reservedJson = "{ \"user\": 1, \"film\": 1, \"startReserved\": \"2024-10-22T10:00:00\", \"endReserved\": \"2024-10-25T10:00:00\" }";

        mockMvc.perform(post("/v1/reserved")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservedJson))
                .andExpect(status().isOk());

    }
}
