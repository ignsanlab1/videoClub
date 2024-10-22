package com.example.videoClub.domain.validators;

import com.example.videoClub.domain.dto.response.ReservedDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class ReservationDatesValidator implements ConstraintValidator<ValidReservationDates, ReservedDto> {

    @Override
    public boolean isValid(ReservedDto reservedDto, ConstraintValidatorContext context) {
        if (reservedDto == null) {
            return true;
        }

        LocalDateTime startReserved = reservedDto.getStartReserved();
        LocalDateTime endReserved = reservedDto.getEndReserved();

        if (startReserved == null || endReserved == null) {
            return false;
        }

        if (endReserved.isBefore(startReserved)) {
            return false;
        }
        return !endReserved.isAfter(startReserved.plusDays(7));
    }
}
