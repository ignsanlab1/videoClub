package com.example.videoClub.infraestructure.repository;

import com.example.videoClub.infraestructure.entity.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmJpaRepository extends JpaRepository<FilmEntity, Long> {
    Optional<FilmEntity> findByTitle(String name);
    List<FilmEntity> findByDirector(String director);
    List<FilmEntity> findByYear(Integer year);
}
