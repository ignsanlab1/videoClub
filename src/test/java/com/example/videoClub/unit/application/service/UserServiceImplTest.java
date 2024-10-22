package com.example.videoClub.unit.application.service;

import com.example.videoClub.application.service.impl.UserServiceImpl;
import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.adapts.UserRepositoryImpl;
import com.example.videoClub.infraestructure.common.exceptions.UserNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private ReservedEntity reservedEntity;

    private UserRequest mockUserRequest;
    private UserDto mockUserDto;
    private UserEntity mockUserEntity;
    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUserRequest = UserRequest.builder()
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .email("Javier@gmail.com")
                .build();

        mockUserDto = UserDto.builder()
                .name("Javier")
                .id(1L)
                .password("asjdjahnsjASUJdh91@")
                .birth(LocalDate.of(1990, 1, 1))
                .email("Javier@gmail.com")
                .build();

        mockUser = User.builder()
                .name("Javier")
                .id(1L)
                .password("asjdjahnsjASUJdh91@")
                .birth(LocalDate.of(1990, 1, 1))
                .email("Javier@gmail.com")
                .build();

        mockUserEntity = UserEntity.builder()
                .name("Javier")
                .id(1L)
                .password("asjdjahnsjASUJdh91@")
                .birth(LocalDate.of(1990, 1, 1))
                .email("Javier@gmail.com")
                .reservations(new ArrayList<>())
                .build();
    }

    @Test
    @Order(1)
    void test01GetUserById_Success() {
        when(userRepository.findUserById("1")).thenReturn(Optional.of(mockUser));
        when(userMapper.toUserRequest(mockUser)).thenReturn(mockUserRequest);

        UserRequest result = userService.getUserById("1");

        assertNotNull(result);
        verify(userRepository).findUserById("1");
        verify(userMapper).toUserRequest(mockUser);
    }

    @Test
    @Order(2)
    void test02GetUserById_UserNotFound() {
        when(userRepository.findUserById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById("1");
        });

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userRepository).findUserById("1");
    }

    @Test
    @Order(3)
    void test03CreateUser_Success() {
        when(userMapper.toUserFromDto(mockUserDto)).thenReturn(mockUser);
        when(userRepository.saveUser(mockUser)).thenReturn(mockUser);
        when(userMapper.toUserDto(mockUser)).thenReturn(mockUserDto);

        UserDto result = userService.createUser(mockUserDto);

        assertNotNull(result);
        assertEquals(mockUserDto, result);
        verify(userMapper).toUserFromDto(mockUserDto);
        verify(userRepository).saveUser(mockUser);
        verify(userMapper).toUserDto(mockUser);
    }

    @Test
    @Order(4)
    void test04DeleteUserById_Success() {
        when(userRepository.findUserById("1")).thenReturn(Optional.of(mockUser));
        when(userMapper.toUserRequest(mockUser)).thenReturn(mockUserRequest);
        when(userMapper.toUserFromRequest(mockUserRequest)).thenReturn(mockUser);
        when(userMapper.toUserEntity(mockUser)).thenReturn(mockUserEntity);

        userService.deleteUserById("1");

        verify(userRepository).deleteUserById(anyLong());
    }

    @Test
    @Order(5)
    void test05DeleteUserById_UserHasActiveReservations() {
        ReservedEntity activeReservation = ReservedEntity.builder()
                .user(mockUserEntity)
                .film(new FilmEntity())
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(5))
                .build();

        mockUserEntity.setReservations(new ArrayList<>(List.of(activeReservation)));

        when(userRepository.findUserById("1")).thenReturn(Optional.of(mockUser));
        when(userMapper.toUserRequest(mockUser)).thenReturn(mockUserRequest);
        when(userMapper.toUserFromRequest(any())).thenReturn(mockUser);
        when(userMapper.toUserEntity(any())).thenReturn(mockUserEntity);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            userService.deleteUserById("1");
        });

        assertEquals("Cannot delete user with ID 1 because they have active reservations.", exception.getMessage());
    }

    @Test
    @Order(6)
    void test06DeleteUserById_UserNotFound() {
        when(userRepository.findUserById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserById("1");
        });

        assertEquals("Error deleting User with ID 1User not found", exception.getMessage());
    }

}
