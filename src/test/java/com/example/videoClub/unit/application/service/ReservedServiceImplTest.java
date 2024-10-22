package com.example.videoClub.unit.application.service;

import com.example.videoClub.application.service.impl.ReservedServiceImpl;
import com.example.videoClub.domain.dto.request.FilmRequest;
import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;
import com.example.videoClub.domain.model.Film;
import com.example.videoClub.domain.model.Reserved;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.adapts.ReservedRepositoryImpl;
import com.example.videoClub.infraestructure.adapts.UserRepositoryImpl;
import com.example.videoClub.infraestructure.common.exceptions.ReservedNotFoundException;
import com.example.videoClub.infraestructure.common.exceptions.UserNotFoundException;
import com.example.videoClub.infraestructure.common.mapper.ReservedMapper;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import com.example.videoClub.infraestructure.entity.ReservedEntity;
import com.example.videoClub.infraestructure.entity.UserEntity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedServiceImplTest {

    @Mock
    private ReservedRepositoryImpl reservedRepository;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private ReservedMapper reservedMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ReservedServiceImpl reservedService;

    private ReservedRequest mockReservedRequest;
    private ReservedDto mockReservedDto;
    private Reserved mockReserved;
    private ReservedEntity mockReservedEntity;
    private User mockUser;
    private Film mockFilm;
    private UserEntity mockUserEntity;
    private FilmEntity mockFilmEntity;

    @BeforeEach
    void setUp() {
        mockFilm = Film.builder()
                .id(1L)
                .title("La Vida Misma")
                .director("Almodovarr")
                .year(2024)
                .duration(120)
                .build();

        mockFilmEntity = FilmEntity.builder()
                .id(1L)
                .title("La Vida Misma")
                .director("Almodovarr")
                .year(2024)
                .duration(120)
                .build();

        mockUser = User.builder()
                .id(1L)
                .email("javier@gmail.com")
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .password("ASjdkasdfkaskdf98asbjndfk2@")
                .build();

        mockUserEntity = UserEntity.builder()
                .id(1L)
                .email("javier@gmail.com")
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .password("ASjdkasdfkaskdf98asbjndfk2@")
                .reservations(new ArrayList<>())
                .build();

        mockReserved = Reserved.builder()
                .id(1L)
                .film(mockFilm)
                .user(mockUser)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(1))
                .build();

        mockReservedEntity = ReservedEntity.builder()
                .id(1L)
                .film(mockFilmEntity)
                .user(mockUserEntity)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(1))
                .build();

        mockReservedRequest = ReservedRequest.builder()
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(1))
                .build();

        mockReservedDto = ReservedDto.builder()
                .id(1L)
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(1))
                .build();

    }

    @Test
    @Order(1)
    void test01GetReservedById_Success() {
        when(reservedRepository.findReservedById("1")).thenReturn(Optional.of(mockReserved));
        when(reservedMapper.toReservedRequest(mockReserved)).thenReturn(mockReservedRequest);

        ReservedRequest result = reservedService.getReservedById("1");

        assertNotNull(result);
        verify(reservedRepository).findReservedById("1");
        verify(reservedMapper).toReservedRequest(mockReserved);
    }

    @Test
    @Order(2)
    void test02GetReservedById_NotFound() {
        when(reservedRepository.findReservedById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ReservedNotFoundException.class, () -> {
            reservedService.getReservedById("1");
        });

        assertEquals("Reserved not found with ID: 1", exception.getMessage());
        verify(reservedRepository).findReservedById("1");
    }

    @Test
    @Order(3)
    void test03CreateReserved_Success() {
        when(reservedMapper.toReservedFromDto(mockReservedDto)).thenReturn(mockReserved);
        when(userRepository.findUserById("1")).thenReturn(Optional.of(mockUser));
        when(reservedMapper.toReservedEntityFromDto(mockReservedDto)).thenReturn(mockReservedEntity);
        when(reservedRepository.saveReserved(mockReserved)).thenReturn(mockReserved);
        when(reservedMapper.toReservedDto(mockReserved)).thenReturn(mockReservedDto);

        ReservedDto result = reservedService.createReserved(mockReservedDto);

        assertNotNull(result);
        verify(userRepository).findUserById("1");
        verify(reservedRepository).saveReserved(mockReserved);
    }

    @Test
    @Order(4)
    void test04CreateReserved_UserNotFound() {
        when(reservedMapper.toReservedFromDto(mockReservedDto)).thenReturn(mockReserved);
        when(userRepository.findUserById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            reservedService.createReserved(mockReservedDto);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @Order(5)
    void test05CreateReserved_ReservationAlreadyExists() {
        mockUserEntity.setReservations(new ArrayList<>(List.of(mockReservedEntity)));

        when(reservedMapper.toReservedFromDto(mockReservedDto)).thenReturn(mockReserved);
        when(userRepository.findUserById("1")).thenReturn(Optional.of(mockUser));
        when(reservedMapper.toReservedEntityFromDto(mockReservedDto)).thenReturn(mockReservedEntity);
        when(reservedRepository.findReservedByFilmId(mockReservedDto.getFilm())).thenReturn(Optional.of(mockReserved));

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            reservedService.createReserved(mockReservedDto);
        });

        assertEquals("A reservation already exists for this reserved with ID: " + mockReservedDto.getFilm(), exception.getMessage());
    }

    @Test
    @Order(6)
    void test06DeleteReservedById_Success() {
        when(reservedRepository.findReservedById("1")).thenReturn(Optional.of(mockReserved));
        when(reservedMapper.toReservedRequest(mockReserved)).thenReturn(mockReservedRequest);

        reservedService.deleteReservedById("1");

        verify(reservedRepository).deleteReservedById(anyLong());
    }

    @Test
    @Order(7)
    void test07DeleteReservedById_NotFound() {
        when(reservedRepository.findReservedById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ReservedNotFoundException.class, () -> {
            reservedService.deleteReservedById("1");
        });

        assertEquals("Error deleting Reserved with ID 1Reserved not found", exception.getMessage());
    }
}
