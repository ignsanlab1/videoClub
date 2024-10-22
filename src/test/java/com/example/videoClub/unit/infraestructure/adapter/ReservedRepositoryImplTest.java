package com.example.videoClub.unit.infraestructure.adapter;

import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.model.Film;
import com.example.videoClub.domain.model.Reserved;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.adapts.ReservedRepositoryImpl;
import com.example.videoClub.infraestructure.common.mapper.ReservedMapper;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import com.example.videoClub.infraestructure.entity.ReservedEntity;
import com.example.videoClub.infraestructure.entity.UserEntity;
import com.example.videoClub.infraestructure.repository.ReservedJpaRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedRepositoryImplTest {

    @Mock
    private ReservedJpaRepository reservedJpaRepository;

    @Mock
    private ReservedMapper reservedMapper;

    @InjectMocks
    private ReservedRepositoryImpl reservedRepositoryImpl;

    private ReservedEntity reservedEntity;
    private Reserved reserved;

    @BeforeEach
    public void setUp() {
        reservedEntity = ReservedEntity.builder()
                .film(new FilmEntity())
                .user(new UserEntity())
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(5))
                .build();

        reserved = Reserved.builder()
                .film(new Film())
                .user(new User())
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(5))
                .build();
    }

    @Test
    @Order(1)
    void test01FindReservedByIdSuccess() {
        when(reservedJpaRepository.findById(1L)).thenReturn(Optional.of(reservedEntity));
        when(reservedMapper.toReserved(reservedEntity)).thenReturn(reserved);

        Optional<Reserved> result = reservedRepositoryImpl.findReservedById("1");

        assertTrue(result.isPresent());
        assertEquals(reserved.getId(), result.get().getId());
        verify(reservedJpaRepository).findById(1L);
        verify(reservedMapper).toReserved(reservedEntity);
    }

    @Test
    @Order(2)
    void test02FindReservedByIdNotFound() {
        when(reservedJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Reserved> result = reservedRepositoryImpl.findReservedById("1");

        assertFalse(result.isPresent());
        verify(reservedJpaRepository).findById(1L);
        verify(reservedMapper, never()).toReserved(any());
    }

    @Test
    @Order(3)
    void test03SaveReservedSuccess() {
        when(reservedMapper.toReservedEntity(any(Reserved.class))).thenReturn(reservedEntity);
        when(reservedJpaRepository.save(any(ReservedEntity.class))).thenReturn(reservedEntity);
        when(reservedMapper.toReserved(reservedEntity)).thenReturn(reserved);

        Reserved result = reservedRepositoryImpl.saveReserved(reserved);

        assertNotNull(result);
        assertEquals(reserved.getId(), result.getId());
        verify(reservedMapper).toReservedEntity(reserved);
        verify(reservedJpaRepository).save(reservedEntity);
        verify(reservedMapper).toReserved(reservedEntity);
    }

    @Test
    @Order(4)
    void test04DeleteReservedByIdSuccess() {
        reservedRepositoryImpl.deleteReservedById(1L);

        verify(reservedJpaRepository).deleteById(1L);
    }

    @Test
    @Order(5)
    void test05FindAllReservedSuccess() {
        when(reservedJpaRepository.findAll()).thenReturn(Arrays.asList(reservedEntity));
        when(reservedMapper.toReserved(reservedEntity)).thenReturn(reserved);

        Optional<List<Reserved>> result = reservedRepositoryImpl.findAllReserved();

        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(reserved.getId(), result.get().get(0).getId());
        verify(reservedJpaRepository).findAll();
        verify(reservedMapper).toReserved(reservedEntity);
    }

    @Test
    @Order(6)
    void test06FindAllReservedEmpty() {
        when(reservedJpaRepository.findAll()).thenReturn(List.of());

        Optional<List<Reserved>> result = reservedRepositoryImpl.findAllReserved();

        assertFalse(result.isPresent());
        verify(reservedJpaRepository).findAll();
        verify(reservedMapper, never()).toReserved(any());
    }

    @Test
    @Order(7)
    void test07FindReservedByFilmIdSuccess() {
        when(reservedJpaRepository.findByFilm_Id(100L)).thenReturn(Optional.of(reservedEntity));
        when(reservedMapper.toReserved(reservedEntity)).thenReturn(reserved);

        Optional<Reserved> result = reservedRepositoryImpl.findReservedByFilmId(100L);

        assertTrue(result.isPresent());
        assertEquals(reserved.getFilm(), result.get().getFilm());
        verify(reservedJpaRepository).findByFilm_Id(100L);
        verify(reservedMapper).toReserved(reservedEntity);
    }

    @Test
    @Order(8)
    void test08FindReservedByFilmIdNotFound() {
        when(reservedJpaRepository.findByFilm_Id(anyLong())).thenReturn(Optional.empty());

        Optional<Reserved> result = reservedRepositoryImpl.findReservedByFilmId(100L);

        assertFalse(result.isPresent());
        verify(reservedJpaRepository).findByFilm_Id(100L);
        verify(reservedMapper, never()).toReserved(any());
    }

}
