package com.example.videoClub.application.service.impl;

import com.example.videoClub.application.service.UserService;
import com.example.videoClub.domain.dto.request.UserRequest;
import com.example.videoClub.domain.dto.response.UserDto;
import com.example.videoClub.infraestructure.adapts.UserRepositoryImpl;
import com.example.videoClub.infraestructure.common.exceptions.UserAlreadyExistsException;
import com.example.videoClub.infraestructure.common.exceptions.UserBadRequestException;
import com.example.videoClub.infraestructure.common.exceptions.UserNotFoundException;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.videoClub.infraestructure.common.constants.ExceptionMessages.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepositoryImpl userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserRequest getUserById(String userId) {
        return fetchUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + " with ID: " + userId));
    }

    /**
     * Retrieves the user from the repository based on the given parameters.
     *
     * @param userId The ID .
     * @return An {@link Optional} containing a {@link UserRequest} if a user is found, or empty if not.
     */
    private Optional<UserRequest> fetchUserById(String userId) {
        return userRepository.findUserById(userId)
                .map(userMapper::toUserRequest);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + userDto.getEmail());
        }
        return Optional.of(userDto)
                .map(userMapper::toUserFromDto)
                .map(userRepository::saveUser)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new UserBadRequestException("UserDto cannot be null"));
    }

    @Override
    @Transactional
    public void deleteUserById(String userId) {
        fetchUserById(userId)
                .map(userMapper::toUserFromRequest)
                .map(userMapper::toUserEntity)
                .map(existingUser -> {
                    existingUser.cleanExpiredReservations();
                    if (existingUser.getReservations() != null && !existingUser.getReservations().isEmpty()) {
                        throw new IllegalStateException("Cannot delete user with ID " + userId + " because they have active reservations.");
                    }
                    userRepository.deleteUserById(Long.parseLong(userId));
                    return existingUser;
                })
                .orElseThrow(() -> new UserNotFoundException("Error deleting User with ID " + userId + USER_NOT_FOUND));
    }
}
