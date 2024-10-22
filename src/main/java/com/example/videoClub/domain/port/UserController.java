package com.example.videoClub.domain.port;

import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {
    ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto);
    ResponseEntity<UserRequest> getUser(@PathVariable(name = "id") String id);
    ResponseEntity<?> deleteUserById(@PathVariable(name = "id") String id);
}
