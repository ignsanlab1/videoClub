package com.example.videoClub.infraestructure.adapts;

import com.example.videoClub.domain.model.User;
import com.example.videoClub.domain.port.UserRepository;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import com.example.videoClub.infraestructure.entity.UserEntity;
import com.example.videoClub.infraestructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    public Optional<User> findUserById(String userId) {
        return userJpaRepository.findById(Long.parseLong(userId))
                .map(userMapper::toUser);
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        UserEntity savedUserEntity = userJpaRepository.save(userEntity);
        return userMapper.toUser(savedUserEntity);
    }

    @Override
    public void deleteUserById(Long userId) {
        userJpaRepository.deleteById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
