package com.example.videoClub.infraestructure.common.mapper;

import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user);

    UserDto toUserDto(User user);

    User toUserFromDto(UserDto userDto);

    UserDto toUserDtoFromEntity(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toUserEntityFromDto(UserDto userDto);

    UserRequest toUserRequest(User user);

    User toUserFromRequest(UserRequest userRequest);

    default UserEntity map(Long userId) {
        if (userId == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }

    default Long map(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return userEntity.getId();
    }
}
