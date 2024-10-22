package com.example.videoClub.domain.validators;

import com.example.videoClub.domain.dto.response.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AgeRangeValidator implements ConstraintValidator<ValidateAge, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        if (userDto == null) {
            return false;
        }

        if(userDto.getBirth() == null){
            return false;
        }

        LocalDate today = LocalDate.now();
        return Period.between(userDto.getBirth(), today).getYears() >= 18;
    }
}
