package com.example.videoClub.domain.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationDatesValidator.class)
public @interface ValidReservationDates {
    String message() default "Invalid reservation dates: endReserved must be between startReserved and 7 days later.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
