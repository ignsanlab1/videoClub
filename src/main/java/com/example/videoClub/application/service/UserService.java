package com.example.videoClub.application.service;

import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;

public interface UserService {
    UserRequest getUserById(String id);

    UserDto createUser(UserDto userDto);

    void deleteUserById(String userId);
}
