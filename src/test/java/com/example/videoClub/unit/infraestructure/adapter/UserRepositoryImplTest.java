package com.example.videoClub.unit.infraestructure.adapter;

import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.adapts.UserRepositoryImpl;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import com.example.videoClub.infraestructure.entity.UserEntity;
import com.example.videoClub.infraestructure.repository.UserJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    private UserEntity userEntity;
    private User user;

    @BeforeEach
    public void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .email("javier@gmail.com")
                .password("ajsdfoidajsSADjn98@")
                .build();

        user = user.builder()
                .id(1L)
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .email("javier@gmail.com")
                .password("ajsdfoidajsSADjn98@")
                .build();

    }

    @Test
    @Order(1)
    void testFindUserByIdSuccess() {
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toUser(userEntity)).thenReturn(user);

        Optional<User> result = userRepositoryImpl.findUserById("1");

        assertTrue(result.isPresent());
        assertEquals(user.getName(), result.get().getName());
        verify(userJpaRepository).findById(1L);
        verify(userMapper).toUser(userEntity);
    }

    @Test
    @Order(2)
    void testFindUserByIdNotFound() {
        when(userJpaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<User> result = userRepositoryImpl.findUserById("1");

        assertFalse(result.isPresent());
        verify(userJpaRepository).findById(1L);
        verify(userMapper, never()).toUser(any());
    }

    @Test
    @Order(3)
    void testSaveUserSuccess() {
        when(userMapper.toUserEntity(any(User.class))).thenReturn(userEntity);
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUser(userEntity)).thenReturn(user);

        User result = userRepositoryImpl.saveUser(user);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        verify(userMapper).toUserEntity(user);
        verify(userJpaRepository).save(userEntity);
        verify(userMapper).toUser(userEntity);
    }

    @Test
    @Order(4)
    void testDeleteUserByIdSuccess() {
        userRepositoryImpl.deleteUserById(1L);

        verify(userJpaRepository).deleteById(1L);
    }

}
