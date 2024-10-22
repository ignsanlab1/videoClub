package com.example.videoClub.application.service.impl;

import com.example.videoClub.application.service.ReservedService;
import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;
import com.example.videoClub.domain.model.Reserved;
import com.example.videoClub.domain.model.User;
import com.example.videoClub.infraestructure.adapts.ReservedRepositoryImpl;
import com.example.videoClub.infraestructure.adapts.UserRepositoryImpl;
import com.example.videoClub.infraestructure.common.exceptions.ReservedNotFoundException;
import com.example.videoClub.infraestructure.common.exceptions.UserNotFoundException;
import com.example.videoClub.infraestructure.common.mapper.ReservedMapper;
import com.example.videoClub.infraestructure.common.mapper.UserMapper;
import com.example.videoClub.infraestructure.entity.ReservedEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.example.videoClub.infraestructure.common.constants.ExceptionMessages.RESERVED_NOT_FOUND;

@Service
public class ReservedServiceImpl implements ReservedService {

    private final ReservedRepositoryImpl reservedRepository;
    private final UserRepositoryImpl userRepository;
    private final ReservedMapper reservedMapper;
    private final UserMapper userMapper;

    public ReservedServiceImpl(ReservedRepositoryImpl reservedRepository, UserRepositoryImpl userRepository, ReservedMapper reservedMapper, UserMapper userMapper) {
        this.reservedRepository = reservedRepository;
        this.userRepository = userRepository;
        this.reservedMapper = reservedMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ReservedRequest getReservedById(String id) {
        return fetchReservedById(id)
                .orElseThrow(() -> new ReservedNotFoundException(RESERVED_NOT_FOUND + " with ID: " + id));
    }

    /**
     * Retrieves the reserved from the repository based on the given parameters.
     *
     * @param id The ID .
     * @return An {@link Optional} containing a {@link ReservedRequest} if a reserved is found, or empty if not.
     */
    private Optional<ReservedRequest> fetchReservedById(String id) {
        return reservedRepository.findReservedById(id)
                .map(reservedMapper::toReservedRequest);
    }

    @Override
    public List<ReservedRequest> getAllReserved() {
        return reservedRepository.findAllReserved()
                .orElse(Collections.emptyList())
                .stream()
                .filter(reserved -> reserved.getEndReserved().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(reserved -> reserved.getFilm().getTitle()))
                .map(reservedMapper::toReservedRequest)
                .toList();
    }

    @Override
    @Transactional
    public ReservedDto createReserved(ReservedDto reservedDto) {
        Reserved reserved = reservedMapper.toReservedFromDto(reservedDto);
        User user = userRepository.findUserById(String.valueOf(reserved.getUser().getId()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ReservedEntity reservedEntity = reservedMapper.toReservedEntityFromDto(reservedDto);
        reservedEntity.getUser().cleanExpiredReservations();
        Long reservedId = reservedDto.getFilm();

        Optional<Reserved> existingReserved = reservedRepository.findReservedByFilmId(reservedId);
        if (existingReserved.isPresent()) {
            throw new IllegalStateException("A reservation already exists for this reserved with ID: " + reservedId);
        }
        reserved.setUser(user);
        Reserved savedReserved = reservedRepository.saveReserved(reserved);
        return reservedMapper.toReservedDto(savedReserved);
    }

    @Override
    @Transactional
    public void deleteReservedById(String id) {
        fetchReservedById(id)
                .map(existingReserved -> {
                    reservedRepository.deleteReservedById(Long.parseLong(id));
                    return existingReserved;
                })
                .orElseThrow(() -> new ReservedNotFoundException("Error deleting Reserved with ID " + id + RESERVED_NOT_FOUND));
    }
}
