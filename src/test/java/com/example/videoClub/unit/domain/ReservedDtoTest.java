package com.example.videoClub.unit.domain;

import com.example.videoClub.domain.dto.response.ReservedDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservedDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Order(1)
    public void test01ReservedDtoValidation() {
        ReservedDto reservedDto = ReservedDto.builder()
                .film(null)
                .user(null)
                .startReserved(null)
                .endReserved(null)
                .build();

        Set<ConstraintViolation<ReservedDto>> violations = validator.validate(reservedDto);
        assertEquals(5, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("film")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("user")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("startReserved")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("endReserved")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Invalid reservation dates: endReserved must be between startReserved and 7 days later.")));
    }

    @Test
    @Order(2)
    public void test02ValidReservedDto() {
        ReservedDto reservedDto = ReservedDto.builder()
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(7))
                .build();

        Set<ConstraintViolation<ReservedDto>> violations = validator.validate(reservedDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @Order(3)
    public void test03InvalidDateRange() {
        ReservedDto reservedDto = ReservedDto.builder()
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now())
                .endReserved(LocalDateTime.now().plusDays(8))
                .build();

        Set<ConstraintViolation<ReservedDto>> violations = validator.validate(reservedDto);
        assertEquals(1, violations.size());
        assertEquals("Invalid reservation dates: endReserved must be between startReserved and 7 days later.", violations.iterator().next().getMessage());
    }

    @Test
    @Order(4)
    public void test04ValidReservationDates() {
        ReservedDto reservedDto = ReservedDto.builder()
                .film(1L)
                .user(1L)
                .startReserved(LocalDateTime.now().minusDays(2))
                .endReserved(LocalDateTime.now().minusDays(1))
                .build();

        Set<ConstraintViolation<ReservedDto>> violations = validator.validate(reservedDto);
        assertTrue(violations.isEmpty());
    }
}
