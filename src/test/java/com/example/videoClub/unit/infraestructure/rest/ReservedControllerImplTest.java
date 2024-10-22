package com.example.videoClub.unit.infraestructure.rest;

import com.example.videoClub.application.service.ReservedService;
import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;
import com.example.videoClub.infraestructure.rest.ReservedControllerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservedControllerImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class ReservedControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservedService reservedService;

    private ReservedRequest mockReservedRequest;
    private ReservedDto mockReservedDto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockReservedRequest = ReservedRequest.builder()
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(5))
                .build();

        mockReservedDto = ReservedDto.builder()
                .id(1L)
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(5))
                .build();
    }

    @Test
    @Order(1)
    void test01GetReservedById() throws Exception {
        when(reservedService.getReservedById("1")).thenReturn(mockReservedRequest);

        mockMvc.perform(get("/v1/reserved/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.film").value(mockReservedRequest.getFilm()))
                .andExpect(jsonPath("$.user").value(mockReservedRequest.getUser()));
        verify(reservedService).getReservedById("1");
    }

    @Test
    @Order(2)
    void test02GetAllReserved() throws Exception {
        List<ReservedRequest> reservedList = Arrays.asList(mockReservedRequest);
        when(reservedService.getAllReserved()).thenReturn(reservedList);

        mockMvc.perform(get("/v1/reserved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].film").value(mockReservedRequest.getFilm()))
                .andExpect(jsonPath("$[0].user").value(mockReservedRequest.getUser()));

        verify(reservedService).getAllReserved();
    }

    @Test
    @Order(3)
    void test03CreateReserved() throws Exception {
        when(reservedService.createReserved(any(ReservedDto.class))).thenReturn(mockReservedDto);

        mockMvc.perform(post("/v1/reserved")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockReservedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.film").value(mockReservedDto.getFilm()))
                .andExpect(jsonPath("$.user").value(mockReservedDto.getUser()));

        verify(reservedService).createReserved(any(ReservedDto.class));
    }

    @Test
    @Order(4)
    void test04DeleteReservedById() throws Exception {
        doNothing().when(reservedService).deleteReservedById("1");

        mockMvc.perform(delete("/v1/reserved/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(reservedService).deleteReservedById("1");
    }
}
