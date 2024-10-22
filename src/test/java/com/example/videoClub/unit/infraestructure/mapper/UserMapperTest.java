package com.example.videoClub.unit.infraestructure.mapper;

import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import com.example.videoClub.infraestructure.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserMapperTest {
    private UserMapper userMapper;
    private UserEntity userEntity;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Javier");
        userEntity.setEmail("Javier@gmail.com");
        userEntity.setPassword("ajsadfnhj298ainfi98AF@");
        userEntity.setBirth(LocalDate.of(2000, 1, 1));

        user = new User();
        user.setId(1L);
        user.setName("Javier");
        user.setEmail("Javier@gmail.com");
        user.setPassword("ajsadfnhj298ainfi98AF@");
        user.setBirth(LocalDate.of(2000, 1, 1));

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setName("Javier");
        userDto.setEmail("Javier@gmail.com");
        userDto.setPassword("ajsadfnhj298ainfi98AF@");
        userDto.setBirth(LocalDate.of(2000, 1, 1));

    }

    @Test
    void testToUser() {
        User mappedUser = userMapper.toUser(userEntity);

        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getId()).isEqualTo(userEntity.getId());
        assertThat(mappedUser.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(mappedUser.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(mappedUser.getName()).isEqualTo(userEntity.getName());
        assertThat(mappedUser.getBirth()).isEqualTo(userEntity.getBirth());
    }

    @Test
    void testToUserEntity() {
        UserEntity mappedUserEntity = userMapper.toUserEntity(user);

        assertThat(mappedUserEntity).isNotNull();
        assertThat(mappedUserEntity.getId()).isEqualTo(user.getId());
        assertThat(mappedUserEntity.getEmail()).isEqualTo(user.getEmail());
        assertThat(mappedUserEntity.getPassword()).isEqualTo(user.getPassword());
        assertThat(mappedUserEntity.getName()).isEqualTo(user.getName());
        assertThat(mappedUserEntity.getBirth()).isEqualTo(user.getBirth());
    }

    @Test
    void testToUserDto() {
        UserDto mappedUserDto = userMapper.toUserDto(user);

        assertThat(mappedUserDto).isNotNull();
        assertThat(mappedUserDto.getId()).isEqualTo(user.getId());
        assertThat(mappedUserDto.getEmail()).isEqualTo(user.getEmail());
        assertThat(mappedUserDto.getPassword()).isEqualTo(user.getPassword());
        assertThat(mappedUserDto.getName()).isEqualTo(user.getName());
        assertThat(mappedUserDto.getBirth()).isEqualTo(user.getBirth());
    }

    @Test
    void testToUserFromDto() {
        User mappedUser = userMapper.toUserFromDto(userDto);

        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getId()).isEqualTo(userDto.getId());
        assertThat(mappedUser.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(mappedUser.getPassword()).isEqualTo(userDto.getPassword());
        assertThat(mappedUser.getName()).isEqualTo(userDto.getName());
        assertThat(mappedUser.getBirth()).isEqualTo(userDto.getBirth());
    }

    @Test
    void testToUserDtoFromEntity() {
        UserDto mappedUserDto = userMapper.toUserDtoFromEntity(userEntity);

        assertThat(mappedUserDto).isNotNull();
        assertThat(mappedUserDto.getId()).isEqualTo(userEntity.getId());
        assertThat(mappedUserDto.getEmail()).isEqualTo(userEntity.getEmail());
        assertThat(mappedUserDto.getPassword()).isEqualTo(userEntity.getPassword());
        assertThat(mappedUserDto.getName()).isEqualTo(userEntity.getName());
        assertThat(mappedUserDto.getBirth()).isEqualTo(userEntity.getBirth());
    }

    @Test
    void testToUserEntityFromDto() {
        UserEntity mappedUserEntity = userMapper.toUserEntityFromDto(userDto);

        assertThat(mappedUserEntity).isNotNull();
        assertThat(mappedUserEntity.getId()).isEqualTo(userDto.getId());
        assertThat(mappedUserEntity.getEmail()).isEqualTo(userDto.getEmail());
        assertThat(mappedUserEntity.getPassword()).isEqualTo(userDto.getPassword());
        assertThat(mappedUserEntity.getName()).isEqualTo(userDto.getName());
        assertThat(mappedUserEntity.getBirth()).isEqualTo(userDto.getBirth());
    }

    @Test
    void testToUserRequest() {
        UserRequest userRequest = userMapper.toUserRequest(user);

        assertThat(userRequest).isNotNull();
        assertThat(userRequest.getEmail()).isEqualTo(user.getEmail());
        assertThat(userRequest.getName()).isEqualTo(user.getName());
        assertThat(userRequest.getBirth()).isEqualTo(user.getBirth());
    }

    @Test
    void testToUserFromRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("Javier@gmail.com");
        userRequest.setName("Javier");
        userRequest.setBirth(LocalDate.of(1990, 1, 1));

        User mappedUser = userMapper.toUserFromRequest(userRequest);

        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getEmail()).isEqualTo(userRequest.getEmail());
        assertThat(mappedUser.getName()).isEqualTo(userRequest.getName());
        assertThat(mappedUser.getBirth()).isEqualTo(userRequest.getBirth());
    }

    @Test
    void testMapLongToUserEntity() {
        Long userId = 1L;

        UserEntity mappedUserEntity = userMapper.map(userId);

        assertThat(mappedUserEntity).isNotNull();
        assertThat(mappedUserEntity.getId()).isEqualTo(userId);
    }

    @Test
    void testMapUserEntityToLong() {
        Long userId = userMapper.map(userEntity);

        assertThat(userId).isEqualTo(userEntity.getId());
    }

    @Test
    void testMapNullUserEntityToLong() {
        Long userId = userMapper.map((UserEntity) null);

        assertThat(userId).isNull();
    }


}
