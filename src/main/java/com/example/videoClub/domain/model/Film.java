package com.example.videoClub.domain.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Film {
    private Long id;
    private String title;
    private String director;
    private Integer year;
    private Integer duration;
}
