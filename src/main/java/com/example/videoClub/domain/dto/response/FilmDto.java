package com.example.videoClub.domain.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FilmDto {
    private Long id;

/*    @NotNull(message = "Title cannot be null")*/
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Director cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    private String director;

    @NotNull(message = "Year cannot be null")
    private Integer year;

    @NotNull(message = "Duration cannot be null")
    private Integer duration;
}
