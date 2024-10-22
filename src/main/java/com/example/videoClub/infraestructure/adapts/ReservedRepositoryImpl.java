package com.example.videoClub.infraestructure.adapts;

import com.example.videoClub.domain.model.Reserved;
import com.example.videoClub.domain.port.ReservedRepository;
import com.example.videoClub.infraestructure.common.mapper.ReservedMapper;
import com.example.videoClub.infraestructure.entity.ReservedEntity;
import com.example.videoClub.infraestructure.repository.ReservedJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReservedRepositoryImpl implements ReservedRepository {
    private final ReservedJpaRepository reservedJpaRepository;
    private final ReservedMapper reservedMapper;

    public ReservedRepositoryImpl(ReservedJpaRepository reservedJpaRepository, ReservedMapper reservedMapper) {
        this.reservedJpaRepository = reservedJpaRepository;
        this.reservedMapper = reservedMapper;
    }

    @Override
    public Optional<Reserved> findReservedById(String id) {
        return reservedJpaRepository.findById(Long.parseLong(id))
                .map(reservedMapper::toReserved);
    }

    @Override
    public Reserved saveReserved(Reserved reserved) {
        ReservedEntity reservedEntity = reservedMapper.toReservedEntity(reserved);
        ReservedEntity savedEntity = reservedJpaRepository.save(reservedEntity);
        return reservedMapper.toReserved(savedEntity);
    }

    @Override
    public void deleteReservedById(Long id) {
        reservedJpaRepository.deleteById(id);
    }

    @Override
    public Optional<List<Reserved>> findAllReserved() {
        List<ReservedEntity> reservedEntities = reservedJpaRepository.findAll();
        List<Reserved> reservedList = reservedEntities.stream()
                .map(reservedMapper::toReserved)
                .toList();
        return reservedList.isEmpty() ? Optional.empty() : Optional.of(reservedList);
    }

    @Override
    public Optional<Reserved> findReservedByFilmId(Long reservedId) {
        return reservedJpaRepository.findByFilm_Id(reservedId)
                .map(reservedMapper::toReserved);
    }
}
