package com.example.videoClub.application.service;

import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;

import java.util.List;

public interface ReservedService {
    ReservedRequest getReservedById(String id);

    List<ReservedRequest> getAllReserved();

    ReservedDto createReserved(ReservedDto reservedDto);

    void deleteReservedById(String id);
}
