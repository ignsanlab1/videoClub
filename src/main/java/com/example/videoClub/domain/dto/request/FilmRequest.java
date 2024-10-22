package com.example.videoClub.domain.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FilmRequest {
    private String title;
    private String director;
    private Integer year;
    private Integer duration;
}
