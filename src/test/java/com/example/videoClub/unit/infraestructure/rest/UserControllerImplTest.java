package com.example.videoClub.unit.infraestructure.rest;

import com.example.videoClub.application.service.UserService;
import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.infraestructure.rest.UserControllerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserControllerImpl.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserRequest mockUserRequest;
    private UserDto mockUserDto;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockUserRequest = UserRequest.builder()
                .name("Javier")
                .email("javier@gmail.com")
                .birth(LocalDate.of(1990, 1, 1))
                .build();

        mockUserDto = UserDto.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .password("asfaskjfdjaks98@A")
                .birth(LocalDate.of(1990, 1, 1))
                .id(1L)
                .build();
    }

    @Test
    @Order(1)
    void test01GetUser() throws Exception {
        when(userService.getUserById("1")).thenReturn(mockUserRequest);

        mockMvc.perform(get("/v1/users/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockUserRequest.getName()))
                .andExpect(jsonPath("$.email").value(mockUserRequest.getEmail()));
        verify(userService).getUserById("1");
    }

    @Test
    @Order(2)
    void test02CreateUser() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(mockUserDto);

        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(mockUserDto.getName()))
                .andExpect(jsonPath("$.email").value(mockUserDto.getEmail()));

        verify(userService).createUser(any(UserDto.class));
    }

    @Test
    @Order(3)
    void test03DeleteUserById() throws Exception {
        doNothing().when(userService).deleteUserById("1");

        mockMvc.perform(delete("/v1/users/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById("1");
    }

}

