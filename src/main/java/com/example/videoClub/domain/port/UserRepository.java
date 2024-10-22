package com.example.videoClub.domain.port;

import com.example.videoClub.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserById(String userId);
    User saveUser(User user);
    void deleteUserById(Long userId);

    boolean existsByEmail(String email);
}
