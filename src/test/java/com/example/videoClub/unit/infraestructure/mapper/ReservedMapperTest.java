package com.example.videoClub.unit.infraestructure.mapper;

import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.FilmDto;
import com.example.videoClub.domain.dto.response.ReservedDto;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.domain.model.Film;
import com.example.videoClub.domain.model.Reserved;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.common.mapper.FilmMapper;
import com.example.videoClub.infraestructure.common.mapper.ReservedMapperImpl;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import com.example.videoClub.infraestructure.entity.FilmEntity;
import com.example.videoClub.infraestructure.entity.ReservedEntity;
import com.example.videoClub.infraestructure.entity.UserEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedMapperTest {

    @InjectMocks
    private ReservedMapperImpl reservedMapper;

    @Mock
    private FilmMapper filmMapper;
    @Mock
    private UserMapper userMapper;

    @Test
    void testToReserved() {
        ReservedEntity reservedEntity = new ReservedEntity();
        reservedEntity.setId(1L);
        reservedEntity.setFilm(new FilmEntity());
        reservedEntity.setUser(new UserEntity());
        reservedEntity.setStartReserved(LocalDateTime.now());
        reservedEntity.setEndReserved(LocalDateTime.now().plusHours(2));

        when(filmMapper.toFilm(reservedEntity.getFilm())).thenReturn(new Film());
        when(userMapper.toUser(reservedEntity.getUser())).thenReturn(new User());

        Reserved mappedReserved = reservedMapper.toReserved(reservedEntity);

        assertThat(mappedReserved).isNotNull();
        assertThat(mappedReserved.getId()).isEqualTo(reservedEntity.getId());
        assertThat(mappedReserved.getFilm()).isNotNull();
        assertThat(mappedReserved.getUser()).isNotNull();
        assertThat(mappedReserved.getStartReserved()).isEqualTo(reservedEntity.getStartReserved());
        assertThat(mappedReserved.getEndReserved()).isEqualTo(reservedEntity.getEndReserved());
    }

    @Test
    void testToReservedEntity() {
        Reserved reserved = new Reserved();
        reserved.setId(1L);
        reserved.setFilm(new Film());
        reserved.setUser(new User());
        reserved.setStartReserved(LocalDateTime.now());
        reserved.setEndReserved(LocalDateTime.now().plusHours(2));

        when(filmMapper.toFilmEntity(reserved.getFilm())).thenReturn(new FilmEntity());
        when(userMapper.toUserEntity(reserved.getUser())).thenReturn(new UserEntity());

        ReservedEntity mappedReservedEntity = reservedMapper.toReservedEntity(reserved);

        assertThat(mappedReservedEntity).isNotNull();
        assertThat(mappedReservedEntity.getId()).isEqualTo(reserved.getId());
        assertThat(mappedReservedEntity.getFilm()).isNotNull();
        assertThat(mappedReservedEntity.getUser()).isNotNull();
        assertThat(mappedReservedEntity.getStartReserved()).isEqualTo(reserved.getStartReserved());
        assertThat(mappedReservedEntity.getEndReserved()).isEqualTo(reserved.getEndReserved());
    }

    @Test
    void testToReservedFromDto() {
        ReservedDto reservedDto = new ReservedDto();
        reservedDto.setId(1L);
        reservedDto.setFilm(1L);
        reservedDto.setUser(1L);
        reservedDto.setStartReserved(LocalDateTime.now());
        reservedDto.setEndReserved(LocalDateTime.now().plusHours(2));

        FilmEntity filmEntity = FilmEntity.builder()
                .id(1L)
                .build();
        UserEntity productEntity = UserEntity.builder()
                .id(1L)
                .build();
        Film film = Film.builder()
                .id(1L)
                .build();
        User user = User.builder()
                .id(1L)
                .build();

        when(filmMapper.map(anyLong())).thenReturn(filmEntity);
        when(userMapper.map(anyLong())).thenReturn(productEntity );
        when(filmMapper.toFilm( filmMapper.map( reservedDto.getFilm() ) )).thenReturn(film);
        when(userMapper.toUser( userMapper.map( reservedDto.getUser() ) )).thenReturn(user);

        Reserved mappedReserved = reservedMapper.toReservedFromDto(reservedDto);

        assertThat(mappedReserved).isNotNull();
        assertThat(mappedReserved.getId()).isEqualTo(reservedDto.getId());
        assertThat(mappedReserved.getFilm()).isNotNull();
        assertThat(mappedReserved.getUser()).isNotNull();
        assertThat(mappedReserved.getStartReserved()).isEqualTo(reservedDto.getStartReserved());
        assertThat(mappedReserved.getEndReserved()).isEqualTo(reservedDto.getEndReserved());
    }

    @Test
    void testToReservedRequest() {
        Reserved reserved = new Reserved();
        reserved.setFilm(new Film());
        reserved.setUser(new User());
        reserved.setStartReserved(LocalDateTime.now());
        reserved.setEndReserved(LocalDateTime.now().plusHours(2));

        ReservedRequest reservedRequest = reservedMapper.toReservedRequest(reserved);

        assertThat(reservedRequest).isNotNull();
        assertThat(reservedRequest.getFilm()).isNotNull();
        assertThat(reservedRequest.getUser()).isNotNull();
        assertThat(reservedRequest.getStartReserved()).isEqualTo(reserved.getStartReserved());
        assertThat(reservedRequest.getEndReserved()).isEqualTo(reserved.getEndReserved());
    }

    @Test
    void testToReservedFromRequest() {
        ReservedRequest reservedRequest = new ReservedRequest();
        reservedRequest.setFilm(1L);
        reservedRequest.setUser(1L);
        reservedRequest.setStartReserved(LocalDateTime.now());
        reservedRequest.setEndReserved(LocalDateTime.now().plusHours(2));

        Reserved mappedReserved = reservedMapper.toReservedFromRequest(reservedRequest);

        assertThat(mappedReserved).isNotNull();
        assertThat(mappedReserved.getFilm()).isNotNull();
        assertThat(mappedReserved.getUser()).isNotNull();
        assertThat(mappedReserved.getStartReserved()).isEqualTo(reservedRequest.getStartReserved());
        assertThat(mappedReserved.getEndReserved()).isEqualTo(reservedRequest.getEndReserved());
    }

    @Test
    void testMapLongToReservedEntity() {
        Long reservedId = 1L;

        ReservedEntity mappedReservedEntity = reservedMapper.map(reservedId);

        assertThat(mappedReservedEntity).isNotNull();
        assertThat(mappedReservedEntity.getId()).isEqualTo(reservedId);
    }

    @Test
    void testMapReservedEntityToLong() {
        ReservedEntity reservedEntity = new ReservedEntity();
        reservedEntity.setId(1L);

        Long reservedId = reservedMapper.map(reservedEntity);

        assertThat(reservedId).isEqualTo(reservedEntity.getId());
    }

    @Test
    void testMapNullReservedEntityToLong() {
        Long reservedId = reservedMapper.map((ReservedEntity) null);

        assertThat(reservedId).isNull();
    }
}