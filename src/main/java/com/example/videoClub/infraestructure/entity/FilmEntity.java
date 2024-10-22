package com.example.videoClub.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "FILMS", schema= "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FilmEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "DIRECTOR", nullable = false)
    private String director;

    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "DURATION", nullable = false)
    private Integer duration;

    @OneToOne(mappedBy = "film", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ReservedEntity reservation;
}
