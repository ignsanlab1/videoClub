package com.example.videoClub.domain.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeRangeValidator.class)
public @interface ValidateAge {
    String message() default "You must be at least 18 years old.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
