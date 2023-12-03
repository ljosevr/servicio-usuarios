package com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class PasswordPatternValidator implements ConstraintValidator<ValidPasswordPattern, String> {

    @Value("${pattern.password}")
    private String passwordPattern;

    @Override
    public void initialize(ValidPasswordPattern constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(passwordPattern);
    }
}
