package com.example.videoClub.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Reserved {
    private Long id;
    private Film film;
    private User user;
    private LocalDateTime startReserved;
    private LocalDateTime endReserved;
}
