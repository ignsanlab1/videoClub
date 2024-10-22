package com.example.videoClub.domain.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReservedRequest {
    private Long film;
    private Long user;
    private LocalDateTime startReserved;
    private LocalDateTime endReserved;
}
