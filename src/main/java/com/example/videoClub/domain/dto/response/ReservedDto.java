package com.example.videoClub.domain.dto.response;

import com.example.videoClub.domain.validators.ValidReservationDates;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@ValidReservationDates
public class ReservedDto {
    private Long id;

    @NotNull(message = "Film ID cannot be null")
    private Long film;

    @NotNull(message = "User ID cannot be null")
    private Long user;

    @NotNull(message = "Start reserved cannot be null")
    private LocalDateTime startReserved;

    @NotNull(message = "End reserved cannot be null")
    private LocalDateTime endReserved;
}
