package ru.se.info.tinder.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
    private int minAge;

    @Override
    public void initialize(Age constraintAnnotation) {
        this.minAge = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return false;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= minAge;
    }
}
