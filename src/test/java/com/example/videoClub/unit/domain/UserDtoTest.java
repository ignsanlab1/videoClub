package com.example.videoClub.unit.domain;

import com.example.videoClub.domain.dto.response.UserDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserDtoValidation() {
        UserDto userDto = UserDto.builder()
                .email("invalid-email")
                .password("pass")
                .name(null)
                .birth(null)
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(5, violations.size());

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birth")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("You must be at least 18 years old.")));
    }

    @Test
    public void testValidUserDto() {
        UserDto userDto = UserDto.builder()
                .email("javier@gmail.com")
                .password("Vsdasd123asdasASD@")
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        UserDto userDto = UserDto.builder()
                .email(null)
                .password("Vsdasd123asdasASD@")
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testInvalidPassword() {
        UserDto userDto = UserDto.builder()
                .email("javier@gmail.com")
                .password("asdss")
                .name("Javier")
                .birth(LocalDate.of(1990, 1, 1))
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

}
