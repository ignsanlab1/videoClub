package com.example.videoClub.domain.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequest {
    private String email;
    private String name;
    private LocalDate birth;
}
