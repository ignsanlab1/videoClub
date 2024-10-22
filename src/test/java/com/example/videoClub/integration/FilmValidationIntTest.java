package com.example.videoClub.integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmValidationIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void test01CreateFilm_EmptyTitle() throws Exception {

        mockMvc.perform(post("/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\", \"director\":\"Almodovar\", \"duration\":125, \"year\":2023}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(2)
    void test02CreateFilm() throws Exception {

        mockMvc.perform(post("/v1/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"La vida misma\", \"director\":\"Almodovar\", \"duration\":125, \"year\":2023}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("La vida misma"));
    }
}
