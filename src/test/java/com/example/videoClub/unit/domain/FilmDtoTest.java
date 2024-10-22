package com.example.videoClub.unit.domain;

import com.example.videoClub.domain.dto.response.FilmDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilmDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testFilmDtoValidation() {
        FilmDto filmDto = FilmDto.builder()
                .title(null)
                .director(null)
                .year(null)
                .duration(null)
                .build();

        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);

        assertEquals(4, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("director")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("year")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("duration")));
    }

    @Test
    public void testValidFilmDto() {
        FilmDto filmDto = FilmDto.builder()
                .title("La vida misma")
                .director("Almodovar")
                .year(2024)
                .duration(148)
                .build();

        Set<ConstraintViolation<FilmDto>> violations = validator.validate(filmDto);

        assertTrue(violations.isEmpty());

    }
    }
