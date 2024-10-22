package com.example.videoClub.infraestructure.rest;

import com.example.videoClub.application.service.UserService;
import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.domain.port.UserController;
import com.example.videoClub.infraestructure.common.constants.ApiPathVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.videoClub.infraestructure.common.constants.ExceptionMessages.*;

@RestController
@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.USER_ROUTE)
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve user by ID",
            description = "Fetches the details of a user by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = USER_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = USER_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<UserRequest> getUser(@PathVariable(name = "id") String id) {
        UserRequest userRequest = userService.getUserById(id);
        return ResponseEntity.ok(userRequest);
    }

    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = USER_BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        UserDto createUser = userService.createUser(userDto);
        return ResponseEntity.ok(createUser);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user by ID",
            description = "Deletes a user by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = USER_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = USER_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> deleteUserById(@PathVariable(name = "id") String userId) {
        this.userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
