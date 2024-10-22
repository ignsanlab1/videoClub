package com.example.videoClub.infraestructure.repository;

import com.example.videoClub.infraestructure.entity.ReservedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservedJpaRepository extends JpaRepository<ReservedEntity, Long> {
    Optional<ReservedEntity> findByFilm_Id(Long filmId);
}
