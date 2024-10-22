package com.example.videoClub.domain.port;

import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ReservedController {
    ResponseEntity<ReservedRequest> getReservedById(@PathVariable(name = "reservedId") String id);
    ResponseEntity<?> createReserved(@RequestBody @Valid ReservedDto reservedDto);
    ResponseEntity<?> deleteReservedById(@PathVariable(name = "reservedId") String id);
    ResponseEntity<List<ReservedRequest>> getAllReserved();
}
