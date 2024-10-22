package com.example.videoClub.infraestructure.rest;

import com.example.videoClub.application.service.ReservedService;
import com.example.videoClub.domain.dto.request.ReservedRequest;
import com.example.videoClub.domain.dto.response.ReservedDto;
import com.example.videoClub.domain.port.ReservedController;
import com.example.videoClub.infraestructure.common.constants.ApiPathVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.videoClub.infraestructure.common.constants.ExceptionMessages.*;

@RestController
@RequestMapping(ApiPathVariables.V1_ROUTE + ApiPathVariables.RESERVED_ROUTE)
public class ReservedControllerImpl implements ReservedController {
    private final ReservedService reservedService;

    public ReservedControllerImpl(ReservedService reservedService) {
        this.reservedService = reservedService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Retrieve reserved by ID",
            description = "Fetches the details of a reserved by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film found successfully"),
            @ApiResponse(responseCode = "400", description = RESERVED_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = RESERVED_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<ReservedRequest> getReservedById(@PathVariable String id) {
        ReservedRequest reservedRequest = reservedService.getReservedById(id);
        return ResponseEntity.ok(reservedRequest);
    }

    @GetMapping
    @Operation(
            summary = "List all reserved",
            description = "Fetches a list of all available films.",
            tags = {"Reserved"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserved retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Override
    public ResponseEntity<List<ReservedRequest>> getAllReserved() {
        List<ReservedRequest> reserves = this.reservedService.getAllReserved();
        return ResponseEntity.ok(reserves);
    }

    @PostMapping
    @Operation(
            summary = "Create a new reserved",
            description = "Creates a new reserved with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film created successfully"),
            @ApiResponse(responseCode = "400", description = RESERVED_BAD_REQUEST),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    @Override
    public ResponseEntity<?> createReserved(@RequestBody @Valid ReservedDto reservedDto) {
        ReservedDto createReserved = reservedService.createReserved(reservedDto);
        return ResponseEntity.ok(createReserved);
    }

    @Override
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete reserved by ID",
            description = "Deletes a reserved by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reserved deleted successfully"),
            @ApiResponse(responseCode = "400", description = RESERVED_BAD_REQUEST),
            @ApiResponse(responseCode = "404", description = RESERVED_NOT_FOUND),
            @ApiResponse(responseCode = "500", description = INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> deleteReservedById(@PathVariable String id) {
        this.reservedService.deleteReservedById(id);
        return ResponseEntity.noContent().build();
    }
}
