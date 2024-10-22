package com.example.videoClub.domain.port;

import com.example.videoClub.domain.model.Reserved;

import java.util.List;
import java.util.Optional;

public interface ReservedRepository {

    Optional<Reserved> findReservedById(String id);

    Reserved saveReserved(Reserved reserved);

    void deleteReservedById(Long id);

    Optional<List<Reserved>> findAllReserved();

    Optional<Reserved> findReservedByFilmId(Long filmId);
}
